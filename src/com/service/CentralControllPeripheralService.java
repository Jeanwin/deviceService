package com.service;

import net.sf.json.JSONObject;

import com.util.CosJSONUtil;
import com.util.Url;

public class CentralControllPeripheralService {
	public static void autoBatJobs(String mac,String Url) {
		UpdateProjectorLedTime(mac,Url);
	}
	
	public static void UpdateProjectorLedTime(String mac,String Urls) {
     
		try {
			String ret = Url.sendGet(Urls + "/cmd", "CsnCmd=GetProjectorLedTime");
			JSONObject jObject=CosJSONUtil.string2json(ret);
			if (jObject!=null && "ok".equals(jObject.getString("result"))){
				String dateString=jObject.getString("info").split(":")[1];
				String useTime=dateString.split("/")[0];
				String lifeTime=dateString.split("/")[1];
				String methordUrl=Url.getServerUrl("web")+"/lightSet/cenConLightSet";
				Url.sendGet(methordUrl,"mac="+mac+"&usedLength="+useTime+"&maxLength="+lifeTime);
			}
		} catch (Exception e) {

		}
	}

}
