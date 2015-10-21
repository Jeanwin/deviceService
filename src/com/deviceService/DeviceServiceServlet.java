package com.deviceService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;

import com.util.DeviceInbound;
//import com.util.DeviceServiceInbound;

@WebServlet(urlPatterns = { "/deviceService" })
public class DeviceServiceServlet extends
		org.apache.catalina.websocket.WebSocketServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
//		String str=DeviceInbound.getStauts();
//		if(!"0".equals(str)){
//			return new DeviceInbound();
//		}
//		return null;
		String username=request.getParameter("username");

		return new DeviceInbound(username);
	}

}
