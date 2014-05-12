package com.ustc.model;

public class Board {

	String name;
	String section;
	String link;
	
	public Board(){
		
	}
	public Board(String name,String section,String link){
		this.name = name;
		this.section = section;
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
