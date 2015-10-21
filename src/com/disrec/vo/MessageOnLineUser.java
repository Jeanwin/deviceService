package com.disrec.vo;

import java.io.Serializable;
import java.util.Date;

public class MessageOnLineUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	private String mac;
	private Date onLineDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Date getOnLineDate() {
		return onLineDate;
	}
	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}
	
}
