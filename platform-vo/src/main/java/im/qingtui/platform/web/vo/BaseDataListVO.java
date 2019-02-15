package im.qingtui.platform.web.vo;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseDataListVO<T> extends BaseDataVo<ListVO<T>> {

    public BaseDataListVO(ListVO<T> data) {
        super(data);
    }

    public BaseDataListVO(List<T> list) {
        super.setData(ListVO.successData(list));
    }

    /**
     * 默认成功设置数据的返回对象
     */
    public static <T> BaseDataListVO<T> successData(List<T> list) {
        return new BaseDataListVO<>(ListVO.successData(list));
    }
}
