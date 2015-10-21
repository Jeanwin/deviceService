package com.mediaServer.vds.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class RetVdsLiveVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RetVdsLive2Vo> content;
	private String response_code_string;
	private String response_code;

	public List<RetVdsLive2Vo> getContent() {
		return content;
	}

	public void setContent(List<RetVdsLive2Vo> content) {
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

	public static void main(String[] args) {
		String str = "{\"content\": [{\"nginx\": \"192.168.12.114\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]}, {\"nginx\": \"192.168.12.111\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.111:1939/zonekey/test\"}]}], \"response_code_string\": \"SUCCESS\", \"response_code\": 0}";

		System.out.println(ToRetVdsLiveVo(str).getContent().get(0)
				.getRtmps().get(0).getRtmp_distributer());
	}

	public static RetVdsLiveVo ToRetVdsLiveVo(String str) {
		JSONObject json = JSONObject.fromObject(str);
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("rtmps", RetVdsLive3Vo.class);
		classMap.put("content", RetVdsLive2Vo.class);
		return (RetVdsLiveVo) JSONObject.toBean(JSONObject.fromObject(json),
				RetVdsLiveVo.class, classMap);
	}
}