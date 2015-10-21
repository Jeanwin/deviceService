package com.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.vo.ServiceInfo;

public class RegServicePool {

	private static final Map<String, Map<String,ServiceInfo>> connections = new HashMap<String, Map<String,ServiceInfo>>();

	public static  Map<String,ServiceInfo> getService(String mac) {
		return connections.get(mac);
	}

	public static Map<String, Map<String,ServiceInfo>> getAllServiceListAllKeys() {
		return connections;
	}
	
	public static Set<String> getAllService() {
		return connections.keySet();
	}

	public static void addService(String mac,  Map<String,ServiceInfo> ip) {
		connections.put(mac, ip);
	}
	
	public static void addServiceId(String mac,String id, ServiceInfo serviceInfo) {
		connections.get(mac).put(id, serviceInfo);
	}

	public static void removeService(String mac) {
		connections.remove(mac);
//		if(getService(mac)!=null)
		DeviceStatusPool.removeDeviceStatus(mac);
		LivingPool.removeLiving(mac);
	}

	public static void forch() {
		Set<String> keySet = getAllService();
		if (keySet.size() <= 0)
			return;
		for (String key : keySet) {
//			System.out.print("  " + key);
//			System.out.print("  " + connections.get(key));
		}
//		System.out.println("  ");
	}

	public static void main(String[] args) {
//		addHost("11", "21" + "&&" + "31");
//		addHost("11", "21" + "&&" + "31");
//		addHost("12", "33" + "&&" + "32");
//		addHost("13", "23" + "&&" + "33");
		forch();
	}
	public static void minitorheartbeat() {
//		System.out.println("------------------------------------------");
//		forch();
////		System.out.println("heartbeat :"+CosJSONUtil.toJsonObjectFromMap(connections));
//		System.out.println("------------------------------------------");
	}
}
