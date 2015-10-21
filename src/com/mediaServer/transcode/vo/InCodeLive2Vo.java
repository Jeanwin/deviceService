package com.mediaServer.transcode.vo;

import java.io.Serializable;

public class InCodeLive2Vo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uid;
	private String rtmp_repeater;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRtmp_repeater() {
		return rtmp_repeater;
	}

	public void setRtmp_repeater(String rtmp_repeater) {
		this.rtmp_repeater = rtmp_repeater;
	}

}
