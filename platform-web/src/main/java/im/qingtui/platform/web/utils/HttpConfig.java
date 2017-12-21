package im.qingtui.platform.web.utils;

import im.qingtui.platform.web.annotation.HttpElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leesir on 2017/6/14.
 */
public class HttpConfig {

    @HttpElement
    private boolean ip;

    @HttpElement
    private boolean params;

    @HttpElement
    private boolean headers;

    @HttpElement
    private boolean uri;

    @HttpElement
    private boolean method;

    @HttpElement
    private List<String> excludedUri = new ArrayList<String>();

    private List<String> sensitiveInfo = new ArrayList<String>();

    private double sensitiveRate;

    HttpConfig() {

    }

    public boolean isIp() {
        return ip;
    }

    public void setIp(boolean ip) {
        this.ip = ip;
    }

    public boolean isParams() {
        return params;
    }

    public void setParams(boolean params) {
        this.params = params;
    }

    public boolean isHeaders() {
        return headers;
    }

    public void setHeaders(boolean headers) {
        this.headers = headers;
    }

    public boolean isUri() {
        return uri;
    }

    public void setUri(boolean uri) {
        this.uri = uri;
    }

    public boolean isMethod() {
        return method;
    }

    public void setMethod(boolean method) {
        this.method = method;
    }

    public List<String> getSensitiveInfo() {
        return sensitiveInfo;
    }

    public void setSensitiveInfo(List<String> sensitiveInfo) {
        this.sensitiveInfo = sensitiveInfo;
    }

    public double getSensitiveRate() {
        return sensitiveRate;
    }

    public void setSensitiveRate(double sensitiveRate) {
        this.sensitiveRate = sensitiveRate;
    }

    public List<String> getExcludedUri() {
        return excludedUri;
    }

    public void setExcludedUri(List<String> excludedUri) {
        this.excludedUri = excludedUri;
    }
}
