package im.qingtui.platform.web.vo;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对象列表结果集返回类
 *
 * @author mengtian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultListVo<T> implements Serializable{

    private List<T> resultList;

}
