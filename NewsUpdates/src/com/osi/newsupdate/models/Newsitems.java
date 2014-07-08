package com.osi.newsupdate.models;

public class Newsitems {

	private String Title;
	private String Descripetion;
	private String image;
	private String newslink;
	private String publisheddate;
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescripetion() {
		return Descripetion;
	}
	public void setDescripetion(String descripetion) {
		Descripetion = descripetion;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getNewslink() {
		return newslink;
	}
	public void setNewslink(String newslink) {
		this.newslink = newslink;
	}
	public String getPublisheddate() {
		return publisheddate;
	}
	public void setPublisheddate(String publisheddate) {
		this.publisheddate = publisheddate;
	}
	
}
