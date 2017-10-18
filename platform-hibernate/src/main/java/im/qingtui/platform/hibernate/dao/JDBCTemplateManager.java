package im.qingtui.platform.hibernate.dao;

import im.qingtui.platform.common.SpringFactory;
import im.qingtui.platform.constants.Global;
import java.util.HashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Sping JDBCTemplate工厂类，根据dbId获取JDBCTemplate
 *
 * @author Peter
 * @version 2014-3-3
 */
public class JDBCTemplateManager {

    private static Logger logger = LoggerFactory.getLogger(JDBCTemplateManager.class);

    private HashMap<String, JdbcTemplate> jdbcTemplates = new HashMap<String, JdbcTemplate>();

    private static JDBCTemplateManager instance = null;

    private JDBCTemplateManager() {
    }

    /**
     * 静态实例化方法，只会生成一个JDBCTemplateManager实例
     */
    static public JDBCTemplateManager getInstance() {
        if (instance == null) {
            instance = new JDBCTemplateManager();
        }
        return instance;

    }

    /**
     * 获取默认JDBCTemplate
     */
    public JdbcTemplate getTemplate() {
        return getTemplate(null);
    }

    /**
     * 根据datasourceId获取JDBCTemplate
     */
    public JdbcTemplate getTemplate(String datasourceId) {
        if (datasourceId == null) {
            datasourceId = TemplateFactory.defaultDatasource;
        }
        JdbcTemplate jt = jdbcTemplates.get(datasourceId);
        if (jt == null) {
            jt = createTemplate(datasourceId);
        }
        logger.debug(Global.LOG_PLATFORM_PREFIX + "得到JdbcTemplate：" + datasourceId);
        return jt;
    }

    private synchronized JdbcTemplate createTemplate(String datasourceId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String dataSourceName = datasourceId;
        DataSource dataSource = (DataSource) SpringFactory.getObject(dataSourceName);
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplates.put(datasourceId, jdbcTemplate);
        return jdbcTemplate;
    }
}
