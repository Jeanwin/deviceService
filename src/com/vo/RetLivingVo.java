package com.vo;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class RetLivingVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String info;
	private String result;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public static RetLivingVo ToRetLivingVo(String str) {
		JSONObject json = JSONObject.fromObject(str);
		return (RetLivingVo) JSONObject.toBean(JSONObject.fromObject(json),RetLivingVo.class);
	}

	public static void main(String[] args) {
		String s = "{\"info\": \"rtmp://192.168.12.117:51935/zonekey/00e04cc20811Living1\", \"result\": \"ok\"}";
		RetLivingVo vo=ToRetLivingVo(s);
		vo.getResult();
	}
}
