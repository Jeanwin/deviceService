package com.service.ptz;

import com.util.Url;

/**
 * @author zfc
 * 
 */
public class MouseTrace {

	public static final String MOUSE_TRACE = "mouse_trace";

	public static String mouse_trace(String mac, String cardInfo, float x,
			float y) {
		String url = Url.getServiceUrl(mac, cardInfo);
		if (url == null || url.equals("")) {
			return null;
		}
		url = url + "/";
		return controlDirection(url + MOUSE_TRACE, "x=" + x + "&y=" + y);
	}

	/**
	 * 
	 * @param url
	 * @param param
	 *            转动到鼠标指定图像位置,X是鼠标点到左边的距离跟宽的比例,Y是鼠标点到上端的距离跟高的比例
	 */

	public static String controlDirection(String url, String param) {
		return Url.sendGet(url, param + "&sx=8&sy=8");
	}

	public static void main(String[] args) {
		// mouse_trace("100","200");
	}
}
