package test.datamng;

import entity.EntityBean;
import im.qingtui.platform.hibernate.datamng.BaseEntityMng;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sunny on 16/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/conf/core.xml", "classpath:/conf/platform-hibernate.xml"})
public class BaseEntityMngTest {

    @Autowired
    private BaseEntityMng baseEntityMng;

    @Test
    public void querySql() throws Exception {
        String sql = "select * from t_big";
        System.out.println(baseEntityMng.querySql(sql, null));
    }

    @Test
    public void saveEntity() throws Exception {
        EntityBean newBean = new EntityBean();
        newBean.setId("2");
        newBean.setUsername("u2");
        newBean.setPassword("1");
        System.out.println(baseEntityMng.saveObj(newBean));
    }

    @Test
    @Transactional
    public void saveTwoEntity() throws Exception {
        EntityBean newBean = new EntityBean();
        newBean.setId("1");
        newBean.setUsername("u1");
        newBean.setPassword("1");
        EntityBean newBean2 = new EntityBean();
        newBean2.setId("3");
        newBean2.setUsername("u3");
        newBean2.setPassword("1");
        System.out.println(baseEntityMng.saveObj(newBean));
        System.out.println(baseEntityMng.saveObj(newBean2));
        throw new RuntimeException();
    }

    @Test
    public void getEntity() throws Exception {
        List<EntityBean> entityList = baseEntityMng.getAllObj(EntityBean.class);
        System.out.println(entityList);
    }

}
