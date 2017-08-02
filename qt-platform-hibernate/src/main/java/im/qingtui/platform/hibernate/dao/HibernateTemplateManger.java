package im.qingtui.platform.hibernate.dao;

import im.qingtui.platform.common.SpringFactory;
import im.qingtui.platform.constants.Global;
import java.util.HashMap;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * HibernateTemplate工厂类，根据dbId获取HibernateTemplate，并提供获取和设置defaultDB的方法
 *
 * @author Peter
 * @version 2014-3-3
 */
public class HibernateTemplateManger {

    private static Logger logger = LoggerFactory.getLogger(HibernateTemplateManger.class);

    private static HashMap<String, HibernateTemplate> templates = new HashMap<String, HibernateTemplate>();

    private static HibernateTemplateManger instance = null;

    public static HibernateTemplateManger getInstance() {
        if (instance == null) {
            instance = new HibernateTemplateManger();
        }
        return instance;
    }

    protected HibernateTemplate getHibernateTemplateById(String dbId) {
        if (dbId == null || "".equals(dbId.trim())) {
            dbId = TemplateFactory.defaultDB;
        }
        if (templates.get(dbId) == null) {
            cacheHibernateTemplate(dbId);
        }
        logger.debug(Global.LOG_PLATFORM_PREFIX + "得到HibernateTemplate {}", dbId);
        return templates.get(dbId);
    }

    private SessionFactory getSessionFactoryById(String dbId) {
        return (SessionFactory) SpringFactory.getObject(dbId);
    }

    private synchronized void cacheHibernateTemplate(String dbId) {
        SessionFactory sessionFactory = getSessionFactoryById(dbId);
        HibernateTemplate ht = new HibernateTemplate();
        ht.setSessionFactory(sessionFactory);
        templates.put(dbId, ht);
    }

}
