package im.qingtui.platform.web.interceptor;

import im.qingtui.platform.web.utils.AuthHeaderUtil;
import im.qingtui.platform.web.utils.HttpUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 把header中的参数放到parameter中的拦截器 如果 parameter 本来就有的参数 则不会用header里的参数覆盖它 此处不处理accountId 因为 aid在accessToken中 所以之后在token验证时一并处理
 *
 * @author dongbin
 */
public abstract class HeaderToParameterInterceptor extends HandlerInterceptorAdapter {


    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKE = "accessToken";



    /**
     * 返回一个 map 配置 得知 需要把 哪些 header 中的 值 放到 param中 key是header 中的key value 是 param中的key
     * param中已存在此key 将跳过
     * @return map 配置 得知 需要把 哪些 header 中的 值 放到 param中
     */
    abstract protected Map<String, String> getHeaderToParameterConfigMap();

    private void setParameterMapAndAttribute(HttpServletRequest request) {
        Map<String, String[]> parameterMap = HttpUtils.getParameterMap(request);
        String authorization = request.getHeader(HEADER_AUTHORIZATION);
        String accessToken = AuthHeaderUtil.getAccessTokenFromAuthHeader(authorization);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = request.getParameter(ACCESS_TOKE);
        }
        request.setAttribute(ACCESS_TOKE, accessToken);
        accessTokenHeaderToParameter(authorization, parameterMap);
        for (Map.Entry<String, String> entry : getHeaderToParameterConfigMap().entrySet()) {
            String value = request.getParameter(entry.getValue());
            if (!StringUtils.isEmpty(value)) {
                request.setAttribute(entry.getValue(), value);
            } else {
                headerToParameterAndAttribute(request, parameterMap, entry.getKey(), entry.getValue());

            }

        }
    }

    private void setAttribute(HttpServletRequest request) {
        String authorization = request.getHeader(HEADER_AUTHORIZATION);
        String accessToken = AuthHeaderUtil.getAccessTokenFromAuthHeader(authorization);
        request.setAttribute(ACCESS_TOKE, accessToken);
        for (Map.Entry<String, String> entry : getHeaderToParameterConfigMap().entrySet()) {
            String value = request.getParameter(entry.getValue());
            if (StringUtils.isEmpty(value)) {
                value = request.getHeader(entry.getKey());
            }
            request.setAttribute(entry.getValue(), value);
        }
    }

    /**
     * header Token 转request parameter参数
     */
    private void accessTokenHeaderToParameter(String accessToken, Map<String, String[]> parameterMap) {
        if (!StringUtils.isEmpty(accessToken)) {

            if (!parameterMap.containsKey(ACCESS_TOKE)) {
                parameterMap.put(ACCESS_TOKE, new String[]{accessToken});
            }
        }
    }

    /**
     * Token数据 转request parameter参数
     */
    private void headerToParameterAndAttribute(HttpServletRequest request, Map<String, String[]> parameterMap, String headerKey, String parameterKey) {
        if (!parameterMap.containsKey(parameterKey)) {
            String headerValue = request.getHeader(headerKey);
            if (!StringUtils.isEmpty(headerValue)) {
                parameterMap.put(parameterKey, new String[]{headerValue});
                request.setAttribute(parameterKey, headerValue);
            }
        }
    }

    /**
     * Intercept the execution of a request handler <i>before</i> its invocation.
     * <p>Allows for preparing context resources (such as a Hibernate Session)
     * and expose them as request attributes or as thread-local objects.
     *
     * @param request the current web request
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean isUploadFileRequest = HttpUtils.isUploadFileRequest(request);
        if (isUploadFileRequest) {
            setAttribute(request);
        } else {

            setParameterMapAndAttribute(request);
        }
        return super.preHandle(request, response, handler);
    }

}
