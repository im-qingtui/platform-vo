package im.qingtui.platform.web.vo;

import java.util.Collections;
import java.util.Map;

/**
 * map使用的vo
 *
 * @author dongbin
 */
public class BaseSingleMapVo<T> extends BaseDataVo<Map<String, T>> {

    public BaseSingleMapVo(String key, T data) {
        super(Collections.singletonMap(key, data));
    }
}
