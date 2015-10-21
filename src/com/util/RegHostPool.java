package com.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RegHostPool {

	private static final Map<String, String> connections = new ConcurrentHashMap<String, String>();

	public static String getHost(String mac) {
		return connections.get(mac);
	}
	
	public static Set<String> getAllHost() {
		return connections.keySet();
	}

	public static void addHost(String mac, String ip) {
		connections.put(mac, ip);
	}

	public static void removeHost(String mac) {
		connections.remove(mac);
	}

	public static void forch() {
		Set<String> keySet = getAllHost();
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
}
