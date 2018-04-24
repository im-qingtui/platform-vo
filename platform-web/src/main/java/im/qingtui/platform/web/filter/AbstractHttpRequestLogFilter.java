package im.qingtui.platform.web.filter;

import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;
import im.qingtui.platform.web.utils.HttpUtils;
import im.qingtui.platform.web.utils.UUIDUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.NDC;

/**
 * http请求统一日志
 *
 * @author leesir
 */
public abstract class AbstractHttpRequestLogFilter implements Filter {

    private HttpConfig httpConfig;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        NDC.push("uri=" + uri + " id=" + UUIDUtil.getUUID());
        try {
            HttpUtils.httpRequestLog(request,httpConfig);
            chain.doFilter(req, resp);
        } finally {
            NDC.pop();
        }
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
