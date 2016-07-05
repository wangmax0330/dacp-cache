package com.pikai.cache.decorating;

public abstract interface ValuePostProcessor {
	public abstract boolean supportsCacheName(String paramString);

	public abstract Object postProcessAfterGet(Object paramObject1, Object paramObject2);

	public abstract Object postProcessBeforePut(Object paramObject1, Object paramObject2);
}
