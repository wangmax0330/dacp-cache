package com.pikai.cache.decorating;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.CollectionUtils;

public class ValueProcessingCacheManagerDecorator implements CacheManager, InitializingBean {
	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap();
	private CacheManager cacheManager;
	private Collection<ValuePostProcessor> valuePostProcessors;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public CacheManager getCacheManager() {
		return this.cacheManager;
	}

	public void setValuePostProcessors(Collection<ValuePostProcessor> valuePostProcessors) {
		this.valuePostProcessors = valuePostProcessors;
	}

	public Collection<ValuePostProcessor> getValuePostProcessors() {
		return this.valuePostProcessors;
	}

	public Cache getCache(String name) {
		if (CollectionUtils.isEmpty(this.valuePostProcessors)) {
			return this.cacheManager.getCache(name);
		}
		Cache cache = (Cache) this.cacheMap.get(name);
		if (cache == null) {
			cache = this.cacheManager.getCache(name);
			if (cache != null) {
				Collection<ValuePostProcessor> processorsToUse = new ArrayList();
				for (ValuePostProcessor valuePostProcessor : this.valuePostProcessors) {
					if (valuePostProcessor.supportsCacheName(name)) {
						processorsToUse.add(valuePostProcessor);
					}
				}
				if (!CollectionUtils.isEmpty(processorsToUse)) {
					cache = new ValueProcessingCacheDecorator(cache, processorsToUse);
				}
				this.cacheMap.put(cache.getName(), cache);
			}
		}
		return cache;
	}

	public Collection<String> getCacheNames() {
		return this.cacheManager.getCacheNames();
	}

	public void afterPropertiesSet() throws Exception {
		if (this.cacheManager == null) {
			throw new IllegalStateException("Decorated CacheManager must not be null.");
		}
	}
}
