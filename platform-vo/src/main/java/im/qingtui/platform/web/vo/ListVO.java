package im.qingtui.platform.web.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * 用于装载标准list vo的返回 此 list 可以包裹在 {@link BaseDataListVO}
 *
 * @param <T> list中的泛型
 * @author dongbin
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ListVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer totalCount;
    private Boolean hasMore;
    @NonNull
    private List<T> list;

    public static <T> ListVO<T> successData(List<T> list) {
        if (list == null) {
            return new ListVO<>(Collections.<T>emptyList());
        }
        val vo = new ListVO<T>(list);
        vo.setHasMore(false);
        vo.setTotalCount(list.size());
        return vo;
    }
}
