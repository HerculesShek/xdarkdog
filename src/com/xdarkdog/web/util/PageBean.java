package com.xdarkdog.web.util;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("rawtypes")
public class PageBean {
	private int totalRows; // 总记录数
	private int totalPages; // 总页数
	private int currentPage; // 当前页
	private int pageSize; // 每页多少条

	private List data = new ArrayList(); // 记录 ( 数据 )

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

}
