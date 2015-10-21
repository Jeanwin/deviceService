package com.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.ServerUrls;
import com.util.Url;

public class IoAlarmServcie {
	/**
	 * http://localhost:8080/disrec/centerController/getDutyPersonByMac
	 */
	public static String ManageIoAlarm(String mac,String N,String Y) {
		String Result="IoAlarm Start";
		//1.查询报警信息的基本设置,进行数据翻译,判断是否需要报警
		String methordUrl=ServerUrls.WebSerUrl+"/centerController/getDeviceAlarm";
		String result=Url.sendGet(methordUrl, "mac="+mac+"&output="+N+"&state="+Y);
		JSONObject jsonObject=CosJSONUtil.string2json(result);
		String messageAlarm=jsonObject.getString("messageAlarm");
		//2.查询需要报警的值班人员
		if (jsonObject!=null&&"0".equals(messageAlarm)){
			String message=jsonObject.getString("clues");		
			Map<String, String> map=new HashMap<String, String>();
			map.put("MessageType", "IoAlarm");
			map.put("messageid", jsonObject.getString("messageId"));
			map.put("clues", message);
			map.put("output", N);
			map.put("state", Y);
			map.put("mac", mac);
			map.put("bell", jsonObject.getString("bell"));
			methordUrl=ServerUrls.WebSerUrl+"/centerController/getDutyPersonByMac";
			result=Url.sendGet(methordUrl, "mac="+mac);
			jsonObject=CosJSONUtil.string2json(result);
			Result="getDeviceAlarm success";
			//3.向在线的用户发送消息窗口提醒
			if (jsonObject!=null){
				Result="PersonByMac success";
				String username=jsonObject.getString("userId");
				String areaName=jsonObject.getString("areaName");
				map.put("areaName", areaName);
				jsonObject=CosJSONUtil.toJsonObjectFromMap(map);
				Result=DeviceInboundPool.sendMessageToUserExt(username, jsonObject.toString());
			}	
		}
		return Result;
	}
}
