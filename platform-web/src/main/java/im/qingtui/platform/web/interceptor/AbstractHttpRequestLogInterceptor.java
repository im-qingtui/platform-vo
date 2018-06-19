package im.qingtui.platform.web.interceptor;


import im.qingtui.platform.web.annotation.RequestLog;
import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;
import im.qingtui.platform.web.utils.HttpUtils;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 日志拦截器
 *
 * @author dongbin
 */
@Controller
public abstract class AbstractHttpRequestLogInterceptor extends HandlerInterceptorAdapter {

    private final HttpConfig httpConfig = config(new HttpConfigBuilder());

    private static final String MDC_DESC_KEY = "DESC";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RequestLog logAnnotation = method.getAnnotation(RequestLog.class);

        if (null != logAnnotation) {
            if (logAnnotation.ignore()) {
                return true;
            } else if (StringUtils.isNotBlank(logAnnotation.value())) {
                MDC.put(MDC_DESC_KEY, logAnnotation.value());
            }
        }

        HttpUtils.httpRequestLog(req, httpConfig);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 配置http打印项
     *
     * @param builder HttpConfig创建器
     * @return HttpConfig
     */
    protected abstract HttpConfig config(HttpConfigBuilder builder);
}
