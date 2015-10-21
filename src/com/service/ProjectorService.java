package com.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.ServerUrls;
import com.util.Url;

public class ProjectorService {
	
	public static void ProjectorOff(String mac) {
		//1.通过mac获取直播室用户,和来电教室信息
		String methordUrl=ServerUrls.WebSerUrl+"/centerController/getDutyPersonByMac";
		String result=Url.sendGet(methordUrl, "mac="+mac);
		JSONObject jsonObject=CosJSONUtil.string2json(result);
		if (jsonObject!=null){
			Map<String, String> map=new HashMap<String, String>();
			map.put("MessageType", "ProjectorOFF");
			map.put("mac", mac);
			//2.向在线直播室用户发送呼叫消息
			String username=jsonObject.getString("userId");
			String areaName=jsonObject.getString("areaName");
			map.put("areaName", areaName);
			jsonObject=CosJSONUtil.toJsonObjectFromMap(map);
			DeviceInboundPool.sendMessageToUserExt(username, jsonObject.toString());
		}
	}

}
