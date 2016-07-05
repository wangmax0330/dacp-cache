package com.pikai.cache.decorating;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class ValueProcessingCacheDecorator implements Cache {
	private Cache cache;
	private Collection<ValuePostProcessor> valuePostProcessors;

	public ValueProcessingCacheDecorator(Cache cache, Collection<ValuePostProcessor> valuePostProcessors) {
		this.cache = cache;
		this.valuePostProcessors = valuePostProcessors;
	}

	public Cache.ValueWrapper get(Object key) {
		Cache.ValueWrapper valueWrapper = this.cache.get(key);
		Object originalValue = valueWrapper != null ? valueWrapper.get() : null;

		Object value = applyValuePostProcessorsAfterGet(key, originalValue);
		if (value == originalValue) {
			return valueWrapper;
		}

		return value != null ? new SimpleValueWrapper(value) : null;
	}

	public <T> T get(Object key, Class<T> type) {
		T originalValue = this.cache.get(key, type);

		Object value = applyValuePostProcessorsAfterGet(key, originalValue);
		if (value == originalValue) {
			return originalValue;
		}
		if ((value != null) && (type != null) && (!type.isInstance(value))) {
			throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
		}
		return (T) value;
	}

	public void put(Object key, Object value) {
		value = applyValuePostProcessorsBeforePut(key, value);
		this.cache.put(key, value);
	}

	public void evict(Object key) {
		this.cache.evict(key);
	}

	public String getName() {
		return this.cache.getName();
	}

	public void clear() {
		this.cache.clear();
	}

	public Object getNativeCache() {
		return this.cache.getNativeCache();
	}

	private Object applyValuePostProcessorsAfterGet(Object key, Object value) {
		for (ValuePostProcessor valuePostProcessor : this.valuePostProcessors) {
			value = valuePostProcessor.postProcessAfterGet(key, value);
			if (value == null) {
				return value;
			}
		}
		return value;
	}

	private Object applyValuePostProcessorsBeforePut(Object key, Object value) {
		for (ValuePostProcessor valuePostProcessor : this.valuePostProcessors) {
			value = valuePostProcessor.postProcessBeforePut(key, value);
			if (value == null) {
				return value;
			}
		}
		return value;
	}
}
