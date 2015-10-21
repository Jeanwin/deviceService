package com.zonekey.disrec.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

//public class JsonTest {
//	private String response_code;
//
//	
//}
public class JsonTest {
	private List<JsonTest2> content;
	private String response_code_string;
	private String response_code;

//	private List<B> list;
//
//	public List<B> getList() {
//		return list;
//	}
//
//	public void setList(List<B> list) {
//		this.list = list;
//	}

	public List<JsonTest2> getContent() {
		return content;
	}

	public void setContent(List<JsonTest2> content) {
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
		JSONObject json = JSONObject.fromObject(str);
		
        Map<String,Object> classMap = new HashMap<String,Object>();
        classMap.put("rtmps", JsonTest3.class);
        classMap.put("content", JsonTest2.class);
        JsonTest j2 = (JsonTest)JSONObject.toBean(JSONObject.fromObject(json),JsonTest.class , classMap);
		System.out.println(j2.getContent().get(0).getRtmps().get(0).getRtmp_distributer());
	}
}

