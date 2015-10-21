package com.service;

import com.util.CosJSONUtil;
import com.util.Url;

/**
 * @author zfc
 * 
 *         http://192.168.12.47:10006/recording/cmd?RecordCmd=RtspPreview
 */
public class PreView {

	// private static String url = "http://192.168.12.47:10006/recording/cmd";
	//private static String url = Url.getConseleIpUrl() + ":10006/recording/cmd";

	public static String RtspPreview(String mac) {

		return createRetPreViewInfo(mac);
	}

	/**
	 * 
	 * @param url
	 * @param direction
	 * @param param
	 */
	public static String controlDirection(String url, String param) {
		return Url.sendGet(url, param);

	}

	public static void main(String[] args) {
		//RtspPreview();
		//createRetPreViewInfo();
	}

	public static String createRetPreViewInfo(String mac) {
		String ret = "";
		try {
			// String str = controlDirection(url, "RecordCmd=RtspPreview");
			// Map<String, String> map = new HashMap<String, String>();
			// map = CosJSONUtil.json2Map(CosJSONUtil.string2json(str));
			// CosJSONUtil.string2json(map.get("info"));
			// ret = CosJSONUtil.string2json(map.get("info")).toString();
			String url=Url.getServiceUrl(mac, "recording");
			
			ret = CosJSONUtil.string2json(
					controlDirection(url+"/cmd", "RecordCmd=RtspPreview")).toString();

		} catch (Exception e) {
			//e.printStackTrace();
			
		}
		return ret;
	}

}
