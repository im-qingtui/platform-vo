package im.qingtui.platform.web.filter;

import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;
import im.qingtui.platform.web.utils.HttpUtils;
import im.qingtui.platform.web.wrapper.ParameterRequestWrapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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
        HttpServletRequest request = new ParameterRequestWrapper((HttpServletRequest) req);

        HttpUtils.httpRequestLog(request, httpConfig);
        chain.doFilter(request, resp);
    }

    /**
     * 配置http打印项
     *
     * @param builder HttpConfig创建器
     */
    protected abstract HttpConfig config(HttpConfigBuilder builder);

    /**
     * 获取请求发起人身份标识
     *
     * @return 身份标识
     */

    public void init(FilterConfig config) throws ServletException {
        HttpConfigBuilder builder = new HttpConfigBuilder();
        httpConfig = config(builder);
    }

}
