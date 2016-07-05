package com.pikai.cache.model;

import java.util.List;

import com.pikai.cache.pagination.MutablePagedList;

public abstract interface ModelContainer extends ModelEnhancer {
	public abstract Object addModel(ModelKey paramModelKey, Object paramObject);

	public abstract Object addModel(ModelKey paramModelKey, Object paramObject, boolean paramBoolean);

	public abstract Object addModel(ModelKey paramModelKey, Object paramObject, boolean paramBoolean1, boolean paramBoolean2);

	public abstract Object getModel(ModelKey paramModelKey);

	public abstract Object getModel(ModelKey paramModelKey, ModelLoader paramModelLoader);

	public abstract Object getModel(ModelKey paramModelKey, ModelLoader paramModelLoader, boolean paramBoolean);

	public abstract Object removeModel(ModelKey paramModelKey);

	public abstract boolean containsModel(ModelKey paramModelKey);

	public abstract void identifiersToModels(MutablePagedList<Object> paramMutablePagedList, Class<?> paramClass, ModelLoader paramModelLoader);

	public abstract <T> List<T> identifiersToModels(List<? extends Object> paramList, Class<T> paramClass, ModelLoader paramModelLoader);
}