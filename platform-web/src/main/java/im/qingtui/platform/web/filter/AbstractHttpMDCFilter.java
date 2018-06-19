package im.qingtui.platform.web.filter;

import im.qingtui.platform.utils.log.LogUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * http 请求统一日志
 *
 * @author leesir
 */
public abstract class AbstractHttpMDCFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "REQUEST_ID";

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        String requestId = LogUtils.getRequestId();
        String method = getMethod(request);
        LogUtils.setMDC(requestId, method);
        ((HttpServletResponse) resp).addHeader(REQUEST_ID_HEADER, requestId);

        try {
            chain.doFilter(req, resp);
        } finally {
            long duration = System.currentTimeMillis() - start;
            String requester = getRequester(request);
            LogUtils.logRequest(requester, duration);
            LogUtils.clearRequestId();
            LogUtils.clearMDC();
        }
    }

    private String getMethod(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        if (StringUtils.isNotBlank(contextPath)) {
            return StringUtils.substring(uri, contextPath.length());
        } else {
            return uri;
        }

    }

    /**
     * 获取请求发起人身份标识
     *
     * @return 身份标识
     */
    protected abstract String getRequester(HttpServletRequest request);

}
