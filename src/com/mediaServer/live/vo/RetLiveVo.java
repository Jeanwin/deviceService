package com.mediaServer.live.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class RetLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RetLive2Vo> info;
	private String result;

	public List<RetLive2Vo> getInfo() {
		return info;
	}

	public void setInfo(List<RetLive2Vo> info) {
		this.info = info;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public static RetLiveVo ToRetLiveVo(String str) {
		JSONObject json = JSONObject.fromObject(str);
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("info", RetLive2Vo.class);
		return (RetLiveVo) JSONObject.toBean(JSONObject.fromObject(json),
				RetLiveVo.class, classMap);
	}
}