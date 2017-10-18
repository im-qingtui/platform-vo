package im.qingtui.platform.common;

import im.qingtui.platform.constants.Global;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性文件配置读取类
 *
 * @author bowen
 */
@Slf4j
public class SysConfigPlaceholder extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
        log.info(Global.LOG_PLATFORM_PREFIX + "全局配置加载完成");
        super.processProperties(beanFactoryToProcess, props);
    }

    /**
     * @deprecated 请使用 {@link #getStringProperty(String)}
     */
    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public static String getStringProperty(String key) {
        return (String) ctxPropertiesMap.get(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getStringProperty(key));
    }

    public static long getLongProperty(String key) {
        return Long.parseLong(getStringProperty(key));
    }

    public static double getDoubleProperty(String key) {
        return Double.parseDouble(getStringProperty(key));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getStringProperty(key));
    }

}
