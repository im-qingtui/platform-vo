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

    private static final BaseVo DEFAULT_SUCCESS = new BaseVo();

    private static final BaseVo DEFAULT_ERROR = new BaseVo(-1L, "system error");


    private static final long serialVersionUID = 1L;
    private static final Long DEFAULT_SUCCESS_CODE = 0L;

    private Long code = DEFAULT_SUCCESS_CODE;

    private String message;

    public static BaseVo error(long code, String message) {
        return new BaseVo(code, message);
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

    public void setCode(String code) {
        this.code = Long.valueOf(code);
    }

    public void setCode(int code) {
        this.code = (long) code;
    }

}
