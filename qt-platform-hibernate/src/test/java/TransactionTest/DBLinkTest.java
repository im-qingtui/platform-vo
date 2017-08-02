package TransactionTest;

import im.qingtui.platform.hibernate.dao.TemplateFactory;
import java.sql.Statement;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sunny on 16/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/conf/core.xml", "classpath*:/conf/qt-platform-hibernate.xml"})
public class DBLinkTest {

    private static Logger logger = LoggerFactory.getLogger(DBLinkTest.class);

    // @Transactional(value = "jtaTransactionManager")
    @org.junit.Test
    public void connTest() throws Exception {
        Session session = TemplateFactory.getDefaultHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createQuery("from User ");
        query.list();
        // Statement statement = session.connection().createStatement();
        Statement statement = TemplateFactory.getJDBCTemplate("CTP").getDataSource().getConnection().createStatement();
        // statement.execute("select * from ctp_user");
//        statement.execute("Alter session close database link " + "MCCPRD_DBLINK");
        // statement.close();
        // session.close();
    }
}
