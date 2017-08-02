package im.qingtui.platform.hibernate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Template工厂类，根据dbId获取JDBCTemplate和HibernateTemplate
 *
 * @author Peter
 * @version 2014-3-3
 */
public class TemplateFactory {

    public static final String defaultDB = "hibernateSessionFactory";
    public static final String defaultDatasource = "defaultDatasource";

    /**
     * 获取默认的HibernateTemplate
     */
    public static HibernateTemplate getDefaultHibernateTemplate() {
        return im.qingtui.platform.hibernate.dao.HibernateTemplateManger.getInstance().getHibernateTemplateById(null);
    }

    /**
     * 根据dbId(sessionFactoryId)获取HibernateTemplate
     */
    public static HibernateTemplate getHibernateTemplate(String dbId) {
        return HibernateTemplateManger.getInstance().getHibernateTemplateById(dbId);
    }

    /**
     * 获取默认JdbcTemplate
     */
    public static JdbcTemplate getDefaultJDBCTemplate() {
        return JDBCTemplateManager.getInstance().getTemplate(null);
    }

    /**
     * 根据datasourceId获取JdbcTemplate
     */
    public static JdbcTemplate getJDBCTemplate(String datasourceId) {
        return JDBCTemplateManager.getInstance().getTemplate(datasourceId);
    }

}
