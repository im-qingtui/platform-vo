package im.qingtui.platform.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * spring 上下文
 *
 * @author bowen
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
     */
    public static <T> T getObject(String id) {
        Object object = context.getBean(id);
        return (T)object;
    }

    /**
     * 获取spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
