package com.pikai.cache.pagination;

import java.util.List;

public abstract interface MutablePagedList<T> extends PagedList<T> {
	public abstract void setItems(List<T> paramList);
}
