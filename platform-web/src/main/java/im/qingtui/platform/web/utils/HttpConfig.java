package im.qingtui.platform.web.utils;

import im.qingtui.platform.web.annotation.HttpElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Set<String> excludedUri = new HashSet<String>();

    private Set<SensitiveParam> sensitiveInfo = new HashSet<SensitiveParam>();

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

    public Set<SensitiveParam> getSensitiveInfo() {
        return sensitiveInfo;
    }

    public void setSensitiveInfo(Set<SensitiveParam> sensitiveInfo) {
        this.sensitiveInfo = sensitiveInfo;
    }

    public Set<String> getExcludedUri() {
        return excludedUri;
    }

    public void setExcludedUri(Set<String> excludedUri) {
        this.excludedUri = excludedUri;
    }
}
