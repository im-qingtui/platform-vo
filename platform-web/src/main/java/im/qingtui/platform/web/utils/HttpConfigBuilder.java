package im.qingtui.platform.web.utils;


import im.qingtui.platform.sensitive.SensitiveInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leesir on 2017/6/14.
 */
public class HttpConfigBuilder {

    private HttpConfig httpConfig;

    public HttpConfigBuilder() {
        httpConfig = new HttpConfig();
    }

    public HttpConfig build() {
        return this.httpConfig;
    }

    public HttpConfigBuilder ip() {
        this.httpConfig.setIp(true);
        return this;
    }

    public HttpConfigBuilder params() {
        this.httpConfig.setParams(true);
        return this;
    }

    public HttpConfigBuilder headers() {
        this.httpConfig.setHeaders(true);
        return this;
    }

    public HttpConfigBuilder uri() {
        this.httpConfig.setUri(true);
        return this;
    }

    public HttpConfigBuilder method() {
        this.httpConfig.setMethod(true);
        return this;
    }

    /**
     * 排除打印日志的uri
     * @param uri 需要被排除的uri
     * @return HttpConfig配置创建器
     */
    public HttpConfigBuilder excludedUri(String... uri){
        List<String> uris = new ArrayList<String>();
        uris.addAll(Arrays.asList(uri));
        this.httpConfig.setExcludedUri(uris);
        return this;
    }

    /**
     * 配置需要被过滤的敏感信息
     *
     * @param sensitives 被过滤的关键词，请求参数或请求头包含（注意不是全匹配）这些关键词时将被打码30%的内容
     * @return HttpConfig配置创建器
     */
    public HttpConfigBuilder sensitiveInfo(String... sensitives) {
        return sensitiveInfo(SensitiveInfo.DEFAULT_RATE, sensitives);
    }

    /**
     * 配置需要被过滤的敏感信息
     *
     * @param rate 打码比率 范围[0,1]
     * @param sensitives 被过滤的关键词，请求参数或请求头包含（注意不是全匹配）这些关键词时将被打码
     * @return HttpConfig配置创建器
     */
    public HttpConfigBuilder sensitiveInfo(double rate, String... sensitives) {
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(sensitives));
        this.httpConfig.setSensitiveInfo(list);
        this.httpConfig.setSensitiveRate(rate);
        return this;
    }
}
