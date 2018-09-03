package redis.demo2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 主要用来给用户提供一个设计完备的，通过jedis的jar包来管理redis内存数据库的各种方法
 */
public class RedisManager {

	private static Logger log_error = Logger.getLogger("error");

	// ip、port、AUTH属性都定义在了config.properties文件中
	private static String host = CfgReader.getParam("REDIS_HOST");
	private static int port = Integer.parseInt(CfgReader.getParam("REDIS_PORT"));

	// 访问密码
	private static String AUTH = CfgReader.getParam("REDIS_AUTH");

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 60000;

	private static int TIMEOUT = 60000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	// 设置为0的话就是永远都不会过期
	private static int expire = 0;

	// 定义一个管理池，所有的redisManager共同使用。
	private static JedisPool jedisPool = null;

	public RedisManager() {}

	/**
	 * 
	 * 初始化方法,在这个方法中通过host和port来初始化jedispool。
	 * 
	 */
	public static void init() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			if (RegexUtil.isEmpty(AUTH)) {
				jedisPool = new JedisPool(config, host, port, TIMEOUT, AUTH);
			} else {
				jedisPool = new JedisPool(config, host, port, TIMEOUT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_error.error("-- Redis 连接出错--host=" + host + ",port=" + port + ",错误原因" + e);
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				resource.select(1); // 设置为第二个库（db1）
				return resource;
			} else {
				MSGSendUtils.getInstance()
					.sendMSG(CfgReader.getParam("error_name"), CfgReader.getParam("error_phone"),"获取的Redis链接为null");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			MSGSendUtils.getInstance()
				.sendMSG(CfgReader.getParam("error_name"), CfgReader.getParam("error_phone"),"获取Redis链接出错：" + e.getMessage());
			return null;
		}
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public static byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = getJedis();
		try {
			value = jedis.get(key);
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * get value from redis
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = getJedis();
		try {
			value = jedis.get(key);
		} finally {
			jedis.close();
		}
		return value;
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static void set(byte[] key, byte[] value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static void set(String key, String value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public static void set(byte[] key, byte[] value, int expire) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * set
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public static void set(String key, String value, int expire) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			jedis.close();
		}
	}

	/**
	 * del
	 * 
	 * @param key
	 */
	public static void del(byte[] key) {
		Jedis jedis = getJedis();
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * del
	 * 
	 * @param key
	 */
	public static void del(String key) {
		Jedis jedis = getJedis();
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}
	}

	/**
	 * 判断指定键是否存在
	 * 
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		Jedis jedis = getJedis();
		boolean flag = jedis.exists(key);
		jedis.close();
		return flag;
	}

	/**
	 * 获取key对应的值剩余存活时间
	 * 
	 * @param key
	 * @return 正数：剩余的时间(秒) 负数：已过期
	 */
	public static Long ttlKey(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.ttl(key);
		} catch (Exception e) {
			log_error.error(" -- Redis 获取key对应的值剩余存活时间出错，出错原因：" + e);
			e.printStackTrace();
			return 0L;
		} finally {
			jedis.close();
		}
	}

	/**
	 * 获取key对应的值剩余存活时间
	 * 
	 * @param key
	 * @return 正数：剩余的时间(秒) 负数：已过期
	 */
	public static Long ttlKey(byte[] key) {
		Jedis jedis = getJedis();
		try {
			return jedis.ttl(key);
		} catch (Exception e) {
			log_error.error(" -- Redis 获取key对应的值剩余存活时间出错，出错原因：" + e);
			e.printStackTrace();
			return 0L;
		} finally {
			jedis.close();
		}
	}

	/**
	 * 存储对象
	 * 
	 * @param key
	 * @param obj
	 * @param expire
	 */
	public static void setObject(String key, Object obj, int expire) {
		byte[] data = ObjTOSerialize(obj);
		Jedis jedis = getJedis();
		jedis.set(key.getBytes(), data);
		if (expire != 0) {
			jedis.expire(key, expire);
		}
		jedis.close();
	}

	/**
	 * 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObject(String key) {
		Jedis jedis = getJedis();
		byte[] data = jedis.get(key.getBytes());
		Object obj = null;
		if (data != null) {
			obj = unSerialize(data);
		}
		jedis.close();
		return obj;
	}

	/**
	 * 
	 * 序列化一个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] ObjTOSerialize(Object obj) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream byteOut = null;
		try {
			byteOut = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(byteOut);
			oos.writeObject(obj);
			byte[] bytes = byteOut.toByteArray();
			return bytes;
		} catch (Exception e) {
			log_error.error("-- Redis序列化对象出错：" + e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化一个对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unSerialize(byte[] bytes) {
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(bytes);
			ObjectInputStream objIn = new ObjectInputStream(in);
			return objIn.readObject();
		} catch (Exception e) {
			log_error.error("-- Redis反序列化对象出错：" + e);
			e.printStackTrace();
		}
		return null;
	}

}
