package com.zonekey.disrec.common;

import java.util.List;

//public class JsonTest {
//	private String response_code;
//
//	
//}
public class JsonTest2 {
	private List<JsonTest3> rtmps;
	private String nginx;
	

	public List<JsonTest3> getRtmps() {
		return rtmps;
	}


	public void setRtmps(List<JsonTest3> rtmps) {
		this.rtmps = rtmps;
	}


	public String getNginx() {
		return nginx;
	}


	public void setNginx(String nginx) {
		this.nginx = nginx;
	}


	public static void main(String[] args) {
//		String str = "{\"content\": [{\"nginx\": \"192.168.12.114\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]}, {\"nginx\": \"192.168.12.111\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.111:1939/zonekey/test\"}]}], \"response_code_string\": \"SUCCESS\", \"response_code\": 0}";
//		JSONObject json = JSONObject
//		// .fromObject("{list:[{name:'Tom',age:999}]}");
//		// .fromObject("{list:[{name:'Tom',age:1},{name2:'Tom2',age2:2}]}");
//				.fromObject(str);
//		JsonTest2 j = (JsonTest2) JSONObject.toBean(json, JsonTest2.class);
//		// System.out.println(j.getList().get(0));
////		System.out.println(j.getContent());
	}
}

