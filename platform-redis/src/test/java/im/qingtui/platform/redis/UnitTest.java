package im.qingtui.platform.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * 单元测试
 *
 * @author bowen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/META-INF/spring/*.xml"})
public class UnitTest {

    @Test
    public void getJedis(){
        Jedis jedis=RedisUtils.getJedis();
        System.out.printf("test");
    }

}