package im.qingtui.open.platform.team.api.utils;


import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * 加解密工具
 *
 * @author yiya
 */
public class EncryptDecrypt {

    private static final Map<Character, Character> encodeMap = new HashMap<Character, Character>();
    private static final Map<Character, Character> decodeMap = new HashMap<Character, Character>();

    static {
        encodeMap.put('0', '1');
        encodeMap.put('1', '2');
        encodeMap.put('2', '3');
        encodeMap.put('3', '4');
        encodeMap.put('4', '5');
        encodeMap.put('5', '6');
        encodeMap.put('6', '7');
        encodeMap.put('7', '8');
        encodeMap.put('8', '9');
        encodeMap.put('9', 'a');
        encodeMap.put('a', 'b');
        encodeMap.put('b', 'c');
        encodeMap.put('c', 'd');
        encodeMap.put('d', 'e');
        encodeMap.put('e', 'f');
        encodeMap.put('f', '0');

        decodeMap.put('0', 'f');
        decodeMap.put('1', '0');
        decodeMap.put('2', '1');
        decodeMap.put('3', '2');
        decodeMap.put('4', '3');
        decodeMap.put('5', '4');
        decodeMap.put('6', '5');
        decodeMap.put('7', '6');
        decodeMap.put('8', '7');
        decodeMap.put('9', '8');
        decodeMap.put('a', '9');
        decodeMap.put('b', 'a');
        decodeMap.put('c', 'b');
        decodeMap.put('d', 'c');
        decodeMap.put('e', 'd');
        decodeMap.put('f', 'e');

    }

    public static String encode(String content) {
        return changeChar(content, encodeMap);
    }

    public static String decode(String password) {
        return changeChar(password, decodeMap);
    }

    private static String changeChar(String content, Map<Character, Character> map) {
        if (StringUtils.isBlank(content)) {
            return "";
        } else {
            char[] contentChars = content.toCharArray();
            char[] result = new char[contentChars.length];
            int i = 0;
            for (char c : contentChars) {
                if (map.containsKey(c)) {
                    result[i] = map.get(c);
                } else {
                    result[i] = c;
                }
                i++;
            }
            return new String(result);
        }
    }

}
