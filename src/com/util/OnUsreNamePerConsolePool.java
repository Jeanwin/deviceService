package com.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.disrec.vo.OnUserVo;

public class OnUsreNamePerConsolePool {
	/**
	 * @author zfc
	 * 
	 *         导播台
	 */
	private static final Map<String, OnUserVo> connections = new HashMap<String, OnUserVo>();

	public static Map<String, OnUserVo> all() {
		return connections;
	}

	public static Set<String> getOnlineUser() {
		return connections.keySet();
	}

	public static void addMessage(OnUserVo inbound, String user) {
		connections.put(user, inbound);
	}

	public static OnUserVo getMessage(String key) {
		return connections.get(key);
	}

	public static void removeMessageInbound(String user) {
		connections.remove(user);
	}

	public static void forch() {
		Set<String> keySet = getOnlineUser();
		if (keySet.size() <= 0)
			return;
//		System.out.print(" OnUsreNamePerConsolePool forch()   OnUsreName:   ");
		for (String key : keySet) {
//			System.out.print("  " + key);
//			System.out.print("  " + connections.get(key).toString());
		}
//		System.out.println("  ");
	}

	// private static OnUsreNamePerConsolePool perConsolePool = new
	// OnUsreNamePerConsolePool();
	//
	// private OnUsreNamePerConsolePool() {
	// }
	//
	// public synchronized static OnUsreNamePerConsolePool getInstance() {
	// if (perConsolePool == null) {
	// perConsolePool = new OnUsreNamePerConsolePool();
	// }
	// return perConsolePool;
	// }
}
