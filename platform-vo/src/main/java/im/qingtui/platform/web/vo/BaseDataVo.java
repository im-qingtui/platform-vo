package im.qingtui.platform.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * 带返回数据的通用vo
 *
 * @author mengtian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseDataVo<T> extends BaseVo {

    private static final long serialVersionUID = 1L;
    private T data;

    public static BaseDataVo error(long code, String message) {
        val vo = new BaseDataVo<>();
        vo.setCode(code);
        vo.setMessage(message);
        return vo;
    }

    // 默认成功设置数据的返回对象
    public static <T> BaseDataVo<T> successData(T data) {
        return new BaseDataVo<T>(data);
    }
}
