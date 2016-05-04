package com.estsoft.jblog.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class BlogUserVo {
	private Long no;
	@NotEmpty
	private String name;
	@NotEmpty
	private String id;
	@NotEmpty
	private String passwd;
	private String regDate;
	
	@Override
	public String toString() {
		return "BlogUser [no=" + no + ", name=" + name + ", id=" + id + ", passwd=" + passwd + ", regDate=" + regDate
				+ "]";
	}
	
	public Long getNo() {
		return no;
	}
	
	public void setNo(Long no) {
		this.no = no;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}
