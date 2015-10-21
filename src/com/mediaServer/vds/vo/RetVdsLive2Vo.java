package com.mediaServer.vds.vo;

import java.io.Serializable;
import java.util.List;

public class RetVdsLive2Vo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RetVdsLive3Vo> rtmps;
	private String nginx;

	public List<RetVdsLive3Vo> getRtmps() {
		return rtmps;
	}

	public void setRtmps(List<RetVdsLive3Vo> rtmps) {
		this.rtmps = rtmps;
	}

	public String getNginx() {
		return nginx;
	}

	public void setNginx(String nginx) {
		this.nginx = nginx;
	}

}
