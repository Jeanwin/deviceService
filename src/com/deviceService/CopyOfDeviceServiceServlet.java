package com.deviceService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;

import com.util.DeviceServiceInbound;

@WebServlet(urlPatterns = { "/message" })
public class CopyOfDeviceServiceServlet extends
		org.apache.catalina.websocket.WebSocketServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		 return DeviceServiceInbound.getInstance();
//		return new DeviceInbound("test");
	}
}
