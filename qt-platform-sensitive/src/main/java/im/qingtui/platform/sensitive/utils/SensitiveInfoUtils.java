package im.qingtui.platform.sensitive.utils;

/**
 * 过滤敏感信息工具
 *
 * @author leesir
 */
public class SensitiveInfoUtils {

    /**
     * 隐藏部分信息
     * @param object 被部分隐藏信息的对象
     * @param rate 被隐藏的比例
     * @return 隐藏部分信息后的字符串
     */
    public static String incomplete(Object object,double rate){
        StringBuilder resultString = new StringBuilder();
        if(object==null){
            resultString.append("****");
        }else {
            String objString = object.toString();
            resultString.append(objString);
            int strLength = objString.length();
            //处理rate的传值 保证在[0,1]之间
            rate = Math.min(1.0,rate);
            rate = Math.max(0,rate);
            //被隐藏的字符长度
            int hideLength = (int)Math.ceil(strLength*rate);
            //开始打码的位置 这里打码策略始终是原字符串的中间
            int startIndex = (strLength-hideLength)/2;
            StringBuilder mark = new StringBuilder();
            for(int i=0;i<hideLength;i++){
                mark.append("*");
            }
            resultString.replace(startIndex,startIndex+hideLength,mark.toString());
        }
        return resultString.toString();
    }
}
