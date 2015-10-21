package com.mediaServer.vds.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class InVdsLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Map<String, String>> rtmps;
	private String group_id;
	private String endtime;

	public List<Map<String, String>> getRtmps() {
		return rtmps;
	}

	public void setRtmps(List<Map<String, String>> rtmps) {
		this.rtmps = rtmps;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}