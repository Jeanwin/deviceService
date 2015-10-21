package com.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.service.CentralControllPeripheralService;
import com.service.LivingService;
import com.util.RegServicePool;
import com.vo.ServiceInfo;

public class CentralControllPeripheralThread extends Thread {

	public CentralControllPeripheralThread() {
		
	}
	
	public void run() {
       try {
    	   List<String> list = new ArrayList<String>(
					RegServicePool.getAllService());
			for (String mac : list) {
				ServiceInfo sInfo=RegServicePool.getAllServiceListAllKeys().get(mac).get("centralcontroller");
			    if(sInfo!=null){
			    	new PeripheralThread(mac,sInfo.getUrl()).start();
			    } 	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

class PeripheralThread extends Thread {
	private String Url;
	private String mac;
	public PeripheralThread(String mac,String Url) {
		this.Url=Url;
		this.mac=mac;
	}
	public void run() {
		try {
			CentralControllPeripheralService.UpdateProjectorLedTime(mac, Url);
		} catch (Exception e) {
			interrupt();
		}
	}
}
