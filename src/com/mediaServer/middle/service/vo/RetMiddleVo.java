package com.mediaServer.middle.service.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class RetMiddleVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RetMiddle2Vo> content;
	private String response_string;
	private String response_code;

	public List<RetMiddle2Vo> getContent() {
		return content;
	}

	public void setContent(List<RetMiddle2Vo> content) {
		this.content = content;
	}

	public String getResponse_string() {
		return response_string;
	}

	public void setResponse_string(String response_string) {
		this.response_string = response_string;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public static void main(String[] args) {
//		String str = "{\"content\": [{\"nginx\": \"192.168.12.114\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]}, {\"nginx\": \"192.168.12.111\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.111:1939/zonekey/test\"}]}], \"response_code_string\": \"SUCCESS\", \"response_code\": 0}";

	}

	public static RetMiddleVo ToRetMiddleVo(String str) {
		JSONObject json = JSONObject.fromObject(str);
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("content", RetMiddle2Vo.class);
		return (RetMiddleVo) JSONObject.toBean(JSONObject.fromObject(json),
				RetMiddleVo.class, classMap);
	}
}