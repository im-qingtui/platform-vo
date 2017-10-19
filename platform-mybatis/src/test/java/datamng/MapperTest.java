package datamng;

import entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.UserService;

/**
 * Created by sunny on 16/7/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/META-INF/spring/*.xml", "classpath:spring-mybatis.xml"})
public class MapperTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveUser() throws Exception {
        User ue = new User();
        ue.setName("u1");
        ue.setPassword("1");
        ue.setCreateTime(System.currentTimeMillis());
        ue.setUpdateTime(System.currentTimeMillis());
        userService.saveUser(ue);
    }

}
