package com.ustc.model;

public class User {
	private String username;
	private String password;
	
	public User(){
		
	}
	public User(String u,String pw){
		this.username = u;
		this.password = pw;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
