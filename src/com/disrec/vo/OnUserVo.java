package com.disrec.vo;

import java.io.Serializable;

public class OnUserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String onUserName;
	private String mac;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnUserName() {
		return onUserName;
	}

	public void setOnUserName(String onUserName) {
		this.onUserName = onUserName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "OnUserVo [id=" + id + ", onUserName=" + onUserName + ", mac="
				+ mac + "]";
	}

}
