package im.qingtui.platform.web.vo;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回数据基类
 *
 * @author qizi Created by qizi on 2017/10/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long DEFAULT_SUCCESS_CODE = 0L;

    private static final BaseVo DEFAULT_SUCCESS = new BaseVo(DEFAULT_SUCCESS_CODE);

    private static final BaseVo DEFAULT_ERROR = new BaseVo(-1L, "system error");

    private long code = DEFAULT_SUCCESS_CODE;

    private String message;

    public static BaseVo error(long code, String message) {
        return new BaseVo(code, message);
    }

    public BaseVo(long code) {
        this.code = code;
    }

    public static BaseVo defaultError() {
        return DEFAULT_ERROR;
    }

    public static BaseVo defaultSuccess() {
        return DEFAULT_SUCCESS;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = Long.valueOf(code);
    }

    public void setCode(int code) {
        this.code = (long) code;
    }
}
