package com.mediaServer.vds.vo;

import java.io.Serializable;

public class RetVdsLive3Vo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String Uid;
	private String rtmp_distributer;

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public String getRtmp_distributer() {
		return rtmp_distributer;
	}

	public void setRtmp_distributer(String rtmp_distributer) {
		this.rtmp_distributer = rtmp_distributer;
	}

}
