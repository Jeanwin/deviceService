package com.util;

import java.util.HashMap;
import java.util.Map;

public class DeviceRtspUrlsPool {

	private static final Map<String, String> connections = new HashMap<String, String>();
	
	public static String getRtspUrlByMac(String mac) {
		return connections.get(mac);
	}
	
	public static void setRtspUrlByMac(String mac,String RtspUrl) {
		connections.put(mac, RtspUrl);
	}
	
	public static void removeRtspUrlByMac(String mac) {
		connections.remove(mac);
	}
	
	public static void clear() {
		connections.clear();
		
	}
}
