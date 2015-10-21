package com.mediaServer.vds.vo;

import java.io.Serializable;

public class InVdsLive2Vo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uid;
	private String rtmp_transcoder;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRtmp_transcoder() {
		return rtmp_transcoder;
	}

	public void setRtmp_transcoder(String rtmp_transcoder) {
		this.rtmp_transcoder = rtmp_transcoder;
	}

}