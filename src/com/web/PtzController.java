package com.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.ptz.MouseTrace;
import com.util.Url;

/**
 * @author zfc
 * 
 *         云台操作
 * 
 */
@Controller
public class PtzController {

	// private static String ptzUrl = ":10003/ptz/0/";
	// private static String url = Url.getConseleIpUrl()+":10003/ptz/teacher/";
	// private static String url = "http://192.168.12.47:10003/ptz/teacher/";
	// private static String url = "http://192.168.12.47:10003/ptz/student/";
	public static final String SPEET = "5";

	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String STOP = "stop";
	public static final String SETPOS = "set_pos";
	public static final String MOUSETRACK = "mouse_trace";
	public static final String SETZOOM = "set_zoom";
	public static final String PRESETSAVE = "preset_save";
	public static final String PRESETCALL = "preset_call";
	public static final String PRESETCLEAR = "preset_clear";
	public static final String ZOOMTELE = "zoom_tele";
	public static final String ZOOMWIDE = "zoom_wide";
	public static final String ZOOMSTOP = "zoom_stop";

	public static final String POS = "get_pos";
	public static final String ZOOM = "get_zoom";
	public static final String SCALES = "ext_get_scales";

	/**
	 * 
	 * http://192.168.13.123:10003/ptz/0/card0/mouse_trace?x=0.9&y=0.9&sx=1&sy=5
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/mouseTrace", method = RequestMethod.GET)
	public void mouseTrace(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		try {
			String mac = request.getParameter("mac");
			if (mac == null || "".equals(mac))
				return;
			String cardInfo = request.getParameter("cardInfo");
			if (cardInfo == null || "".equals(cardInfo))
				return;
			String width = request.getParameter("width");
			if (width == null || "".equals(width))
				return;
			String high = request.getParameter("high");
			if (high == null || "".equals(high))
				return;
			String x = request.getParameter("x");
			if (x == null || "".equals(x))
				return;
			String y = request.getParameter("y");
			if (y == null || "".equals(y))
				return;
			float xx=Float.parseFloat(x) / Float.parseFloat(width);
			xx=(float)(Math.round(xx*100))/100;
			float yy=Float.parseFloat(y) / Float.parseFloat(high);
			yy=(float)(Math.round(yy*100))/100;
			result = MouseTrace.mouse_trace(mac, cardInfo,xx,yy);
		} catch (Exception e) {
		}
		 response.setContentType("text/html;charset=UTF-8");
		 try {
		 PrintWriter pw = response.getWriter();
		 if (result != null && !result.isEmpty()) {
		 pw.print(result.toString());
		 }else{
			 pw.print("error");
		 }
		 pw.flush();
		 pw.close();
		 } catch (IOException e) {
		 }
	}

	/**
	 * @author zfc
	 * 
	 *         得到mac的所有服务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/consoleOperationInfo", method = RequestMethod.GET)
	public void listByMac(HttpServletRequest request,
			HttpServletResponse response) {
		// String s=request.getParameter("consoleOperationInfo");

		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		String cardInfo = request.getParameter("cardInfo");
		if (cardInfo == null && "".equals(cardInfo))
			return;
		
		
		String consoleOperationInfo = request
				.getParameter("consoleOperationInfo");
		if (consoleOperationInfo == null || "".equals(consoleOperationInfo))
			return;
		// exec(request.getParameter("consoleOperationInfo"));
		// exec(mac.toLowerCase(),cardInfo,consoleOperationInfo);
		exec(mac, cardInfo, consoleOperationInfo);
	}
	
	public static void exec(String mac, String cardInfo, String s) {

		// String ipUrl=Url.getConseleIpUrl(mac);

		// if("".equals(ipUrl))return;
		// String url=ipUrl+ptzUrl+cardInfo+"/";
		String url = Url.getServiceUrl(mac, cardInfo);
		// System.out.println("url is "+url);
		if (url == null || url.equals("")) {
			return;
		}
		url = url + "/";

		if (s != null && !"".equals(s)) {
			// controlStatus(url, STOP);
			char c = s.charAt(0);
			// char c = systemIn();
			// if (c == '')
			// continue;
			switch (c) {

			case '1':
				controlDirection(url, ZOOMTELE, "speed=" + SPEET);
				lateTime();
				controlStatus(url, ZOOMSTOP);
				break;
			case '2':
				controlDirection(url, DOWN, "speed=" + SPEET);
				lateTime();
				controlStatus(url, STOP);
				break;
			case '3':
				controlDirection(url, ZOOMWIDE, "speed=" + SPEET);
				lateTime();
				controlStatus(url, ZOOMSTOP);
				break;
			case '4':
				controlDirection(url, LEFT, "speed=" + SPEET);
				lateTime();
				controlStatus(url, STOP);
				break;
			case 5:
				lateTime();
				break;
			case '6':
				controlDirection(url, RIGHT, "speed=" + SPEET);
				lateTime();
				controlStatus(url, STOP);
				break;
			case 7:
				lateTime();
				break;
			case '8':
				controlDirection(url, UP, "speed=" + SPEET);
				lateTime();
				controlStatus(url, STOP);
				break;
			case 9:
				lateTime();
				break;
			default:
				break;
			}

			// Map<String, Map<String, ServiceInfo>> map = null;
			// if (request.getParameter("mac") != null) {
			// map = (Map<String, Map<String, ServiceInfo>>) deviceCache
			// .verifyRead(request.getParameter("mac"));
			//
			// JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
			// response.setContentType("text/html;charset=UTF-8");
			// try {
			// PrintWriter pw = response.getWriter();
			// if (json != null && !json.isEmpty()) {
			// pw.print(json.toString());
			// }
			// pw.flush();
			// pw.close();
			// } catch (IOException e) {
			// }
		}
	}

	public static void controlStatus(String url, String status) {
		// System.out.println(url + status);
		Url.sendGet(url + status, "");

	}

	/**
	 * 控制云台方向
	 * 
	 * @param url
	 * @param direction
	 *            LEFT RIGHT UP DOWN
	 * @param param
	 */
	public static void controlDirection(String url, String direction,
			String param) {
		// System.out.println(HttpRequest.sendGet(url + direction, param));
		Url.sendGet(url + direction, param);

	}

	@SuppressWarnings("static-access")
	public static void lateTime() {
		try {
			Thread.currentThread().sleep(500);
		} catch (Exception e) {
			// e.printStackTrace();
		}// 毫秒
			// Timer timer=new Timer();//实例化Timer类
			// timer.schedule(new TimerTask(){
			// public void run(){
			// this.cancel();}},50000);//五百毫秒
	}

	public static char systemIn() throws IOException {

		String str = new BufferedReader(new InputStreamReader(System.in))
				.readLine();
		if (str != null && !"".equals(str)) {
			char[] ch = str.trim().toCharArray();
			return ch[0];
		} else {
			return ' ';
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(1000);
		// exec("6");
		Thread.sleep(1000);
		// exec("2");
	}
}
