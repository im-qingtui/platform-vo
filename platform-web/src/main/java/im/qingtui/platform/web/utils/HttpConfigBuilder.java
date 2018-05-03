package im.qingtui.platform.web.utils;


import im.qingtui.platform.sensitive.SensitiveInfo;
import im.qingtui.platform.sensitive.SensitiveLevel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public HttpConfigBuilder body(){
        this.httpConfig.setBody(true);
        return this;
    }

    /**
     * 排除打印日志的uri
     * @param uri 需要被排除的uri
     * @return HttpConfig配置创建器
     */
    public HttpConfigBuilder excludedUri(String... uri){
        if(uri != null){
            Set<String> uris = new HashSet<String>();
            uris.addAll(Arrays.asList(uri));
            this.httpConfig.getExcludedUri().addAll(uris);
        }
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
        return sensitiveInfo(rate,SensitiveLevel.INCOMPLETE,sensitives);
    }

    /**
     * 配置需要被排除的敏感信息
     * @param params 被排除的关键词，请求参数或请求头包含（注意不是全匹配）这些关键词时将被忽略打印
     * @return HttpConfig配置创建器
     */
    public HttpConfigBuilder excludedParam(String... params){
        return sensitiveInfo(0,SensitiveLevel.HIDE,params);
    }

    private HttpConfigBuilder sensitiveInfo(double rate, SensitiveLevel level, String... sensitives) {
        if(sensitives != null){
            Set<SensitiveParam> set = new HashSet<SensitiveParam>();
            for(String sensitive : sensitives){
                SensitiveParam sensitiveParam = new SensitiveParam();
                sensitiveParam.setParamName(sensitive);
                sensitiveParam.setLevel(level);
                sensitiveParam.setRate(rate);
                set.add(sensitiveParam);
            }
            this.httpConfig.getSensitiveInfo().addAll(set);
        }
        return this;
    }
}
