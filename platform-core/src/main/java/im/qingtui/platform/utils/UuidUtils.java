package im.qingtui.platform.utils;

import java.util.UUID;

/**
 *
 * 生成全球唯一uuid
 *
 * @author Peter
 */
public class UuidUtils {

    /**
     * @deprecated 请使用 {@link #getUpperUUID()}
     */
    public static String getUUID() {
        return getUpperUUID();
    }

    /**
     * 生成大写无-分隔uuid
     */
    public static String getUpperUUID(){
        String uuid = UUID.randomUUID().toString().toUpperCase();
        return uuid.replaceAll("-", "");
    }

    /**
     * 生成小写无-分隔uuid
     */
    public static String getLowerUUID(){
        String uuid = UUID.randomUUID().toString().toLowerCase();
        return uuid.replaceAll("-", "");
    }

}
