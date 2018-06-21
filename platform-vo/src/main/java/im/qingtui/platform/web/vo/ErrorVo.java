package im.qingtui.platform.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误时使用的vo
 *
 * @author dongbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVo extends BaseVo {

    /**
     * 后端业务内部错误码 可定位调用链路和发生异常服务
     */
    private long errorCode;

    public ErrorVo(long code, String message) {
        super(code, message);
    }

    public ErrorVo(long code, long errorCode, String message) {
        super(code, message);
        this.errorCode = errorCode;
    }

    public static ErrorVo error(long code, long errorCode, String message) {
        return new ErrorVo(code, errorCode, message);
    }

    public static ErrorVo error(long code, String message) {
        return new ErrorVo(code, message);
    }
}
