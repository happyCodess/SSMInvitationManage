package com.vo;

public class Pagination {
	private Integer startRow;//起始行
	private Integer endRow;//结束行
	private Integer totalPage;//总页数
	private Integer totalRow;//总条数
	private Integer curPage;//当前查询页码
	private Integer pageSize;//每页条数
	private String author;//查询条件（标题关键字）
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Pagination() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pagination(Integer startRow, Integer endRow, Integer totalPage,
			Integer totalRow, Integer curPage, Integer pageSize, String author) {
		super();
		this.startRow = startRow;
		this.endRow = endRow;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.author = author;
	}
	
	
	
}
