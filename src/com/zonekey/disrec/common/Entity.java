//package com.zonekey.disrec.common;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.util.CosJSONUtil;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//public class Entity {
//	private String content;
//	// private List<Bean> content;
//	private String response_code_string;
//	private String response_code;
//
//	// public List<Bean> getContent() {
//	// return content;
//	// }
//	// public void setContent(List<Bean> content) {
//	// this.content = content;
//	// }
//	public String getResponse_code_string() {
//		return response_code_string;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public void setResponse_code_string(String response_code_string) {
//		this.response_code_string = response_code_string;
//	}
//
//	public String getResponse_code() {
//		return response_code;
//	}
//
//	public void setResponse_code(String response_code) {
//		this.response_code = response_code;
//	}
//
//	public static void main(String[] args) {
//
//		// Entity entiry =
//		// JsonUtil.jsonToObject("{\"content\": [{\"nginx\": \"192.168.12.114\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]}, {\"nginx\": \"192.168.12.111\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.111:1939/zonekey/test\"}]}], \"response_code_string\": \"SUCCESS\", \"response_code\": 0}",
//		// Entity.class);
//		String str = "{\"content\": [{\"nginx\": \"192.168.12.114\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]}, {\"nginx\": \"192.168.12.111\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.111:1939/zonekey/test\"}]}], \"response_code_string\": \"SUCCESS\", \"response_code\": 0}";
//		JSONObject s = CosJSONUtil
//				.string2json("{\"content\": [{\"nginx\": \"192.168.12.114\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.114:1939/zonekey/test\"}]}, {\"nginx\": \"192.168.12.111\", \"rtmps\": [{\"uid\": \"xx-xxxx-xxxx-xxx\", \"rtmp_distributer\": \"rtmp://192.168.12.111:1939/zonekey/test\"}]}], \"response_code_string\": \"SUCCESS\", \"response_code\": 0}");
//		// System.out.println(entiry.getContent().get(1).getRtmps().get(0).getRtmp_distributer());
//		// fromJSON(str,Entity.class);
//
//		JSONObject jsonObj = JSONObject.fromObject(str);
//		Map<String, Class> classMap = new HashMap<String, Class>();
//		classMap.put("Entity", Entity.class);
////		classMap.put("imageList", ImageBean.class);
//		// 将json转换成workflowBean
//		JSONObject.toBean(jsonObj,Entity.class, classMap);
//	}
//
//	public static <T> T fromJSON(String json, Class<T> clazz) {
//		// MyBeanWithPerson diyBean = (MyBeanWithPerson)
//		Map classMap = new HashMap();
//		classMap.put("Entity", Entity.class);
//		JSONObject.toBean(JSONObject.fromObject(json), Entity.class, classMap);
//		// ObjectMapper mapper = new ObjectMapper();
//		// try {
//		// return mapper.readValue(json, clazz);
//		// } catch (JsonParseException e) {
//		// throw new RuntimeException(e);
//		// } catch (JsonMappingException e) {
//		// throw new RuntimeException(e);
//		// } catch (IOException e) {
//		// throw new RuntimeException(e);
//		// }
//		return null;
//	}
//
//	public static JSONArray toJson(List<Map<String, Object>> list) {
//		JSONArray jsonArray = JSONArray.fromObject(list);
//		return jsonArray;
//
//	}
//}
