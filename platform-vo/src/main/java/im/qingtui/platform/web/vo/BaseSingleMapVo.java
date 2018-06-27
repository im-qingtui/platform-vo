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

    // 默认成功设置数据的返回对象
    public static <T> BaseSingleMapVo<T> successData(String key, T data) {
        return new BaseSingleMapVo<T>( key,  data);
    }
}
