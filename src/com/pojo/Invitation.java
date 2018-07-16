package com.pojo;

import java.util.Date;

/**
 * 实体类
 * */
public class Invitation {
	private Integer id;//帖子编号
	private String title;//帖子标题
	private String summary;//帖子摘要
	private String author;//帖子作者
	private Date createDate;//创建日期
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Invitation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Invitation(Integer id, String title, String summary, String author,
			Date createDate) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.author = author;
		this.createDate = createDate;
	}
	
	
	
	
	
}
