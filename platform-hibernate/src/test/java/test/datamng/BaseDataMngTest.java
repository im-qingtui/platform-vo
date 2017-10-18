package test.datamng;

import entity.RootBean;
import im.qingtui.platform.hibernate.datamng.BaseDataMng;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sunny on 16/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/conf/core.xml", "classpath:/conf/platform-hibernate.xml"})
public class BaseDataMngTest {

    @Autowired
    private BaseDataMng baseDataMng;

    @Test
    public void saveRootBean() throws Exception {
        RootBean rb = new RootBean();
        rb.setUsername("u1");
        rb.setPassword("1");
        baseDataMng.saveObj(rb);
    }

    @Test
    public void getRootList() {
        System.out.println(baseDataMng.getAllObj("RootBean"));
    }

    @Test
    public void querySqlToLowerCase() throws Exception {
        String sql = "select * from RootBean";
        System.out.println(baseDataMng.querySqlToLowerCase(sql, null));
    }

    @Test
    public void querySql() throws Exception {
        String sql = "select * from RootBean";
        System.out.println(baseDataMng.querySql(sql, null));
    }

}
