package im.qingtui.platform.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author: hongya  Date: 16/7/22
 */
@Service
public class SpringFactory implements ApplicationContextAware {

    private static ApplicationContext context;

    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * 根据bean id 获取相应实体
     * 
     * @param id
     * @return
     */
    public static Object getObject(String id) {
        Object object = null;
        object = context.getBean(id);
        return object;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
