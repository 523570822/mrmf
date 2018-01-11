package com.osg.framework.web.cache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.osg.framework.web.cache.CacheConstant.CacheEnum;

public class CacheManagerImpl implements CacheManager {

	private static Logger logger = LoggerFactory.getLogger(CacheManagerImpl.class);

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	private static final Map<String, Object> cacheMap = new HashMap<String, Object>();

    public void save(String key, Object entity) {
		save(key, entity, null);
	}

	private void save(String key, Object entity, Integer expired) {
		if (redisTemplate != null)
			redisTemplate.opsForHash().put(key, key, entity);
		cacheMap.put(key, entity);
	}

	public Object get(String key) {
		Object entity = cacheMap.get(key);
		if (entity == null && redisTemplate != null) {
			entity = redisTemplate.opsForHash().get(key, key);
			if (entity != null)
				cacheMap.put(key, entity);
		}
		return entity;
	}

	private boolean remove(String key) {
		if (redisTemplate != null)
			redisTemplate.opsForHash().delete(key, key);
		cacheMap.remove(key);
		return true;
	}

	@Override
	public void saveObject(CacheEnum cacheEnum, String key, Object entity) {
		saveObject(cacheEnum, key, entity, null);
	}

	/**
	 * 根据枚举类型保存数据格式 加上指定前缀放到ICache中
	 * 
	 * @param cacheEnum
	 * @param key
	 * @param entity
	 */
	public void saveObject(CacheEnum cacheEnum, String key, Object entity, Integer expired) {
		switch (cacheEnum) {
		case TOKEN:
			if (expired == null) {
				this.save(CacheConstant.TOKEN_PREFIX + key, entity);
			} else {
				this.save(CacheConstant.TOKEN_PREFIX + key, entity, expired);
			}
			break;
		case ENTITY:
			if (expired == null) {
				this.save(CacheConstant.ENTITY_PREFIX + key, entity);
			} else {
				this.save(CacheConstant.ENTITY_PREFIX + key, entity, expired);
			}
			break;
		default:
			logger.error("No such save data type! type:{}", cacheEnum);
			break;
		}
	}

	/**
	 * 根据类型取值 加上指定前缀取出对象
	 * 
	 * @param cacheEnum
	 * @param key
	 * @return
	 */
	public Object getObject(CacheEnum cacheEnum, String key) {
		switch (cacheEnum) {
		case TOKEN:
			return this.get(CacheConstant.TOKEN_PREFIX + key);
		case ENTITY:
			return this.get(CacheConstant.ENTITY_PREFIX + key);
		default:
			logger.error("No such get data type! type:{}", cacheEnum);
		}
		return null;
	}

	/**
	 * 根据类型删除 加上指定前缀删除对象
	 * 
	 * @param cacheEnum
	 * @param key
	 * @return
	 */
	public boolean removeObject(CacheEnum cacheEnum, String key) {
		switch (cacheEnum) {
		case TOKEN:
			return this.remove(CacheConstant.TOKEN_PREFIX + key);
		case ENTITY:
			return this.remove(CacheConstant.ENTITY_PREFIX + key);
		default:
			logger.error("No such get data type! type:{}", cacheEnum);
			return false;
		}
	}

}
