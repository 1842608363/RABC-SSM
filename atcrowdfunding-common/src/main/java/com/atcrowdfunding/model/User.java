package com.atcrowdfunding.model;

public class User {
	
	private Integer id;
	private String username;
	private String loginacct;
	private String password;
	private String email;
	private String createtime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginacct() {
		return loginacct;
	}
	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreatetime() {
		return createtime;
	}

	public User() {
		super();
	}
	public User(Integer id,String username,String loginacct,String password,String email,String createtime) {
		this.id = id;
		this.username = username;
		this.loginacct = loginacct;
		this.password = password;
		this.email = email;
		this.createtime = createtime;
	}

}
