package im.qingtui.platform.web.vo;

import java.util.List;

/**
 * list相关vo
 *
 * @author dongbin
 */
public class BaseResultListVo<T> extends BaseDataVo<ResultListVo<T>> {

    public BaseResultListVo(List<T> list) {
        super(new ResultListVo<>(list));
    }

    public BaseResultListVo() {
        super(new ResultListVo<T>());
    }

    // 默认成功设置数据的返回对象
    public static <T> BaseResultListVo<T> successData(List<T> list) {
        return new BaseResultListVo<T>(list);
    }

    // 返回空list
    public static <T> BaseResultListVo<T> emptyList() {
        return new BaseResultListVo<T>();
    }
}
