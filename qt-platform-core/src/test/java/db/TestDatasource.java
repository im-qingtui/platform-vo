package db;

import im.qingtui.platform.common.SpringFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author: hongya  Date: 16/7/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/conf/core.xml")
public class TestDatasource {
    TestDao testDao;

    @Before
    public void init() {
        testDao = new TestDao();
        DataSource dataSource = (DataSource) SpringFactory.getObject("basicDataSource");
        testDao.setDataSource(dataSource);
    }

    @Test
    public void testCreateTable() throws SQLException {
        testDao.createTable();
    }

    @Test
    public void testInsert() throws Exception {
        testDao.insert();
    }

    @Test
    public void testDropTable() throws Exception {
        testDao.tearDown();
    }
}
