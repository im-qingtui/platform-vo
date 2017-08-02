package datamng;

import entity.UserEntity;
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
@ContextConfiguration(locations = {"classpath*:/conf/core.xml", "classpath:/conf/qt-platform-mybatis.xml", "classpath:/conf/mybatis-test.xml"})
public class MapperTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveUser() throws Exception {
        UserEntity ue = new UserEntity();
        ue.setUsername("u1");
        ue.setPassword("1");
        ue.setCreatetime(System.currentTimeMillis());
        ue.setUpdatetime(System.currentTimeMillis());
        userService.saveUser(ue);
    }

    @Test
    public void getUserList() {
        System.out.println(userService.getUserList());
    }

}
