package com.zonekey.disrec.common;

import net.sf.json.JSONObject;

//public class JsonTest {
//	private String response_code;
//
//	
//}
public class JsonTest3 {
	private String Uid;
	private String rtmp_distributer;

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public String getRtmp_distributer() {
		return rtmp_distributer;
	}

	public void setRtmp_distributer(String rtmp_distributer) {
		this.rtmp_distributer = rtmp_distributer;
	}

	public static void main(String[] args) {
		String str = " [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]";
		JSONObject json = JSONObject.fromObject(str);
//				.fromObject("{list:[{name:'Tom',age:999}]}");
//				.fromObject("{list:[{name:'Tom',age:1},{name2:'Tom2',age2:2}]}");
//		.fromObject(str);
		JsonTest3 j = (JsonTest3) JSONObject.toBean(json, JsonTest3.class);
//		System.out.println(j.getList().get(0));
		System.out.println(j.getRtmp_distributer());
	}
}
