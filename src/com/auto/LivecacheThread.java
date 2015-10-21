package com.auto;

import java.util.ArrayList;
import java.util.List;

import com.mediaServer.middle.service.MiddleService;
import com.util.LivingPool;

public class LivecacheThread extends Thread {

	public LivecacheThread() {
	}

	public void run() {
		try {
			List<String> list = new ArrayList<String>(
					LivingPool.getOnlineLiving());
			for (String mac : list) {
				try {
					if (!MiddleService.getLiveStatusByMac(mac))
						LivingPool.removeLiving(mac);
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			interrupt();
		}
	}

}