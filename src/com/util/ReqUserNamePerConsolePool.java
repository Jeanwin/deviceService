package com.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.disrec.vo.ReqUserVo;

public class ReqUserNamePerConsolePool {

	/**
	 * @author zfc
	 * 
	 *         导播台
	 */
	private static final Map<String, ReqUserVo> connections = new HashMap<String, ReqUserVo>();

	public static Map<String, ReqUserVo> all() {
		return connections;
	}

	public static Set<String> getOnlineUser() {
		return connections.keySet();
	}

	public static void addMessage(ReqUserVo inbound, String user) {
		connections.put(user, inbound);
	}

	public static ReqUserVo getMessage(String key) {
		return connections.get(key);
	}

	public static void removeMessageInbound(String user) {
		connections.remove(user);
	}

	public static void forch() {
		Set<String> keySet = getOnlineUser();
		if (keySet.size() <= 0)
			return;
//		System.out.print(" ReqUserNamePerConsolePool forch()   ReqUserVo:    ");
		for (String key : keySet) {
//			System.out.print("  " + key);
//			System.out.print("  " + connections.get(key).toString());
		}
//		System.out.println("  ");
	}

	// private static ReqUserNamePerConsolePool perConsolePool = new
	// ReqUserNamePerConsolePool();
	//
	// private ReqUserNamePerConsolePool() {
	// }
	//
	// public synchronized static ReqUserNamePerConsolePool getInstance() {
	// if (perConsolePool == null) {
	// perConsolePool = new ReqUserNamePerConsolePool();
	// }
	// return perConsolePool;
	// }
}
