package com.deviceService;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;

import com.util.ClassRoomDeviceInbound;

@WebServlet(urlPatterns = { "/classRoomDevice" })
public class ClassRoomDeviceServlet extends
		org.apache.catalina.websocket.WebSocketServlet {

	private static final long serialVersionUID = 1L;

	private static String currentUser = "";

	public static String getCurrentUser() {
		if (currentUser == null || "".equals(currentUser)) {
			return getUser();
		} else {
			return currentUser;
		}
	}

	public static String getUser() {
		String ret = new SimpleDateFormat("yyMMddHms").format(new Date());
		currentUser = ret;
		return ret;
	}

	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		return new ClassRoomDeviceInbound(getUser());
	}
}
