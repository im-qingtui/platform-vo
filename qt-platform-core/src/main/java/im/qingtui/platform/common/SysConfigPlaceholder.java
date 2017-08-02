package im.qingtui.platform.common;

import im.qingtui.platform.constants.Global;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author: hongya  Date: 16/7/22
 */
public class SysConfigPlaceholder extends PropertyPlaceholderConfigurer {

    private Logger logger = LoggerFactory.getLogger(SysConfigPlaceholder.class);

    private static Map<String, Object> ctxPropertiesMap = new HashMap<String, Object>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
        logger.info(Global.LOG_PLATFORM_PREFIX + "全局配置加载完成");
        super.processProperties(beanFactoryToProcess, props);
    }

    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

}
