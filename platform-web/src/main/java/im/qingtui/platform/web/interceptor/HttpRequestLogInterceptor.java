package im.qingtui.platform.web.interceptor;

import im.qingtui.platform.web.utils.HttpConfig;
import im.qingtui.platform.web.utils.HttpConfigBuilder;

/**
 * 日志拦截器
 *
 * @author dongbin
 */
public class HttpRequestLogInterceptor extends AbstractHttpRequestLogInterceptor {

    @Override
    protected HttpConfig config(HttpConfigBuilder builder) {
        return builder.ip().method().params().build();
    }
}
