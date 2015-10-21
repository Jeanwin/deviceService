package com.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.ServerUrls;
import com.util.Url;

public class MangeStartTalk {
	
	/**
	 * 中控呼叫平台.平台发送消息
	 * @param mac
	 */
	public static void centralcontrollToTalk(String mac){
	//1.通过mac获取直播室用户,和来电教室信息
	String methordUrl=ServerUrls.WebSerUrl+"/centerController/getDutyPersonByMac";
	String result=Url.sendGet(methordUrl, "mac="+mac);
	JSONObject jsonObject=CosJSONUtil.string2json(result);
	if (jsonObject!=null){
		Map<String, String> map=new HashMap<String, String>();
		map.put("MessageType", "ToStartTalk");
		map.put("mac", mac);
		//2.向在线直播室用户发送呼叫消息
		String username=jsonObject.getString("userId");
		String areaName=jsonObject.getString("areaName");
		map.put("areaName", areaName);
		jsonObject=CosJSONUtil.toJsonObjectFromMap(map);
		DeviceInboundPool.sendMessageToUserExt(username, jsonObject.toString());
	}	
	}
	/**
	 * 中控开始接听平台呼叫开始
	 */
	public static void StartTalk(String mac) {
		//1.写呼叫日志
		String methordUrl=ServerUrls.WebSerUrl+"/centerController/voiceCallLogSave";
		String result=Url.sendGet(methordUrl, "mac="+mac+"&callerFlag=0");
		JSONObject jsonObject=CosJSONUtil.string2json(result);	
		if (jsonObject!=null){
			//2.通过mac获取直播室用户,和来电教室信息
			methordUrl=ServerUrls.WebSerUrl+"/centerController/getDutyPersonByMac";
			result=Url.sendGet(methordUrl, "mac="+mac);
			jsonObject=CosJSONUtil.string2json(result);
			Map<String, String> map=new HashMap<String, String>();
			map.put("MessageType", "StartTalk");
			map.put("id", jsonObject.getString("id"));
			if (jsonObject!=null){				
				map.put("mac", mac);
				//3.向在线直播室用户发送呼叫消息
				String username=jsonObject.getString("userId");
				String areaName=jsonObject.getString("areaName");
				map.put("areaName", areaName);
				jsonObject=CosJSONUtil.toJsonObjectFromMap(map);
				DeviceInboundPool.sendMessageToUserExt(username, jsonObject.toString());
				methordUrl=ServerUrls.WebSerUrl+"/centerController/voiceCallTime";
				result=Url.sendGet(methordUrl, "mac="+mac+"&state=1");
			}
		}
	}	
	/**
	 * 中控开始接听平台呼叫结束
	 */
	public static void StopTalk(String mac) {
		//1.通过mac获取直播室用户,和来电教室信息
		String methordUrl=ServerUrls.WebSerUrl+"/centerController/getDutyPersonByMac";
		String result=Url.sendGet(methordUrl, "mac="+mac);
		JSONObject jsonObject=CosJSONUtil.string2json(result);
		if (jsonObject!=null){
			Map<String, String> map=new HashMap<String, String>();
			map.put("MessageType", "StopTalk");
			map.put("mac", mac);
			//2.向在线直播室用户发送呼叫消息
			String username=jsonObject.getString("userId");
			String areaName=jsonObject.getString("areaName");
			map.put("areaName", areaName);
			jsonObject=CosJSONUtil.toJsonObjectFromMap(map);
			DeviceInboundPool.sendMessageToUserExt(username, jsonObject.toString());
			methordUrl=ServerUrls.WebSerUrl+"/centerController/voiceCallTime";
			result=Url.sendGet(methordUrl, "mac="+mac+"&state=3");
		}
	}
}
