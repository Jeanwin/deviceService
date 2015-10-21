package com.disrec.service;

import com.disrec.vo.ClassRoomDeviceVo;
import com.service.GetInfoService;
import com.service.RecordingService;
import com.util.Url;

/**
 * @author zfc
 * 
 */
public class DisrecService {

	// private static String url ="http://192.168.12.47:10000/dm/";
	// private static String url = Url.getDmUrl();
	// private static String url = Url.getConseleIpUrl()+":10000/dm/";

	// /dm/host?command=shutdown: 关机
	// /dm/host?command=restart: 重启计算机
	public static String exec(String mac, String param) {
		// System.out.println(param);
		// return param;
		String url = Url.getServiceUrl(mac, "dm");
		url = Url.sendGet(url + "/host", "command=" + param);
		return url;
	}

	//
	public static ClassRoomDeviceVo genClassRoomDeviceVo(String mac) {
		String s1 = "";
		String s2 = "";
		String s3 = "";
		String s4 = "1";
		String s5 = "";// schedule同步课表
		String s6 = "";
		ClassRoomDeviceVo vo;
		try {
			s1 = GetInfoService.getRecordStatus(mac);
			s2 = GetInfoService.getLiveStatus(mac);
			// s3=Preset.call("1");
			s5 = "";
			s6 = RecordingService.execute(mac, "RecordCmd=QueryDiskStatus");
		} catch (Exception e) {
			s4 = "0";
		}
		if (s4 == "0") {
			vo = new ClassRoomDeviceVo("", "", "", s4, "", "");
		} else {
			vo = new ClassRoomDeviceVo(s1, s2, s3, s4, s5, s6);
		}
		return vo;
	}

	public static String SendCmdToDevice(String mac,String para) {
		String url = Url.getServiceUrl(mac, "recording");
		if (url==null && "".equals(url)){
			url = Url.getServiceUrl(mac, "centralcontroller");
		}
		if (url==null && "".equals(url)) return "";
		return controlDirection(url + "/cmd", para);
	}
	
	public static String controlDirection(String url, String param) {
		return Url.sendGet(url, param);

	}
	
	public static void main(String[] args) {
		// genClassRoomDeviceVo();
		// return ;
	}

}
