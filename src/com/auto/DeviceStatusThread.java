package com.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.service.GetInfoService;
import com.util.ClassRoomDeviceInboundPool;
import com.util.DeviceInboundPool;
import com.util.DeviceStatusPool;
import com.util.RegServicePool;

public class DeviceStatusThread extends Thread {

//	private static Logger logger = Logger.getLogger(LiveThread.class);
	
	public DeviceStatusThread() {
	}

	public void run() {
//		String id = Id.getId();
//		logger.info(id+" 设备状态任务开始。。。");
		saveAndUpdateDeviceInfos();
//		logger.info(id+" 设备状态任务结束。。。");
	}

	public static void delDeviceInfos(String mac) {
		DeviceStatusPool.removeDeviceStatus(mac);
	}

	public void saveAndUpdateDeviceInfos() {
		try {
			List<String> list = new ArrayList<String>(
					RegServicePool.getAllService());
//			for (String mac : list) {
			for (int i = 0; i < list.size(); i++) {
				try {
//					String str = GetInfoService.getConsoleOperationInfo(list.get(i));
//					if (str!=null&&!"".equals(str)) {
//						str += ",,1";
//						DeviceStatusPool.addDeviceStatus(list.get(i), str);
//					}
					new InDeviceStatusThread(list.get(i)).start();
				} catch (Exception e) {
					continue;
				}
//				if (!"".equals(str)) {
//					str += ",,1";
//					DeviceStatusPool.addDeviceStatus(list.get(i), str);
//				} else {
//					DeviceStatusPool.addDeviceStatus(list.get(i), "1");
//				}
			}
		} catch (Exception e) {
			return;
		}
	}

	public static String getDeviceStatusInfo() {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		Set<String> keySet = DeviceStatusPool.getAllDeviceStatus();
		if (keySet.size() <= 0)
			return null;
		Map<String, String> map;
		for (String key : keySet) {
			if (!RegServicePool.getService(key).isEmpty()){     //增加，为什么autojob中的 删除不起作用。临时增加
				map = new HashMap<String, String>();
				map.put(key, DeviceStatusPool.getDeviceStatus(key));
				ret.add(map);
			}
		}
		return JSONArray.fromObject(ret).toString();
	}

	public static void sendDeviceStatusInfoToClass() {
		String send;
		try {
			send = DeviceStatusThread.getDeviceStatusInfo();
			ClassRoomDeviceInboundPool.sendMessage(send);
		} catch (Exception e) {
			send = "";
		}
	}
	public static void sendDeviceStatusInfoToConsole() {
		String send;
		try {
			send = DeviceStatusThread.getDeviceStatusInfo();
			DeviceInboundPool.sendMessageToAll(send);
		} catch (Exception e) {
			send = "";
		}
	}
}


class InDeviceStatusThread extends Thread {
	private static Logger logger = Logger.getLogger(InDeviceStatusThread.class);
	private String mac;
	public InDeviceStatusThread(String mac) {
		this.mac=mac;
	}

	public void run() {
//		String id = Id.getId();
//		logger.info(id+" 设备状态任务开始。。。");
		try {
			String str ="";
			try {
				str = GetInfoService.getConsoleOperationInfo(mac);
			} catch (Exception e) {
				str ="";
			}
			//if (str != null && !"".equals(str)) {
			if ("".equals(str)) {
				str = ",,,,,,,,1";
			}
			DeviceStatusPool.addDeviceStatus(mac, str+",,1");
		} catch (Exception e) { 
			logger.error(" 设备状态任务获取  （"+mac+"）录播机信息失败。。。");
			interrupt();
		}
//		logger.info(id+" 设备状态任务结束。。。");
	}
}