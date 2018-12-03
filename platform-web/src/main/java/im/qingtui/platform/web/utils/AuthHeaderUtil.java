package im.qingtui.platform.web.utils;

import com.alibaba.fastjson.JSON;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * 权限header工具类
 *
 * @author mengtian
 */
@UtilityClass
public final class AuthHeaderUtil {


    private static final String HEAD_BEAR = "Bearer ";

    /**
     * 通过authHeader获取accessToken
     */
    public static String getAccessTokenFromAuthHeader(String authHeaderString) {
        if (StringUtils.isEmpty(authHeaderString)) {
            return "";
        }
        return authHeaderString.replace(HEAD_BEAR, "");
    }

    /**
     * 通过请求对象获取AccessToken
     */
    public static String getAccessTokenFromAuthHeader(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authHeader)) {
            return "";
        }

        return authHeader.replace(HEAD_BEAR, "");
    }

    /**
     * 通过accessToken中获取accountId
     */
    public static String getAccountIdFromAccessToken(String accessToken) {

        TokenInfo tokenInfo = getTokenInfoFromAccessToken(accessToken);
        if (tokenInfo == null) {
            return "";
        }
        return tokenInfo.getAid();
    }

    /**
     * 获取设备类型
     */
    public static String getDeviceType(String accessToken) {

        TokenInfo tokenInfo = getTokenInfoFromAccessToken(accessToken);
        if (tokenInfo == null) {
            return "";
        }
        return tokenInfo.getType();
    }

    /**
     * 通过accessToken获取TokenInfo
     */
    private static TokenInfo getTokenInfoFromAccessToken(String accessToken) {

        String info = "";
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        String[] token = accessToken.split("\\.");

        if (token.length >= 2) {
            Base64 base64 = new Base64();
            info = new String(base64.decode(token[1].getBytes()));
        }
        return JSON.parseObject(info, TokenInfo.class);
    }

    /**
     * 通过accessToken获取domainId
     */
    public static String getDomainIdByAccessToken(String accessToken) {

        String info = "";

        if (StringUtils.isEmpty(accessToken)) {
            return info;
        }

        String[] token = accessToken.split("\\.");

        if (token.length >= 4) {
            Base64 base64 = new Base64();
            return new String(base64.decode(token[3].getBytes()));
        }
        return info;
    }

    /**
     * 内部类，解析accessToken的类
     */
    @Data
    private static class TokenInfo {

        private String type; //设备类型
        private String expires; //过期时间
        private String aid; //用户的账号id
    }


}
