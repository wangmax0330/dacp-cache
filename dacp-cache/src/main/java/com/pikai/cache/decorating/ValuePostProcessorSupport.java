package com.pikai.cache.decorating;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

/**
 * 支持的缓存类型
 * 
 * @author Administrator
 *
 */
public abstract class ValuePostProcessorSupport implements ValuePostProcessor {
	private Collection<String> supportedCacheNames;

	public boolean supportsCacheName(String cacheName) {
		if (this.supportedCacheNames == null) {
			return true;
		}

		return CollectionUtils.contains(this.supportedCacheNames.iterator(), cacheName);
	}

	public Object postProcessAfterGet(Object key, Object value) {
		return value;
	}

	public Object postProcessBeforePut(Object key, Object value) {
		return value;
	}

	public void setSupportedCacheNames(Collection<String> supportedCacheNames) {
		this.supportedCacheNames = supportedCacheNames;
	}

	public Collection<String> getSupportedCacheNames() {
		return this.supportedCacheNames;
	}
}