package com.service;

import javax.annotation.Resource;

import com.util.DeviceCache;

/**
 * @author zfc
 * 
 */
public class DeviceStauts {

	@Resource
	DeviceCache deviceCache;

	public  String getRecordStatus(String mac) {
		return GetInfoService.getRecordStatus(mac);
	}
	public  String getLiveStatus(String mac) {
		return GetInfoService.getLiveStatus(mac);
	}
	public  String getDeviceStatus(String mac) {
		if(deviceCache.verifyRead(mac)==null)
//			return (String) deviceCache.verifyRead(mac);
			return "";
		return mac;
	}
	public  String getDiscStatus(String mac) {
		return GetInfoService.getDiscStatus(mac);
	}
	
	
	public static void main(String[] args) {
		DeviceStauts d=new DeviceStauts();
		//System.out.println(d.getRecordStatus());
		//System.out.println(d.getLiveStatus());
		//System.out.println(d.getDiscStatus());
	}
}
