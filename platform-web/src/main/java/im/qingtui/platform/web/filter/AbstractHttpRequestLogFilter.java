package im.qingtui.platform.web.filter;

import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;
import im.qingtui.platform.web.utils.HttpUtils;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求统一日志
 *
 * @author leesir
 */
public abstract class AbstractHttpRequestLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHttpRequestLogFilter.class);

    private HttpConfig httpConfig;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        NDC.push("uri=" + uri + " id=" + UUID.randomUUID().toString().split("-")[0]);
        httpRequestLog(request);
        chain.doFilter(req, resp);
        NDC.pop();
    }

    protected void httpRequestLog(HttpServletRequest request) {
        if (httpConfig != null && !excluded(httpConfig, request)) {
            LOGGER.info(HttpUtils.getHttpRequestProperties(request, httpConfig).toString());
        }
    }

    private boolean excluded(HttpConfig httpConfig, HttpServletRequest request) {
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

    /**
     * 配置http打印项
     *
     * @param builder HttpConfig创建器
     */
    protected abstract HttpConfig config(HttpConfigBuilder builder);

    public void init(FilterConfig config) throws ServletException {
        HttpConfigBuilder builder = new HttpConfigBuilder();
        httpConfig = config(builder);
    }

}
