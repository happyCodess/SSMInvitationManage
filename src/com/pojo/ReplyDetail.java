package com.pojo;

import java.util.Date;

public class ReplyDetail {
	private Integer id;//���ӻظ�ID
	private Integer invid;//����ID�����
	private String content;//�ظ�����
	private String author;//�ظ�����
	private Date createDate; //�ظ�ʱ��
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInvid() {
		return invid;
	}
	public void setInvid(Integer invid) {
		this.invid = invid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public ReplyDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReplyDetail(Integer id, Integer invid, String content,
			String author, Date createDate) {
		super();
		this.id = id;
		this.invid = invid;
		this.content = content;
		this.author = author;
		this.createDate = createDate;
	}
	
	
}
