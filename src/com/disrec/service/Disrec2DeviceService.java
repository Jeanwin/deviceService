package com.disrec.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.util.Url;

public class Disrec2DeviceService {

//	private static String setDeviceTimeUrl = "http://192.168.12.214:8080/disrec/rest/curriculum/updateEndtimeByBatch";
//	private static String url = "http://192.168.12.214:8080/disrec/rest/curriculum/";
	private static String setDeviceTimeUrl = Url.getServerUrl("web")+"/rest/curriculum/updateEndtimeByBatch";
	private static String url = Url.getServerUrl("web")+"/rest/curriculum/";
	public static final String FINDCLASSBYAREAIDANDTIME = "findClassByAreaidAndTime";// 查找课程

	public static String exec(String url ,String para) {
		String s = Url.sendGet(url, para);
		return s;
	}
	public static String exec(String para) {
		String s = Url.sendGet(url + FINDCLASSBYAREAIDANDTIME, "mac=" + para);
		return s;
	}

	public static void main(String[] args) {
//		System.out.println(exec(""));
//		Map<String, Object> map = parseJSON2Map(exec(""));
//		map.get("ScheduleTask");
//		System.out.println(exec(""));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	
	
	public static String updateEndtimeByBatch(String id, String endtime) {
		return exec(setDeviceTimeUrl ,"id="+id+"&endtime="+endtime);
	}
}
