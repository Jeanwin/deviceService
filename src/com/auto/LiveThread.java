package com.auto;

import java.util.ArrayList;
import java.util.List;

import com.service.LivingService;
import com.util.LivingPool;
import com.util.RegServicePool;

public class LiveThread extends Thread {

//	private static Logger logger = Logger.getLogger(LiveThread.class);
	public LiveThread() {
	}

	public void run() {
//		String id = Id.getId();
//		logger.info(id+" 开启直播任务开始。。。");
//		TreadCount.i++;
		try {
			List<String> list = new ArrayList<String>(
					RegServicePool.getAllService());

			String ret ;
			for (String mac : list) {
				try {
					ret = LivingPool.getLiving(mac);
					if ((ret==null||"".equals(ret))&&(!listLivingStatusByMac()))
						new InLiveThread(mac).start();
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
//			logger.error(id+" 开启直播任务异常结束。。。");
//			TreadCount.i--;
			interrupt();
		}
//		TreadCount.i--;
//		logger.info(id+" 开启直播任务结束。。。");
	}

	public boolean listLivingStatusByMac() {
		boolean ret=false;
		try {
		} catch (Exception e) {
			ret=false;
		}
		return ret;
	}
}
class InLiveThread extends Thread {
	private String mac;
	public InLiveThread(String mac) {
		this.mac=mac;
	}
	public void run() {
		try {
				LivingService.startLiving(mac);
		} catch (Exception e) {
			interrupt();
		}
	}
}
