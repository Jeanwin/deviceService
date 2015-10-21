package com.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.disrec.vo.OnUserVo;

public class HeartBeatPool {
	/**
	 * @author xfx
	 * 
	 *         	 */
	private static final Map<String, Long> heartBeatMap = new HashMap<String, Long>();

	public static Map<String, Long> getHeartbeatmap() {
		return heartBeatMap;
	}

	public static void addMessage(Long time, String mac) {
		heartBeatMap.put(mac, time);
	}

	public static Long getMessage(String key) {
		return heartBeatMap.get(key);
	}

	public static void removeMessageInbound(String mac) {
		heartBeatMap.remove(mac);
	}

}
