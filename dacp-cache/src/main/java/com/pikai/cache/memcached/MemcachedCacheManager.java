package com.pikai.cache.memcached;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

public class MemcachedCacheManager implements CacheManager, InitializingBean {
	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);
	private MemcachedClient memcachedClient;
	private boolean dynamic = true;
	private int expiry = 3600;
	private boolean allowNullValues = true;

	public MemcachedCacheManager() {
	}

	public MemcachedCacheManager(String... cacheNames) {
		setCacheNames(Arrays.asList(cacheNames));
	}

	public void setCacheNames(Collection<String> cacheNames) {
		if (cacheNames != null) {
			for (String name : cacheNames) {
				this.cacheMap.put(name, createMemcachedCache(name));
			}
			this.dynamic = false;
		}
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		Assert.notNull(memcachedClient, "MemcachedClient must not be null");
		this.memcachedClient = memcachedClient;
	}

	public MemcachedClient getMemcachedClient() {
		return this.memcachedClient;
	}

	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}

	public int getExpiry() {
		return this.expiry;
	}

	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	public boolean isAllowNullValues() {
		return this.allowNullValues;
	}

	public Collection<String> getCacheNames() {
		return Collections.unmodifiableSet(this.cacheMap.keySet());
	}

	public Cache getCache(String name) {
		Cache cache = (Cache) this.cacheMap.get(name);
		if ((cache == null) && (this.dynamic)) {
			synchronized (this.cacheMap) {
				cache = (Cache) this.cacheMap.get(name);
				if (cache == null) {
					cache = createMemcachedCache(name);
					this.cacheMap.put(name, cache);
				}
			}
		}
		return cache;
	}

	protected Cache createMemcachedCache(String name) {
		return new MemcachedCache(name, this.memcachedClient, this.expiry, isAllowNullValues());
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.memcachedClient, "MemcachedClient must not be null.");
	}
}
