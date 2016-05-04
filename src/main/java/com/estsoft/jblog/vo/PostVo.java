package com.estsoft.jblog.vo;

public class PostVo {
	private Long postId;
	private Long categoryId;
	private String title;
	private String content;
	private String regDate;
	
	@Override
	public String toString() {
		return "PostVo [postId=" + postId + ", categoryId=" + categoryId + ", title=" + title + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}
	
	public Long getPostId() {
		return postId;
	}
	
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}