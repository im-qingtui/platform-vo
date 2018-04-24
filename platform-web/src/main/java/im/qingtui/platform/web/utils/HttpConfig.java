package im.qingtui.platform.web.utils;

import im.qingtui.platform.web.annotation.HttpElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by leesir on 2017/6/14.
 */
@NoArgsConstructor
@Data
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

    /**
     * 排除的url集合 如/static /resources等静态资源目录
     */
    private Set<String> excludedUri = new HashSet<String>();

    /**
     * 敏感参数集合 如密码 token等
     */
    private Set<SensitiveParam> sensitiveInfo = new HashSet<SensitiveParam>();

}
