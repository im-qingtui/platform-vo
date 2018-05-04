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
    private static final Long DEFAULT_SUCCESS_CODE = 200L;

    private Long code = DEFAULT_SUCCESS_CODE;

    private String message;


    public static BaseVo defaultError() {
        return new BaseVo(-1L, "system error");
    }

    public static BaseVo defaultSuccess() {
        return new BaseVo();
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = Long.valueOf(code);
    }

}
