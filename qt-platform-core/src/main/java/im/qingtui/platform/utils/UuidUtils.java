package im.qingtui.platform.utils;

import java.util.UUID;

/**
 * 生成全球唯一uuid
 *
 * @author Peter
 */
public class UuidUtils {

    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().toUpperCase();
        return uuid.replaceAll("-", "");
    }
}
