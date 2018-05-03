package im.qingtui.platform.web.utils;


import im.qingtui.platform.sensitive.SensitiveLevel;
import im.qingtui.platform.sensitive.utils.SensitiveInfoUtils;
import im.qingtui.platform.web.annotation.HttpElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将http请求内容封装到map中
 *
 * @author leesir
 */
public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 将HTTP请求的信息组装成map，用于日志打印
     *
     * @param request http请求
     * @return map
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

    public static String getBodyString(HttpServletRequest request) {
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


}
