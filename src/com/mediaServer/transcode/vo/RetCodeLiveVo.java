package com.mediaServer.transcode.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class RetCodeLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RetCodeLive2Vo> content;
	private String response_code_string;
	private String response_code;
	private String failure_reason;

	public static RetCodeLiveVo ToRetCodeLiveVo(String str) {
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("content", RetCodeLive2Vo.class);
		JSONObject json = JSONObject.fromObject(str);
		return (RetCodeLiveVo) JSONObject.toBean(JSONObject.fromObject(json),
				RetCodeLiveVo.class, classMap);
	}

	public List<RetCodeLive2Vo> getContent() {
		return content;
	}

	public void setContent(List<RetCodeLive2Vo> content) {
		this.content = content;
	}

	public String getResponse_code_string() {
		return response_code_string;
	}

	public void setResponse_code_string(String response_code_string) {
		this.response_code_string = response_code_string;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getFailure_reason() {
		return failure_reason;
	}

	public void setFailure_reason(String failure_reason) {
		this.failure_reason = failure_reason;
	}
}
