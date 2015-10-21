package com.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.auto.AutoJob;
import com.util.CosJSONUtil;
import com.util.JsonUtil;
import com.util.ServerUrls;
import com.util.Url;

public class CardMangeService {
	private static Logger logger = Logger.getLogger(AutoJob.class);
	/**
	 * 平台接收到中控发送来的GetCardNums命令
	 * @param mac
	 */
	public static void CardNumList(String mac) {
		try {
			//1.获取特殊卡号列表
			String methordUrl=ServerUrls.WebSerUrl+"/centerController/getAllSpecialCard";
			String result=Url.sendGet(methordUrl, "");
			//JSONObject jsonObject=CosJSONUtil.string2json(result);
			if (result!=null&&!"".equals(result)){
				JSONArray jArray=JSONArray.fromObject(result);
				if (jArray.size()>0){
					//List<Map<String,Object>> maplist=JsonUtil.jsonToObject(jsonObject, List.class);
					String AddCardNumList="AddCardNumList:";
					String DelCardNumList="DelCardNumList:";
					for (int i = 0; i < jArray.size(); i++) {
						JSONObject jo = (JSONObject) jArray.getJSONObject(i);
						if ("0".equals(jo.getString("cardState"))){
						    AddCardNumList=AddCardNumList+jo.getString("cardNumber")+"/";
						}else{
							DelCardNumList=DelCardNumList+jo.getString("cardNumber")+"/";
						}
					}
					AddCardNumList=AddCardNumList.substring(0, AddCardNumList.length()-1);
					DelCardNumList=DelCardNumList.substring(0, DelCardNumList.length()-1);
					//2.想mac的中控发送特殊卡号列表
					CentralControllService.sendCmdToCentralControl(mac, AddCardNumList);
					//3.想mac的中控发送挂失和删除的特殊卡号列表
					CentralControllService.sendCmdToCentralControl(mac, DelCardNumList);
				}
			}
			
		} catch (Exception e) {
			logger.info("CardNumList"+e.getMessage());
		}
		
		
	}
	
	public static String getCardInformation(String CardNum) {
		String methordUrl=ServerUrls.WebSerUrl+"/centerController/getCardInformation";
		String result=Url.sendGet(methordUrl, "cardNumber="+CardNum);
		if (result!=null &&!"".equals(result)){
			return result;
		}
		return "";
	}
	
	/**
	 * 平台接收到普通卡号和mac地址
	 */
	public static String M1cardNum(String mac,String CardNum) {
		//1.用卡号获取对应的老师登录名称
		String cardInfoString=getCardInformation(CardNum);
		if (!"".equals(cardInfoString)){
			JSONObject jsonObject=CosJSONUtil.string2json(cardInfoString);
			String teacherNameString=jsonObject.getString("cardPerson");
			//2.根据mac和老师的登录名称,获取当前卡号,是否有课程
			String methordUrl=ServerUrls.WebSerUrl+"/centerController/checkTeacherTime";
			String result=Url.sendGet(methordUrl, "loginname="+teacherNameString+"&mac="+mac);
			if ("true".equals(result)){
				//3.如果有课程,想中控发送RightCardNum命令
				return CentralControllService.sendCmdToCentralControl(mac, "RightCardNum");
			}
		}
		return "error";
	}

}
