package com.vo;

public class Pagination {
	private Integer startRow;//��ʼ��
	private Integer endRow;//������
	private Integer totalPage;//��ҳ��
	private Integer totalRow;//������
	private Integer curPage;//��ǰ��ѯҳ��
	private Integer pageSize;//ÿҳ����
	private String author;//��ѯ����������ؼ��֣�
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
