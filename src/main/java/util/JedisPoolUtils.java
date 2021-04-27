package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jy
 * @date 2021年04月16日 10:31
 * JedisPool工具类
 * 加载配置文件，配置获取连接池的参数
 * 提供获取连接的方法
 */
public class JedisPoolUtils {

    //静态变量JedisPool
    private static JedisPool jedisPool;

    //加载配置文件
    static {
        //读取配置文件
        InputStream is = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        //创建Properties对象
        Properties pro = new Properties();
        //关联文件
        try {
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));
        //初始化JedisPool
        jedisPool = new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));
    }
    /**
     * 获取连接方法
     * @return
     */
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
