package com.service;

import org.apache.log4j.Logger;

import com.auto.AutoJob;
import com.vo.ServiceInfo;

public class DeviceRegisteringService {
	private static Logger logger = Logger.getLogger(AutoJob.class);
	
	public static void DeviceRegisterFilter(ServiceInfo serviceInfo) {
		SpecialCardIssued(serviceInfo);
	}
	
	public static void SpecialCardIssued(ServiceInfo serviceInfo) {
		try {
			if ("centralcontroller".equals(serviceInfo.getType())){
				CardMangeService.CardNumList(serviceInfo.getMac());
			}
		} catch (Exception e) {
			logger.info("SpecialCardIssued"+e.getMessage());
		}

	}

}
