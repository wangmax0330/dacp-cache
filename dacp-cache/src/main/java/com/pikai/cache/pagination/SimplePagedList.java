package com.pikai.cache.pagination;

import java.util.List;

public class SimplePagedList<T> implements MutablePagedList<T> {
	private int pageIndex = 1;
	private int pageSize = 10;
	private int totalItemCount = 0;
	private List<T> items;

	public SimplePagedList() {
	}

	public SimplePagedList(int pageIndex, int pageSize, int totalItemCount, List<T> items) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalItemCount = totalItemCount;
		this.items = items;
	}

	public SimplePagedList<T> pageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		return this;
	}

	public SimplePagedList<T> pageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public SimplePagedList<T> totalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
		return this;
	}

	public SimplePagedList<T> items(List<T> items) {
		this.items = items;
		return this;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getCurrentPageIndex() {
		return this.pageIndex;
	}

	public List<T> getItems() {
		return this.items;
	}

	public int getNextPageIndex() {
		return this.pageIndex + 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getPreviousPageIndex() {
		return this.pageIndex - 1;
	}

	public int getTotalItemCount() {
		return this.totalItemCount;
	}

	public int getTotalPageCount() {
		int totalPage = this.totalItemCount / this.pageSize;
		if ((this.totalItemCount % this.pageSize > 0) || (totalPage == 0)) {
			totalPage++;
		}
		return totalPage;
	}
}