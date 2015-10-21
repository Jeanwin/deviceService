package com.service.ptz;

import com.util.Url;

/**
 * @author zfc
 * 
 *         云台预置位操作（保存，调用，删除）
 */
public class Preset {
	public static final String PRESET_SAVE = "preset_save";
	public static final String PRESET_CALL = "preset_call";
	public static final String PRESET_CLEAR = "preset_clear";

	public static String getServiceUrl(String mac, String serviceName) {
		return Url.getServiceUrl(mac, serviceName);
	}

	public static String save(String mac, String cardInfo, String id) {
		String url = getServiceUrl(mac, cardInfo);
		if (!("".equals(url)))
			return controlDirection(url + "/"+"preset_save", "id=" + id);
		return null;
	}

	public static String call(String mac, String cardInfo, String id) {
		String url = getServiceUrl(mac, cardInfo);
		if ("".equals(url))
			return "";
		return controlDirection(url + "/"+ "preset_call", "id=" + id);
	}

	public static void clear(String mac, String cardInfo, String id) {
		String url = getServiceUrl(mac, cardInfo);
		if (!("".equals(url)))
			controlDirection(url + "/"+ "preset_clear", "id=" + id);
	}

	public static String controlDirection(String url,String param) {
		return Url.sendGet(url, param);
	}

	public static void main(String[] args) {
	}
}
