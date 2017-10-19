package utils;

import im.qingtui.platform.utils.UuidUtils;

/**
 * uuid工具类测试
 *
 * @author bowen
 */
public class UuidUtilsTest {

    public static void main(String[] args) {
        System.out.println("大写UUID -> " + UuidUtils.getUUID());
        System.out.println("大写UUID -> " + UuidUtils.getUpperUUID());
        System.out.println("小写UUID -> " + UuidUtils.getLowerUUID());
    }

}