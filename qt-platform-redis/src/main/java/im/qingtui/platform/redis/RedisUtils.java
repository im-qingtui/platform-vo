package im.qingtui.platform.redis;


import im.qingtui.platform.common.SysConfigPlaceholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Jeddy
 * @version 2016年3月24日  下午2:28:04
 */
public class RedisUtils {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String redisAddr;

    public String auth;

    /**
     * 轻推基本信息库
     */
    public static int defalut_db = 0;

    public static JedisPool pool;

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 10240;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 2000;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    public RedisUtils() {
        this.redisAddr = (String) SysConfigPlaceholder.getContextProperty("redis.host");
        this.auth = (String) SysConfigPlaceholder.getContextProperty("redis.auth");
        initConnetion();
    }

    public RedisUtils(String host,String auth) {
        this.redisAddr = host;
        this.auth = auth;
        initConnetion();
    }

    /**
     * 初始化jedis连接池
     */
    private void initConnetion() {

        for (int i = 1; pool == null; i++) {
            if (i % 50 == 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("Thread sleep异常", e);
                }
            }
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setMinEvictableIdleTimeMillis(1800000);
            config.setSoftMinEvictableIdleTimeMillis(1800000);

            if (pool == null) {
                pool = new JedisPool(config, redisAddr);
            } else if (pool.isClosed()) {
                pool = new JedisPool(config, redisAddr);
            }
        }
    }

    /**
     * 获取jedis连接
     */
    public synchronized Jedis getJedis() {
        Jedis resource = null;
        for (int i = 1; resource == null; i++) {
            if (i % 50 == 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error("Thread sleep异常", e);
                }
            }
            try {
                resource = pool.getResource();
                resource.auth(auth);
                return resource;
            } catch (Exception e) {
                log.error("获取jedis异常", e);
            }
        }
        return resource;
    }

    /**
     * 返回到连接池
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

}
