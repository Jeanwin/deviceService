package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class CosJSONUtil {

	// public static <T> T jsonToObject(Object json, Class<T> clazz) {
	// if(json instanceof HttpServletRequest){
	// HttpServletRequest req = (HttpServletRequest) json;
	// try {
	// String str = new BufferedReader(new
	// InputStreamReader(req.getInputStream(),"utf-8")).readLine();
	// // return jsonMapper.fromJson(str, clazz);
	// } catch (Exception e) {
	// return null;
	// }
	// }
	// // return jsonMapper.fromJson(String.valueOf(json), clazz);
	// return null;
	// }
	/**
	 * @param map
	 * @return
	 */
	public static JSONObject toJsonObjectFromMap(
			@SuppressWarnings("rawtypes") Map map) {
		if (map == null || map.isEmpty()) {
			return new JSONObject();
		}
		JsonConfig jsonConfig = new JsonConfig();
		// jsonConfig.registerJsonValueProcessor(Date.class,new
		// JsonDateValueProcessor());
		JSONObject jo = JSONObject.fromObject(map, jsonConfig);
		return jo;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static JSONObject toJsonObjectFromObj(Object obj) {
		if (obj == null || "".equals(obj)) {
			return new JSONObject();
		}
		JsonConfig jsonConfig = new JsonConfig();
		// jsonConfig.registerJsonValueProcessor(Date.class,new
		// JsonDateValueProcessor());
		JSONObject jo = JSONObject.fromObject(obj, jsonConfig);
		return jo;
	}

	/**
	 * @param list
	 * @return
	 */
	public static JSONArray toJsonArrayFromList(
			@SuppressWarnings("rawtypes") List list) {
		if (list == null || list.isEmpty() || list.size() == 0) {
			return new JSONArray();
		}
		Object[] obj = list.toArray();
		JsonConfig jsonConfig = new JsonConfig();
		// jsonConfig.registerJsonValueProcessor(Date.class,new
		// JsonDateValueProcessor());
		JSONArray jo = JSONArray.fromObject(obj, jsonConfig);
		return jo;
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

	public static Map<String, Object> getRecordStatus(Map<String, Object> info) {
		String[] str = info.get(info).toString().split(",,");
		for (int i = 0; i < str.length; i++) {
//			System.out.println(str[i]);
		}
		return null;
	}

	// //JSON格式的String 怎么转成 net.sf.json.JSONObject
	// public static Map<String, Object> string2json2(String str){
	// JSONObject json=JSONObject.fromObject(str);
	//
	// // boolean bool=json.getBoolean("bool");
	// // int i=json.getInt("int");
	// // double d=json.getDouble("double");
	// // String value=json.getString("json");
	// //
	// System.out.println("bool="+String.valueOf(bool)+"\tjson="+value+"\tint="+i+"\tdouble="+d);
	// return json;
	// }

	// JSON鏍煎紡鐨凷tring 鎬庝箞杞垚 net.sf.json.JSONObject
	public static JSONObject string2json(String str) {
		try {
			JSONObject ret=null;
			ret=JSONObject.fromObject(str);
			if ("null".equals(ret.toString())) return null;
			return ret;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> json2Map(JSONObject jsonObject) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Iterator<String> iterator = jsonObject.keys();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				value = jsonObject.getString(key);
				result.put(key, value);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T ToVo(String str, Class<T> clazz,Map<String, Object> classMap) {
		JSONObject json = JSONObject.fromObject(str);
		return (T) JSONObject.toBean(JSONObject.fromObject(json),clazz, classMap);
	}
	
	public static Map<String,String>CharSplitToList(int Startindex,String split,String strurl)
	{
		String subString=strurl.substring(Startindex);
		String[] s=subString.split(split);
		Map<String, String> map = new HashMap<String, String>();
		for (String url : s) {
			if (url == null || "".equals(url))
				continue;
			//System.out.println(url.substring(url.lastIndexOf("_")+1));
			map.put(url.substring(url.lastIndexOf("_")+1), url);
		}
		return map;
	}
	
	public static void main(String[] args) {
		// string2json("{'json':'jsonvalue','bool':true,'int':1,'double':'20.5'}");
		// string2json(""
		// +
		// "{'result'=ok,'info'={'movie':'rtsp://root:root@192.168.12.47:554/session6.mpg','resource5':'rtsp://root:root@192.168.12.47:554/session4.mpg','resource4':'rtsp://root:root@192.168.12.47:554/session3.mpg','resource6':'rtsp://root:root@192.168.12.47:554/session5.mpg','resource1':'rtsp://root:root@192.168.12.47:554/session0.mpg','resource3':'rtsp://root:root@192.168.12.47:554/session2.mpg','resource2':'rtsp://root:root@192.168.12.47:554/session1.mpg'}}"
		// + "");
	}
}
