package com.osi.newsupdate.models;

public class Category {
	
	private int id;
	private String categoryname;
	private String categoryurl;
	private int count;
	private int channelid;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getCategoryurl() {
		return categoryurl;
	}
	public void setCategoryurl(String categoryurl) {
		this.categoryurl = categoryurl;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getChannelid() {
		return channelid;
	}
	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}
	
	
	
	

}
