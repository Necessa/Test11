package com.ustc.model;

public class Board {

	String name;
	String title;
	String section;
	String sectionLink;
	String link;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSectionLink() {
		return sectionLink;
	}
	public void setSectionLink(String sectionLink) {
		this.sectionLink = sectionLink;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Board(String name, String title, String section, String sectionLink,
			String link) {
		super();
		this.name = name;
		this.title = title;
		this.section = section;
		this.sectionLink = sectionLink;
		this.link = link;
	}
	
	
}
