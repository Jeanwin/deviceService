package com.auto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.service.GetInfoService;
import com.service.RecordingService;
import com.util.DeviceCache;
import com.util.DeviceRtspUrlsPool;
import com.util.DeviceServiceInbound;
import com.util.Id;
import com.util.RegServicePool;
import com.vo.ServiceInfo;

public class AutoJob {
	private static Logger logger = Logger.getLogger(AutoJob.class);
	DeviceCache deviceCache;
	int intwebscott = 0;
	String stringwebscott = "test ";
	private DeviceServiceInbound deviceServiceInbound = DeviceServiceInbound
			.getInstance();

	public DeviceCache getDeviceCache() {
		return deviceCache;
	}

	public void setDeviceCache(DeviceCache deviceCache) {
		this.deviceCache = deviceCache;
	}

	public void execute() {
//		String id = Id.getId();
//		logger.info(id+" 定时任务开始。。。");
		naming();
		//new LiveThread().start();                 //推流的处理线程
		new DeviceStatusThread().start();
		new CentralControllPeripheralThread().start();
		DeviceStatusThread.sendDeviceStatusInfoToConsole();
//		logger.info(id+" 定时任务结束。。。");
	}
    /**
     * map<mac,map<deviceType,ServiceInfo>>
     */
	public void naming() {
		Map<String, Map<String, ServiceInfo>> Maps = deviceCache
				.verifyReadAll();   //或得所有的注册的设备
		//或得注册的mac地址
		List<String> list = new ArrayList<String>(Maps.keySet());
		//map<deviceType,ServiceInfo>
		Map<String, ServiceInfo> idMap = null;  
		Iterator<String> idIterator;
		ServiceInfo serviceInfo;
		for (int i = 0; i < list.size(); i++) {
			idMap = Maps.get(list.get(i));
			if (idMap.keySet().isEmpty())
				deviceCache.verifyDel(list.get(i));
			else {
				idIterator = idMap.keySet().iterator(); //deviceType  "recording" ,"dm"
				while (idIterator.hasNext()) {
					String str = idIterator.next();
					//System.out.println("str___"+str); // "recording"
					serviceInfo = (ServiceInfo) idMap.get(str);
					try {
						Date d = new Date();
						long l1 = d.getTime();
						long l2 = serviceInfo.getDate();
						if (l1 == 0l || l2 == 0l)
							continue;
						if (l1 - l2 > 20000) {
							idIterator.remove();
							RegServicePool.getService(list.get(i)).remove(str);
							//logger.info(RegServicePool.getService(list.get(i)).keySet());
							//System.out.println(RegServicePool.getService(list.get(i)).keySet().isEmpty());
							if (RegServicePool.getService(list.get(i)).keySet().isEmpty()) {
								RegServicePool.removeService(list.get(i));						
								DeviceRtspUrlsPool.removeRtspUrlByMac(list.get(i));
								//list.remove(i);
							}
						}
					} catch (Exception e) {
						continue;
					}
				}

			}

		}

	}

	public String getDeviceInfos() {
		try {

			List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
			List<String> list = new ArrayList<String>(
					RegServicePool.getAllService());
			Map<String, String> map;
			/*for (String string : list) {
				map = new HashMap<String, String>();
				String str = GetInfoService.getConsoleOperationInfo(string);
				if (!"".equals(str)) {
					map.put(string, str);
					ret.add(map);
				}
			}*/
			for (int i = 0; i < list.size(); i++) {
				map = new HashMap<String, String>();
				String str = GetInfoService.getConsoleOperationInfo(list.get(i));
				if (!"".equals(str)) {
					map.put(list.get(i), str);
					ret.add(map);
				}
			}
			return JSONArray.fromObject(ret).toString();
		} catch (Exception e) {
			return "";
		}
	}

	public void getInfos(String mac) {
//		RecordingService.execute(mac, "MetaInfoCmd=GetMac");
//		// System.out.print("disc : ");
//		RecordingService.execute(mac, "RecordCmd=QueryDiskStatus");
//		// System.out.print("recordTime : ");
//		RecordingService.execute(mac, "RecordCmd=QueryRecordTime");
//		// System.out.print("systemTime : ");
//		RecordingService.execute(mac, "RecordCmd=QuerySystemTime");
//		// System.out.print("allInfo  : ");
//		RecordingService.execute(mac, "RecordCmd=QueryRAllInfo");
	}

	public static void main(String[] args) {
		// System.out.print(RecordingService.execute("MetaInfoCmd=GetMac"));
	}
	

//	public void execute() {
//		new LiveThread().start();
//		new DeviceStatusThread().start();
//		DeviceStatusThread.sendDeviceStatusInfoToConsole();
//		// WebS ok
//		// intwebscott++;
//		// System.out.println("-------------" + deviceServiceInbound);
//		// System.out.println(stringwebscott + intwebscott + "-------------"+
//		// deviceServiceInbound);
//
//		// ClassRoomDeviceInboundPool.forch();
//		// ClassRoomDeviceInboundPool.sendMessage(String.valueOf(intwebscott));
//
//		// deviceServiceInbound.sendMessage(stringwebscott + intwebscott);
//		// deviceServiceInbound.sendMessage(GetInfoService.getConsoleOperationInfo());
//		// System.out.println("处理心跳前。。。");
////		RegServicePool.minitorheartbeat();
////		Monitoring.display();
//		Map<String, Map<String, ServiceInfo>> Maps = deviceCache
//				.verifyReadAll();
//		List<String> list = new ArrayList<String>(Maps.keySet());
//		Map<String, ServiceInfo> idMap = null;
//		Iterator<String> idIterator;
//		ServiceInfo serviceInfo;
//		// String cons;
////		String send;
////		try {
////			// send = getDeviceInfos();//不要删除 取设备状态
////			send = DeviceStatusThread.getDeviceStatusInfo();
////		} catch (Exception e) {
////			send = "";
////		}
//		/*for (String mac : list) {*/
//		for (int i = 0; i < list.size(); i++) {
////			if (send != null || !"".equals(send)) {
////				try {
////					DeviceInboundPool.sendMessageToAll(send);
////					deviceServiceInbound.sendMessage(send);
////				} catch (Exception e) {
////				}
////			}
//			idMap = Maps.get(list.get(i));
//			if (idMap.keySet().isEmpty())
//				deviceCache.verifyDel(list.get(i));
//			else {
//				idIterator = idMap.keySet().iterator();
//				while (idIterator.hasNext()) {
//					String str = idIterator.next();
//					serviceInfo = (ServiceInfo) idMap.get(str);
//					try {
//						Date d = new Date();
//						long l1 = d.getTime();
//						long l2 = serviceInfo.getDate();
//
//						if (l1 == 0l || l2 == 0l)
//							continue;
//						// System.out.println(l1 - l2);
//						// int i = new Date(l1).compareTo(new Date(l2));
//						if (l1 - l2 > 40000) {
//							RegServicePool.getService(list.get(i)).remove(str);
//							if (RegServicePool.getService(list.get(i)).keySet()
//									.isEmpty()) {
//								RegServicePool.removeService(list.get(i));
//							}
//							// RegServicePool.addServiceId(mac, str,
//							// serviceInfo);
////							DeviceStatusThread.delDeviceInfos(str);
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//						continue;
//					}
//				}
//
//			}
//
//		}
//		// System.out.println("处理心跳后。。。");
//		RegServicePool.minitorheartbeat();
//
//		// // System.out.println("---------守护进程-----"+new
//		// // SimpleDateFormat("yyyy-MM-dd H:m:s").format(new Date())+"------");
//		// Iterator<String> idIterator;
//		// Iterator<String> macIterator;
//		// Map<String, ServiceInfo> idMap = null;
//		// Map<String, Map<String, ServiceInfo>> macMap = null;
//		// ServiceInfo serviceInfo;
//		// List<String> macs = (List<String>) deviceCache.verifyReadAllKeys();
//		//
//		// for (String mac : macs) {
//		// if (mac != null && !"".equals(mac)) {
//		//
//		// macMap = (Map<String, Map<String, ServiceInfo>>) deviceCache
//		// .verifyRead(mac);
//		// macIterator = macMap.keySet().iterator();
//		// while (macIterator.hasNext()) {
//		// idMap = macMap.get(macIterator.next());
//		// if (idMap.keySet().isEmpty())
//		// deviceCache.verifyDel(mac);
//		// else {
//		// idIterator = idMap.keySet().iterator();
//		// while (idIterator.hasNext()) {
//		// String str = idIterator.next();
//		// serviceInfo = (ServiceInfo) idMap.get(str);
//		//
//		// //
//		// System.out.println("---------mac---id------------"+serviceInfo.getMac()
//		// // + serviceInfo.getId());
//		//
//		// try {
//		// // if (DateUtils.timeDifference(new Date(),
//		// // serviceInfo.getDate()) > 20000) {
//		// if (new Date().getTime()-serviceInfo.getDate() > 20000) {
//		// // System.out.println(new
//		// //
//		// SimpleDateFormat("yyyy-MM-dd H:m:s").format(serviceInfo.getDate()));
//		// idMap.remove(str);
//		// if (idMap.keySet().isEmpty()) {
//		// deviceCache.verifyDel(mac);
//		// } else {
//		// deviceCache.verifyWrite(
//		// serviceInfo.getMac(), macMap);
//		// }
//		// }
//		//
//		// idIterator = idMap.keySet().iterator();
//		//
//		// } catch (Exception e) {
//		// continue;
//		// }
//		// }
//		// }
//		// }
//		// }
//		// }
//
//		// getInfos();
//		// System.out.print("xxxxxxxxxxxxxxxxxxxxxxxxx"+getInfo.executeWithReturn("MetaInfoCmd=GetMac"));
//		// DeviceServiceInbound.getInstance().sendMessage(new
//		// SimpleDateFormat("yyyy-MM-dd H:m:s").format(new Date()));
//
//		// DeviceServiceInbound.getInstance().sendMessage(getInfo.executeWithReturn("MetaInfoCmd=GetMac"));
//		// DeviceServiceInbound.getInstance().sendMessage(GetInfoService.executeWithReturn("RecordCmd=QueryDiskStatus"));
//
//		// deviceServiceInbound.sendMessage(GetInfoService.getConsoleOperationInfo());
//		// GetInfoService.getConsoleOperationInfo();
//	}
}
