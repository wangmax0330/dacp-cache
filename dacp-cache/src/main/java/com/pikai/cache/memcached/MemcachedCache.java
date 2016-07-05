package com.pikai.cache.memcached;

import java.io.Serializable;
import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

public class MemcachedCache implements Cache {
	private static final Object NULL_HOLDER = new NullHolder(null);
	private final String name;
	private final MemcachedClient memcachedClient;
	private final int expiry;
	private final boolean allowNullValues;

	public MemcachedCache(String name, MemcachedClient memcachedClient, int expiry) {
		this(name, memcachedClient, expiry, true);
	}

	public MemcachedCache(String name, MemcachedClient memcachedClient, int expiry, boolean allowNullValues) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(memcachedClient, "MemcachedClient must not be null");
		this.name = name;
		this.memcachedClient = memcachedClient;
		this.expiry = expiry;
		this.allowNullValues = allowNullValues;
	}

	public String getName() {
		return this.name;
	}

	public Object getNativeCache() {
		return this.memcachedClient;
	}

	public final boolean isAllowNullValues() {
		return this.allowNullValues;
	}

	public Cache.ValueWrapper get(Object key) {
		String stringKey = objectToString(key);
		try {
			Object value = this.memcachedClient.get(stringKey);
			return value != null ? new SimpleValueWrapper(fromStoreValue(value)) : null;
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to get value [key=" + stringKey + "]", ex);
		}
	}

	public <T> T get(Object key, Class<T> type) {
		String stringKey = objectToString(key);
		try {
			Object value = fromStoreValue(this.memcachedClient.get(stringKey));
			if ((value != null) && (type != null) && (!type.isInstance(value))) {
				throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
			}
			return (T) value;
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to get value [key=" + stringKey + "] from cache " + this.name, ex);
		}
	}

	public void put(Object key, Object value) {
		String stringKey = objectToString(key);
		try {
			this.memcachedClient.set(stringKey, this.expiry, toStoreValue(value));
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to set entry [key=" + stringKey + ",expiry=" + this.expiry + "(s),value=" + value
					+ "] into cache " + this.name, ex);
		}
	}

	public void evict(Object key) {
		String stringKey = objectToString(key);
		try {
			this.memcachedClient.delete(stringKey);
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to delete entry [key=" + stringKey + "] from cache " + this.name, ex);
		}
	}

	public void clear() {
		try {
			this.memcachedClient.flushAll();
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to clear cache " + this.name, ex);
		}
	}

	protected Object fromStoreValue(Object storeValue) {
		if ((this.allowNullValues) && (storeValue == NULL_HOLDER)) {
			return null;
		}
		return storeValue;
	}

	protected Object toStoreValue(Object userValue) {
		if ((this.allowNullValues) && (userValue == null)) {
			return NULL_HOLDER;
		}
		return userValue;
	}

	private String objectToString(Object object) {
		if (object == null) {
			return null;
		}
		if ((object instanceof String)) {
			return this.name.concat((String) object);
		}
		return this.name.concat(object.toString());
	}

	private static class NullHolder implements Serializable {
		public NullHolder(Object object) {
		}
	}
}
