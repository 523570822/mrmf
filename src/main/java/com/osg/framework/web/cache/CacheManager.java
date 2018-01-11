package com.osg.framework.web.cache;

import com.osg.framework.web.cache.CacheConstant.CacheEnum;


public interface CacheManager {
	public void saveObject(CacheEnum cacheEnum,String key,Object entity);
	
	public void saveObject(CacheEnum cacheEnum,String key,Object entity,Integer expired);

	public Object getObject(CacheEnum cacheEnum,String key);

	public boolean removeObject(CacheEnum cacheEnum,String key);

	public void save(String key, Object entity);

	public Object get(String key);
}
