package im.qingtui.platform.sensitive;

import java.lang.annotation.*;

/**
 * 敏感信息
 *
 * @author leesir
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface SensitiveInfo {

    /**
     * 默认隐藏比率
     */
    double DEFAULT_RATE = 0.3;

    SensitiveLevel value() default SensitiveLevel.HIDE;

    /**
     * 隐藏的比率
     */
    double rate() default DEFAULT_RATE;
}
