package im.qingtui.platform.web.utils;

import java.util.UUID;
import lombok.experimental.UtilityClass;

/**
 * 生成uuid的工具类
 *
 * @author dongbin
 */
@UtilityClass
public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
