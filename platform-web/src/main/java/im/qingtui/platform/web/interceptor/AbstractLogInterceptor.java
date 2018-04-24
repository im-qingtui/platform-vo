package im.qingtui.platform.web.interceptor;


import im.qingtui.platform.web.annotation.Log;
import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;
import im.qingtui.platform.web.utils.HttpUtils;
import im.qingtui.platform.web.utils.UUIDUtil;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.NDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 日志拦截器
 *
 * @author dongbin
 */
@Controller
public abstract class AbstractLogInterceptor extends HandlerInterceptorAdapter {

    private final HttpConfig httpConfig = config(new HttpConfigBuilder());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String uri = request.getRequestURI();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null && logAnnotation.ignore()) {
            return true;
        }
        StringBuilder ndcMessage = new StringBuilder();
        if (logAnnotation != null
            && !StringUtils.isEmpty(logAnnotation.value())
            && !Log.DEFAULT_DESC.equals(logAnnotation.value())) {
            ndcMessage.append("des=").append(logAnnotation.value()).append(" ");
        }
        ndcMessage.append("uri=").append(uri).append(" id=").append(UUIDUtil.getUUID());
        NDC.push(ndcMessage.toString());
        HttpUtils.httpRequestLog(request, httpConfig);
        NDC.pop();
        return true;
    }


    /**
     * 配置http打印项
     */
    protected abstract HttpConfig config(HttpConfigBuilder builder);
}
