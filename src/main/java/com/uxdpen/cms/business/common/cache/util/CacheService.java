package com.uxdpen.cms.business.common.cache.util;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.uxdpen.cms.business.common.constants.RedisConstants;

/**
 * <p>
 * Cache service
 * 缓存Service
 * </p>
 * 
 * @author flywind(飞风)
 * @date 2016年1月8日
 * @网址：http://www.flywind.org
 * @QQ技术群：41138107(人数较多最好先加这个)或33106572
 * @since 1.0
 */
public class CacheService {
	
	/**
	 * Singleton class
	 * 单例类
	 */
	private static final CacheService cacheService = new CacheService();
	
	public static RedisTemplate<String, Object> redisTemplate;
	
	private static ValueOperations<String, Object> valueOper;
	
	/**
	 * The construction method is private, to achieve a single case
	 * 将构造方法私有化，实现单例
	 */
	@SuppressWarnings("unchecked")
	private CacheService() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-redis.xml");
		redisTemplate = (RedisTemplate<String, Object>) context.getBean(RedisConstants.REDIS_TEMPLATE_BEAN);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		
		valueOper = redisTemplate.opsForValue();
	}
	
	/**
	 * Get CacheService instance
	 * 获得CacheService实例
	 * 
	 * @return CacheService instance
	 *         CacheService实例
	 */
	public static CacheService getInstance() {
		return cacheService;
	}
	
	/**
	 * To determine whether there is the keyword REDIS
	 * 判断REDIS中是否存在该关键字
	 * 
	 * @param key
	 *        Cache key
	 * @return true:exist，false:never existed
	 *         true:存在，false:不存在
	 */
	public boolean sessionExists(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**
	 * Save the cache key and the corresponding value to the cache, while setting the data timeout
	 * 保存缓存key和对应的值到缓存，同时设置数据超时时间
	 * 
	 * @param key
	 *        Cache key
	 * @param value
	 *        Cache value
	 * @param timeoutSecends
	 *        Timeout period (SEC)
	 */
	public void save(String key, Object value, long timeoutSecends) {
		valueOper.set(key, value, timeoutSecends, TimeUnit.SECONDS);
	}
	
	/**
	 * Save the cache key and the corresponding value to the cache, while setting the data timeout
	 * 保存缓存key和对应的值到缓存，同时设置数据超时时间
	 * 
	 * @param key
	 *        Cache key
	 * @param value
	 *        Cache value
	 */
	public void save(String key, Object value) {
		valueOper.set(key, value);
	}
	
	/**
	 * Clear cache
	 * 清除缓存
	 * 
	 * @param key
	 *        Cache key
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	/**
	 * Get the corresponding value from the key to the cache
	 * 根据key到缓存中获取对应的值
	 * 
	 * @param key
	 *        Cache key
	 * @return Object
	 * 	       Value
	 */
	public Object get(String key) {
		Object value = null;
		if (sessionExists(key)) {
			value = valueOper.get(key);
			valueOper.set(key, value);
		}
		return value;
	}

}
