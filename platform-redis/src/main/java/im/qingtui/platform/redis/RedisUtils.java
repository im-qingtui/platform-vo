package im.qingtui.platform.redis;


import im.qingtui.platform.common.SysConfigPlaceholder;
import im.qingtui.platform.constants.Global;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * redis工具类
 *
 * @author bowen
 */
@Slf4j
public class RedisUtils {

    private static String host;

    private static String auth;

    private static int port;

    private static int defaultDb;

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int maxActive;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int maxIdle;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int maxWait;

    private static JedisPool pool;

    static {
        host = SysConfigPlaceholder.getStringProperty("redis.host");
        auth = SysConfigPlaceholder.getStringProperty("redis.auth");
        port = SysConfigPlaceholder.getIntProperty("redis.port");
        maxActive = SysConfigPlaceholder.getIntProperty("redis.max.active");
        maxIdle = SysConfigPlaceholder.getIntProperty("redis.max.idle");
        maxWait = SysConfigPlaceholder.getIntProperty("redis.max.wait");
        defaultDb = SysConfigPlaceholder.getIntProperty("redis.default.db");
        initConnetion();
    }

    /**
     * 初始化jedis连接池
     */
    private static void initConnetion() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setMinEvictableIdleTimeMillis(1800000);
        config.setSoftMinEvictableIdleTimeMillis(1800000);
        pool = new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT, auth);
        log.info(Global.LOG_PLATFORM_PREFIX + "redis加载完成");

    }

    /**
     * 获取jedis连接
     */
    public static synchronized Jedis getJedis() {
        return getJedis(defaultDb);
    }

    /**
     * 获取jedis连接
     */
    public static synchronized Jedis getJedis(int db) {
        if (pool == null) {
            initConnetion();
        }
        Jedis jedis = pool.getResource();
        if (jedis == null) {
            throw new RuntimeException("get jedis faild.");
        }
        if (db > 0) {
            jedis.select(db);
        }
        return jedis;
    }

    /**
     * @deprecated {@link Jedis#close()} <p> 返回到连接池
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

}
