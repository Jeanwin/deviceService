package com.daobotai;

import com.util.Url;


/**
* @author zfc
* 
 * 云台操作（鼠标跟踪）
 * 
 */
public class MouseTrace {

//	private static String url = "http://192.168.12.47:10003/ptz/teacher/";
	//private static String url = Url.readValue("recordingUrl");
	public static final String MOUSE_TRACE = "mouse_trace";

	public static void mouse_trace(String mac,String x,String y) {
		String url=Url.getServiceUrl(mac, "ptz");
		controlDirection(url+"/teacher/", MOUSE_TRACE,"x="+x+"y"+y);
	}

	/**
	 * 
	 * @param url
	 * @param direction
	 * @param param
	 *转动到鼠标指定图像位置,X是鼠标点到左边的距离跟宽的比例,Y是鼠标点到上端的距离跟高的比例
	 */
	
	public static void controlDirection(String url, String direction,
			String param) {
		Url.sendGet(url + direction, param+"&sx=3 &sy=3");

	}

	public static void main(String[] args) {
		//mouse_trace("100","200");
	}
}
