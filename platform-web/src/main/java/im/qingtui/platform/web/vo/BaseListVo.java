package im.qingtui.platform.web.vo;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 返回列表用的vo
 *
 * @author dongbin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseListVo<T> extends BaseVo {

    private static final long serialVersionUID = 1L;
    private Integer totalCount;
    private Boolean hasMore;
    private List<T> list;

}
