package im.qingtui.platform.web.filter;

import im.qingtui.platform.web.utils.UnderLineCamelUtils;
import im.qingtui.platform.web.wrapper.ParameterRequestWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求参数下划线转驼峰
 *
 * @author yiya
 */
public class UnderLineToCamelFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Map<String, String[]> m = new HashMap<String, String[]>();
        Enumeration enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String[] value = req.getParameterValues(key);
            m.put(UnderLineCamelUtils.underlineToCamel(key), value);
        }
        req = new ParameterRequestWrapper((HttpServletRequest) req, m);
        chain.doFilter(req, resp);
    }

    public void destroy() {

    }
}
