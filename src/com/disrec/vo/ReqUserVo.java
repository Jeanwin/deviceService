package com.disrec.vo;

import java.io.Serializable;

public class ReqUserVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reqUserName;
	private String mac;
	private String status;

	public String getReqUserName() {
		return reqUserName;
	}


	public void setReqUserName(String reqUserName) {
		this.reqUserName = reqUserName;
	}


	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "ReqUserVo [reqUserName=" + reqUserName + ", mac=" + mac
				+ ", status=" + status + "]";
	}

}
