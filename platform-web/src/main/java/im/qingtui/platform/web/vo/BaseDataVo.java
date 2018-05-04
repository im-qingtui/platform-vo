package im.qingtui.platform.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
}
