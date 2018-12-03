package im.qingtui.platform.web.utils;


import com.alibaba.fastjson.JSONObject;
import im.qingtui.platform.sensitive.SensitiveLevel;
import im.qingtui.platform.sensitive.utils.SensitiveInfoUtils;
import im.qingtui.platform.web.annotation.HttpElement;
import im.qingtui.platform.web.wrapper.ParameterRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * 将http请求内容封装到map中
 *
 * @author leesir
 */
@UtilityClass
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 将HTTP请求的信息组装成map，用于日志打印
     *
     * @param request request http请求
     * @param httpConfig config 配置
     * @return 所有需要打印的参数
     */
    public static Map<String, String> getHttpRequestProperties(HttpServletRequest request, HttpConfig httpConfig) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        StringBuilder parameterBuilder = new StringBuilder();
        parameterBuilder.append("{");
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = (String) parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            //过滤敏感信息
            sensitiveFilter(paramName, paramValue, parameterBuilder, httpConfig.getSensitiveInfo());
        }
        if (parameterBuilder.length() > 1) {
            parameterBuilder.deleteCharAt(parameterBuilder.length() - 1);
        }
        parameterBuilder.append("}");
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("{");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            sensitiveFilter(headerName, headerValue, headerBuilder, httpConfig.getSensitiveInfo());
            headerBuilder.append(headerName).append(":").append(headerValue);
            headerBuilder.append(",");

        }
        if (headerBuilder.length() > 1) {
            headerBuilder.deleteCharAt(headerBuilder.length() - 1);
        }
        headerBuilder.append("}");
        Map<String, String> fullMap = new HashMap<String, String>();
        String body = getBodyString(request);
        pushToFullMap(uri, method, remoteAddr, parameterBuilder.toString(), headerBuilder.toString(), body, fullMap);
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = HttpConfig.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(HttpElement.class) != null) {
                field.setAccessible(true);
                try {
                    Boolean b = (Boolean) field.get(httpConfig);
                    if (b) {
                        map.put(field.getName(), fullMap.get(field.getName()));
                    }
                } catch (IllegalAccessException e) {
                    //并不会产生的异常
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 从当前线程中 获取 人request 变量
     * @return request 变量
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
    }

    private static void pushToFullMap(String uri, String method, String remoteAddr, String parameters, String headers, String body,
        Map<String, String> fullMap) {
        fullMap.put("ip", remoteAddr);
        fullMap.put("headers", headers);
        fullMap.put("params", parameters);
        fullMap.put("uri", uri);
        fullMap.put("method", method);
        fullMap.put("body", body);
    }

    private static void sensitiveFilter(String paramName, String paramValue, StringBuilder parameterBuilder, Set<SensitiveParam> sensitiveParams) {
        for (SensitiveParam sensitiveParam : sensitiveParams) {
            if (paramName.equalsIgnoreCase(sensitiveParam.getParamName())
                || paramName.toLowerCase().contains(sensitiveParam.getParamName().toLowerCase())) {
                if (sensitiveParam.getLevel() == SensitiveLevel.HIDE) {
                    return;
                }
                paramValue = SensitiveInfoUtils.incomplete(paramValue, sensitiveParam.getRate());
                break;
            }
        }
        parameterBuilder.append(paramName).append(":").append(paramValue);
        parameterBuilder.append(",");
    }


    public static void httpRequestLog(HttpServletRequest request, HttpConfig httpConfig) {
        if (httpConfig != null && !excluded(httpConfig, request)) {
            LOGGER.info(HttpUtils.getHttpRequestProperties(request, httpConfig).toString());
        }
    }

    private static boolean excluded(HttpConfig httpConfig, HttpServletRequest request) {
        boolean excluded = false;
        String httpUri = request.getRequestURI();
        for (String uri : httpConfig.getExcludedUri()) {
            if (httpUri.contains(uri)) {
                excluded = true;
                break;
            }
        }
        return excluded;
    }

    private static String getBodyString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get http request body error", e);
        }
        return sb.toString();
    }


    /**
     * 向 httpResponse 中写入文件流
     *
     * @param fileName 文件名
     * @param inputStream 输入流
     */
    public static void writeFileToResponse(HttpServletResponse response, String fileName, InputStream inputStream) throws IOException {
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        setFileDownloadHeader(response, fileName);
//        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        //激活下载操作
        @Cleanup OutputStream outputStream = response.getOutputStream();
        //打开本地文件流
        FileCopyUtils.copy(inputStream, outputStream);
    }

    /**
     * 向 httpResponse 中写入返回
     */
    public static boolean writeResponse(ServletResponse response, Object result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSONObject.toJSONString(result));
        return false;
    }

    public static ParameterRequestWrapper getParameterRequestWrapper(HttpServletRequest request) {
        ParameterRequestWrapper wrapper;
        if (request instanceof DefaultMultipartHttpServletRequest) {
            wrapper = (ParameterRequestWrapper) ((DefaultMultipartHttpServletRequest) request).getRequest();
        } else {
            wrapper = (ParameterRequestWrapper) request;
        }
        return wrapper;
    }

    public static boolean isUploadFileRequest(HttpServletRequest request) {
        return request.getHeader("Content-Type") != null &&
            request.getHeader("Content-Type").contains("multipart");
    }

    public static Map<String, String[]> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap;
        if (HttpUtils.isUploadFileRequest(request)) {
            val fileRequest = (DefaultMultipartHttpServletRequest) request;
            parameterMap = fileRequest.getParameterMap();
        } else {
            ParameterRequestWrapper wrapper = getParameterRequestWrapper(request);
            parameterMap = wrapper.getParameterMap();
        }
        return parameterMap;
    }

    /**
     * 浏览器下载文件时需要在服务端给出下载的文件名，当文件名是ASCII字符时没有问题当文件名有非ASCII字符时就有可能出现乱码 最终设置的response header是这样: Content-Disposition: attachment; filename="encoded_text";
     * filename*=utf-8''encoded_text 其中encoded_text是经过RFC 3986的“百分号URL编码”规则处理过的文件名
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String filename) {
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeURIComponent(filename) + "\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(filename);
        response.setHeader("Content-Disposition", headerValue);
    }

    /**
     * 符合 RFC 3986 标准的“百分号URL编码”在这个方法里，空格会被编码成%20，而不是+和浏览器的encodeURIComponent行为一致
     */
    public static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
