package im.qingtui.platform.common;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import im.qingtui.platform.constants.Global;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.util.StringUtils;

/**
 * 属性文件配置读取类
 *
 * @author bowen
 */
@Slf4j
public class SysConfigPlaceholder extends PropertyPlaceholderConfigurer implements BeanDefinitionRegistryPostProcessor {

    private static Config apolloConfig = ConfigService.getAppConfig();
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
     * @deprecated 请使用 {@link #getStringProperty(String, String)}
     */
    public static Object getContextProperty(String key) {
        return getStringProperty(key, null, null);
    }

    /**
     * @deprecated 请使用 {@link #getStringProperty(String, String)}
     */
    public static String getStringProperty(String key) {
        return getStringProperty(key, null, null);
    }

    /**
     * @deprecated 请使用 {@link #getIntProperty(String, Integer)}
     */
    public static Integer getIntProperty(String key) {
        return getIntProperty(key, null, null);
    }

    /**
     * @deprecated 请使用 {@link #getLongProperty(String, Long)}
     */
    public static Long getLongProperty(String key) {
        return getLongProperty(key, null, null);
    }

    /**
     * @deprecated 请使用 {@link #getDoubleProperty(String, Double)}
     */
    public static Double getDoubleProperty(String key) {
        return getDoubleProperty(key, null, null);
    }

    /**
     * @deprecated 请使用 {@link #getBooleanProperty(String, Boolean)}
     */
    public static Boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, null, null);
    }

    public static String getStringProperty(String key, String defaultValue) {
        return getStringProperty(key, defaultValue, null);
    }

    public static Integer getIntProperty(String key, Integer defaultValue) {
        return getIntProperty(key, defaultValue, null);
    }

    public static Long getLongProperty(String key, Long defaultValue) {
        return getLongProperty(key, defaultValue, null);
    }

    public static Double getDoubleProperty(String key, Double defaultValue) {
        return getDoubleProperty(key, defaultValue, null);
    }

    public static Boolean getBooleanProperty(String key, Boolean defaultValue) {
        return getBooleanProperty(key, defaultValue, null);
    }


    /**
     * 根据spring value 类型的key 配置获取配置的值
     * @param valueStr like "${systemProperties.myProp}".
     * @return 配置信息
     */
    public static String getPropertyBySpringValue(String valueStr){
        if (StringUtils.isEmpty(valueStr)||!valueStr.startsWith("${")||!valueStr.endsWith("}")){
            log.warn("错误的配置项{}",valueStr);
            return "";
        }
        if (valueStr.contains(":")){
            String key=valueStr.substring(2,valueStr.indexOf(":"));
            String defaultValue=valueStr.substring(valueStr.indexOf(":"),valueStr.length()-1);
            return getStringProperty(key,defaultValue);
        }else {
            String key=valueStr.substring(2,valueStr.length()-1);
            return getStringProperty(key,"");
        }
    }


    public static String getStringProperty(String key, String defaultValue, String nameSpace) {
        String value;
        if (StringUtils.isEmpty(nameSpace)) {
            value = apolloConfig.getProperty(key, defaultValue);
        } else {
            Config config = ConfigService.getConfig(nameSpace);
            value = config.getProperty(key, defaultValue);
        }
        if (value == null) {
            value = (String) ctxPropertiesMap.get(key);
            if (StringUtils.isEmpty(value)) {
                value = defaultValue;
            }
        }
        return value;
    }

    public static Integer getIntProperty(String key, Integer defaultValue, String nameSpace) {
        Integer value;
        if (StringUtils.isEmpty(nameSpace)) {
            value = apolloConfig.getIntProperty(key, defaultValue);
        } else {
            Config config = ConfigService.getConfig(nameSpace);
            value = config.getIntProperty(key, defaultValue);
        }
        if (value == null) {
            String valueStr = (String) ctxPropertiesMap.get(key);
            if (valueStr == null) {
                value = defaultValue;
            } else {
                value = Integer.parseInt(valueStr);
            }
        }
        return value;
    }

    public static Long getLongProperty(String key, Long defaultValue, String nameSpace) {
        Long value;
        if (StringUtils.isEmpty(nameSpace)) {
            value = apolloConfig.getLongProperty(key, defaultValue);
        } else {
            Config config = ConfigService.getConfig(nameSpace);
            value = config.getLongProperty(key, defaultValue);
        }
        if (value == null) {
            String valueStr = (String) ctxPropertiesMap.get(key);
            if (valueStr == null) {
                value = defaultValue;
            } else {
                value = Long.parseLong(valueStr);
            }
        }
        return value;
    }

    public static Double getDoubleProperty(String key, Double defaultValue, String nameSpace) {
        Double value;
        if (StringUtils.isEmpty(nameSpace)) {
            value = apolloConfig.getDoubleProperty(key, defaultValue);
        } else {
            Config config = ConfigService.getConfig(nameSpace);
            value = config.getDoubleProperty(key, defaultValue);
        }
        if (value == null) {
            String valueStr = (String) ctxPropertiesMap.get(key);
            if (valueStr == null) {
                value = defaultValue;
            } else {
                value = Double.parseDouble(valueStr);
            }
        }
        return value;
    }

    public static Boolean getBooleanProperty(String key, Boolean defaultValue, String nameSpace) {
        Boolean value;
        if (StringUtils.isEmpty(nameSpace)) {
            value = apolloConfig.getBooleanProperty(key, defaultValue);
        } else {
            Config config = ConfigService.getConfig(nameSpace);
            value = config.getBooleanProperty(key, defaultValue);
        }
        if (value == null) {
            String valueStr = (String) ctxPropertiesMap.get(key);
            if (valueStr == null) {
                value = defaultValue;
            } else {
                value = Boolean.parseBoolean(valueStr);
            }
        }
        return value;
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }
}
