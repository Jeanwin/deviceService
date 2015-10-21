package com.vo;

import java.io.Serializable;

public class ServiceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ip;
	private String id;
	private String mac;
	private String type;
	private Long  date;
	private String url;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ServiceInfo [ip=" + ip + ", id=" + id + ", mac=" + mac
				+ ", type=" + type + ", date=" + date + ", url=" + url + "]";
	}

}
