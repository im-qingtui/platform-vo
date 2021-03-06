package im.qingtui.platform.web.vo;

import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * 返回列表用的vo
 * 不推荐使用了，此处 的list 未用data包裹，不再推荐使用
 * @author dongbin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor

@Deprecated
public class BaseListVo<T> extends BaseVo {

    private static final long serialVersionUID = 1L;
    private Integer totalCount;
    private Boolean hasMore;
    @NonNull
    private List<T> list;

    public static <T> BaseListVo<T> successData(List<T> list) {
        if (list == null) {
            return new BaseListVo<>(Collections.<T>emptyList());
        }
        val vo = new BaseListVo<T>(list);
        vo.setHasMore(false);
        vo.setTotalCount(list.size());
        return vo;
    }
}
