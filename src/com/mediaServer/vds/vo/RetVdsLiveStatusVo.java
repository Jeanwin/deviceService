package com.mediaServer.vds.vo;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class RetVdsLiveStatusVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String failure_reason;
	private String response_code_string;
	private String response_code;

	public String getFailure_reason() {
		return failure_reason;
	}

	public void setFailure_reason(String failure_reason) {
		this.failure_reason = failure_reason;
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

	public static RetVdsLiveStatusVo ToRetVdsLiveVo(String str) {
		JSONObject json = JSONObject.fromObject(str);
		return (RetVdsLiveStatusVo)JSONObject.toBean(JSONObject.fromObject(json), RetVdsLiveStatusVo.class);
	}
}