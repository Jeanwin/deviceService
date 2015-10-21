package com.mediaServer.transcode.vo;

import java.io.Serializable;

public class RetStopVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String response_code_string;
	private String response_code;
	private String failure_reason;

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
