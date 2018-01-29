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
public class RedisUtilsTest {

    @Test
    public void getJedis() {
        Jedis jedis = RedisUtils.getJedis();
        String key = "testkey";
        String value = "testvalue";
        jedis.set(key, value);
        String redisValue = jedis.get(key);
        System.out.println(value + "   " + redisValue);
        jedis.del(key);
        jedis.close();
    }

}