package com.pikai.cache.model;

import org.springframework.beans.BeansException;

public abstract interface ModelEnhancer {
	public abstract <T> T makeModel(Class<T> paramClass) throws BeansException;

	public abstract <T> T enhanceModel(T paramT) throws BeansException;

	public abstract void injectDependencies(Object paramObject) throws BeansException;
}
