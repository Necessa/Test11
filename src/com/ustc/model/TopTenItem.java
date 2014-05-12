package com.ustc.model;

public class TopTenItem {

	private String title;
	private String author;
	private String hot;
	private String department;
	
	public TopTenItem(){
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	//###############Õı—Û
	private String url;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String ss)
	{
		url=ss;
	}
	//#################
}
