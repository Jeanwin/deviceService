package com.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LivingPool {

	/**
	 * @author zfc
	 * 
	 *         录播机直播地址
	 *         
	 *         生命周期
	 *         1 LiveThread 
	 *         2 LivingService.startLiving(String mac)
	 *         3 RegServicePool.removeService(String mac)
	 *         
	 *         
		if("recording".equals(serviceInfo.getId()))new LiveThread(serviceInfo.getMac()).start();
	 */
	private static final Map<String, String> connections = new HashMap<String, String>();

	public static Set<String> getOnlineLiving() {
		return connections.keySet();
	}

	public static void addLiving(String mac, String living) {
		connections.put(mac, living);
	}

	public static String getLiving(String mac) {
		return connections.get(mac);
	}

	/**
	 * RegServicePool.removeService(String mac) del ...
	 * 中删除这个
	 * @param mac
	 */
	public static void removeLiving(String mac) {
		if(!"".equals(getLiving(mac)))
		connections.remove(mac);
	}
	
	public static void removeAll() {
		Set<String> keySet = getOnlineLiving();
		for (String key : keySet) {
			connections.remove(key);
		}
	}

	public static void forch() {
		Set<String> keySet = getOnlineLiving();
		if (keySet.size() <= 0)
			return;
//		System.out.print(" livingPool  forch()   mac:    ");
		for (String key : keySet) {
//			System.out.print("  " + key);
//			System.out.print("  " + connections.get(key).toString());
		}
//		System.out.println("  ");
	}

	private static LivingPool perConsolePool = new LivingPool();

	private LivingPool() {
	}

	public synchronized static LivingPool getliving() {
		if (perConsolePool == null) {
			perConsolePool = new LivingPool();
		}
		return perConsolePool;
	}
}
