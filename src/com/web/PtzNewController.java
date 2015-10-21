package com.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.util.Url;

/**
 * @author zfc
 * 
 *         云台操作
 * 
 */
@Controller
public class PtzNewController {

	public static final String SPEET = "4";

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
	 * @author zfc
	 * 
	 *         停止云台
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/consoleOperationStopNewInfo", method = RequestMethod.GET)
	public void consoleOperationStopNewInfo(HttpServletRequest request,
			HttpServletResponse response) {
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
		execStop(mac, cardInfo, consoleOperationInfo);
	}

	/**
	 * @author zfc
	 * 
	 *         开始云台
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/consoleOperationNewInfo", method = RequestMethod.GET)
	public void listByMac(HttpServletRequest request,
			HttpServletResponse response) {
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
		exec(mac, cardInfo, consoleOperationInfo);
	}

	public static String execStop(String mac, String cardInfo, String s) {
		String url = Url.getServiceUrl(mac, cardInfo);
		System.out.println("url is " + url);
		if (url == null || url.equals("")) {
			return null;
		}
		return controlStatus(url + "/", ZOOMSTOP);
	}

	public static String exec(String mac, String cardInfo, String s) {
		String url = Url.getServiceUrl(mac, cardInfo);
		System.out.println("url is " + url);
		if (url == null || url.equals("")) {
			return null;
		}
		url = url + "/";
		String result = null;
		if (s != null && !"".equals(s)) {
			char c = s.charAt(0);
			switch (c) {
			case '1':
				result =controlDirection(url, ZOOMTELE, "speed=" + SPEET);
				break;
			case '2':
				result =controlDirection(url, DOWN, "speed=" + SPEET);
				break;
			case '3':
				result =controlDirection(url, ZOOMWIDE, "speed=" + SPEET);
				break;
			case '4':
				result =controlDirection(url, LEFT, "speed=" + SPEET);
				break;
			case 5:
				break;
			case '6':
				result =controlDirection(url, RIGHT, "speed=" + SPEET);
				break;
			case 7:
				break;
			case '8':
				result =controlDirection(url, UP, "speed=" + SPEET);
				break;
			case 9:
				break;
			default:
				break;
			}
		}
		return result;
	}

	public static String controlStatus(String url, String status) {
		return Url.sendGet(url + status, "");
	}

	/**
	 * 控制云台方向
	 * 
	 * @param url
	 * @param direction
	 *            LEFT RIGHT UP DOWN
	 * @param param
	 */
	public static String controlDirection(String url, String direction,
			String param) {
		return Url.sendGet(url + direction, param);

	}

	@SuppressWarnings("static-access")
	public static void lateTime() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (Exception e) {
			//e.printStackTrace();
		}
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
