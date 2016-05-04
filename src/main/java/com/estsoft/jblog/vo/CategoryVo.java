package com.estsoft.jblog.vo;

public class CategoryVo {
	private Long no;
	private Long blog_id;
	private String name;
	private String discription;
	private Long postCount;
	
	@Override
	public String toString() {
		return "Category [no=" + no + ", blog_id=" + blog_id + ", name=" + name + ", discription=" + discription
				+ ", postCount=" + postCount + "]";
	}

	public Long getNo() {
		return no;
	}
	
	public void setNo(Long no) {
		this.no = no;
	}
	
	public Long getBlog_id() {
		return blog_id;
	}
	
	public void setBlog_id(Long blog_id) {
		this.blog_id = blog_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDiscription() {
		return discription;
	}
	
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	
	public Long getPostCount() {
		return postCount;
	}
	
	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}
}
