package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;

import com.service.GetInfoService;

/**
 * 
 * @author zfc 
 * 
 * for 教室日常 导播台
 * 
 * 
 */
public class DeviceStatusPool {

	private static final Map<String, String> connections = new ConcurrentHashMap<String, String>();

	public static String getDeviceStatus(String mac) {
		return connections.get(mac);
	}

	public static Set<String> getAllDeviceStatus() {
		return connections.keySet();
	}

	public static void addDeviceStatus(String mac, String status) {
		connections.put(mac, status);
	}

	public static void removeDeviceStatus(String mac) {
		connections.remove(mac);
	}

	public static void forch() {
		Set<String> keySet = getAllDeviceStatus();
		if (keySet.size() <= 0)
			return;
		for (String key : keySet) {
//			System.out.print("  " + key);
//			System.out.print("  " + connections.get(key));
		}
//		System.out.println("  ");
	}

	public static void main(String[] args) {
		 addDeviceStatus("11", "21" + "&&" + "31");
		 addDeviceStatus("12", "33" + "&&" + "32");
		 addDeviceStatus("13", "23" + "&&" + "33");
		forch();
	}
}
