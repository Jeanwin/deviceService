package com.mediaServer.vds.vo;

import java.io.Serializable;
import java.util.List;

import com.mediaServer.transcode.vo.RetCodeLive2Vo;

import net.sf.json.JSONArray;

public class VdsLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String endtime;
	private List<RetCodeLive2Vo> rtmps;
	private String group_id;

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<RetCodeLive2Vo> getRtmps() {
		return rtmps;
	}

	public void setRtmps(List<RetCodeLive2Vo> rtmps) {
		this.rtmps = rtmps;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public static JSONArray toJson(List<VdsLiveVo> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray;

	}
}
