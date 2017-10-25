package im.qingtui.platform.web.filter;


import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;

/**
 * http默认日志打印过滤器
 *
 * @author yiya
 */
public class HttpRequestLogFilter extends AbstractHttpRequestLogFilter {

    @Override
    protected HttpConfig config(HttpConfigBuilder builder) {
        return builder.ip().method().params().build();
    }
}
