package com.service;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.iterators.ReverseListIterator;
import org.apache.log4j.Logger;
import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;

import com.util.CenterControlStatePool;
import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.JsonUtil;
import com.util.Url;
import com.vo.CentralCmdsVo;
import com.web.PerConsoleController;

public class CentralControllService {
	private static Logger logger = Logger.getLogger(PerConsoleController.class);
   /**
    * 向中控发送控制命令
    * @param mac  中控的mac地址
    * @param cmd  发送给中控的命令
    * @return
    */
   public static String sendCmdToCentralControl(String mac,String cmd) {
		String url = Url.getServiceUrl(mac, "centralcontroller");
		String reusltString;
		if ((url==null)&&("".equals(url))){
			reusltString="";
		} else {
			reusltString=Url.sendGet(url+"/cmd", "CsnCmd="+cmd);
		}	
		return reusltString;
   }	
   
   /**
    * 向中控批量发送命令
    * @param request
    * @return
    */
   public static String sendBatCmdToCentralControls(HttpServletRequest request) {
	   List<Map<String,Object>> list = JsonUtil.jsonToObject(request, List.class);
	   List<Map<String,String>> resultList=new ArrayList<Map<String,String>>();
	   String result="no run";
       for (Map<String,Object> map:list) {
    	   try {
    		   String mac=map.get("mac").toString();
    		   String url = Url.getServiceUrl(mac, "centralcontroller");
    		   if ((url!=null)&&(!"".equals(url))){
    			   String reusltString=Url.sendGet(url+"/cmd","CsnCmd="+CosJSONUtil.toJsonObjectFromMap(map).toString());
    			   Map<String, String> resulMap=new HashMap<String,String>();
    			   if (reusltString!=null && !"".equals(reusltString)){
    				   JSONObject jsonObject=CosJSONUtil.string2json(reusltString);
    				   resulMap.put("mac", mac);
    				   resulMap.put("result", jsonObject.getString("result"));
    			   }else {
    				   resulMap.put("mac", mac);
    				   resulMap.put("result", "error");
				    }
    			   resultList.add(resulMap);
    			   
    		   }
			} catch (Exception e) {
				 logger.info("sendBatCmdToCentralControls"+e.getMessage());
			}
	    }
       result=JSONArray.fromObject(list).toString();
       return result;
}
   
   /**
    * 告诉中控同步DB文件
    * *NewDBconf:IP&PORT&USER&PWD  平台告诉中控分离，并将DB上传到FTP目录
    * *GetCsnDBconf:IP&PORT&USER&PWD 告诉中控去FTP服务器下载DB文件
    * @param mac
    * @param cmd
    * @return
    */
   public static String centralDbSynchronization(String mac,String cmd) {
	   String FtpUrl=Url.getServerUrl("ftp");
	   String FtpNamePwd=Url.getServerUrl("ftpnp");
	   int portIndex=FtpUrl.lastIndexOf(":");
		String port=FtpUrl.substring(portIndex+1);
		String ip=FtpUrl.substring(FtpUrl.indexOf("//")+2,portIndex);
		String temcmd=cmd+":IP="+ip+"&PORT="+port+FtpNamePwd;
		String url = Url.getServiceUrl(mac, "centralcontroller");
		String reusltString;
		if ((url==null)&&("".equals(url))){
			reusltString="";
		} else {
			reusltString=Url.sendGet(url+"/cmd", "CsnCmd="+temcmd);
		}	
		return reusltString;
   }	 
   /**
    * 直接向中控发送查询状态命令,进行状态的查询, 同时把查询的结果,写入状态的缓存中.
    * @param mac  :中控的mac地址
    * @return
    */
   public static JSONObject selectCentralState(String mac) {
		String url = Url.getServiceUrl(mac, "centralcontroller");
		String deviceState=Url.sendGet(url+"/cmd", "CsnCmd=GetAllState");
	    JSONObject jsonObject=CosJSONUtil.string2json(deviceState);
	    if ("ok".equals(jsonObject.getString("result"))){
	    	String infovaluesString=jsonObject.getString("info").split(":")[1];
	    	//infovaluesString=infovaluesString.substring(0, infovaluesString.length()-2);
	    	String[] strings=infovaluesString.split("//");
	    	for (String string : strings) {
	    		saveOrUpdateDeviceState(mac,string);
			}
	    }
	    return selectCentralStateFromEache(mac);
   }
   
   /**
    * 从缓存中获取中控的设备状态,缓存记录了中控改变的设备状态.
    * @param CentralMac
    * @return
    */
   public static JSONObject selectCentralStateFromEache(String CentralMac) {
		JSONObject json=new JSONObject();
		if ( CenterControlStatePool.getCenterControlAllStateByMac(CentralMac)==null
				|| CenterControlStatePool.getCenterControlAllStateByMac(CentralMac).isEmpty()) {
			//json.put("info", "No State");
			//json.put("result", "error");
			json=selectCentralState(CentralMac);
		}else {
			json = CosJSONUtil.toJsonObjectFromMap(CenterControlStatePool.getCenterControlAllStateByMac(CentralMac));
		}
	    return json;
   }
   
   /**
    * 保存或者更新缓存中的设备状态
    * @param centralmac   :设备的mac地址
    * @param deviceState  :设备的状态
    */
   public static void saveOrUpdateDeviceState(String centralmac,String deviceState) {
		String key= deviceState.substring(0, deviceState.indexOf("="));
		String value =deviceState.substring(deviceState.indexOf("=")+1);
		
		if (CenterControlStatePool.getCenterControlAllStateByMac(centralmac)==null ||
				CenterControlStatePool.getCenterControlAllStateByMac(centralmac).isEmpty()) {
			Map<String, String> map=new HashMap<String, String>();
			map.put(key, value);
			CenterControlStatePool.addCenterControlstate(centralmac, map);
		} else {
			CenterControlStatePool.getCenterControlAllStateByMac(centralmac).put(key, value);
		}
   }
   
   public static String SetProjectorLedTime(String mac,String blublife,String usetime) {
	   String[] strings=mac.split(",");
	   String urlresult;
	   JSONObject resultString=new JSONObject();
	   for(String s:strings){
		   String url = Url.getServiceUrl(s, "centralcontroller");
			if ((url==null)&&("".equals(url))){
                 continue;
			} else {
				urlresult=Url.sendGet(url+"/cmd", "CsnCmd=SetProjectorLedTime:"+usetime+"/"+blublife);
			}	
			JSONObject jsonObject=CosJSONUtil.string2json(urlresult);
			resultString.put(s, jsonObject.getString("result"));
	   }
	   return resultString.toString();
	
}
   
   /**
    * 中控返回的数据,状态写如缓存.
    * I/O报警需要写入数据库,同时发送邮件等
    * @param centralmac
    * @param data
    */
   public static String centralReturnData(String centralmac,String data) {
	    //CsnCmd=CsnUpdateState:SetSoundVolume=立体声音量&0&3=音量3
	   //CsnCmd=IoAlarm:N/M
	   String Result=data;
	   String temString;
	   String[] strings;
	   if (data.indexOf(":")>0){
		   strings=data.split(":"); 
		   temString=strings[0].split("=")[1];
		   if ("CsnUpdateState".equals(temString)){
				saveOrUpdateDeviceState(centralmac,strings[1]);
			}
	    }else {
		   temString=data.split("=")[1]; 
	    }	

	   //IO报警返回数据  
	   if ("IoAlarm".equals(temString)){
		   if (data.indexOf(":")>0){
			    String N=data.split(":")[1].split("/")[0];
			    String Y=data.split(":")[1].split("/")[1];
			    Result=IoAlarmServcie.ManageIoAlarm(centralmac,N,Y);
		   }
		}
        if ("IoNotice".equals(temString)){
			//IoNoticeService
		}
        /**
         * 中控向平台特殊卡要验证信息
         * 当平台收到该命令后.向在线的所有中控 通过CsnCmd=CardNumList:XXXXXXXX/XXXXXXXX/XXXXXXXX/… 命令下方所有特殊卡号
         */
        if ("GetCardNums".equals(temString)){
        	CardMangeService.CardNumList(centralmac);
		}
        /**
         * 中控向平台上传普通卡，获取当前卡号是否有上课。如果有在发送：RightCardNum
         */
        if("M1cardNum".equals(temString)) {
           if (data.indexOf(":")>0){
        	   String CardNum=data.split(":")[1];
        	    CardMangeService.M1cardNum(centralmac, CardNum);
           }
        }
        /**
         * 中控呼叫平台,平台推送消息
         */
        if ("ToStartTalk".equals(temString)){
        	MangeStartTalk.centralcontrollToTalk(centralmac);
		}
        /**
         * 中控开始接听平台呼叫开始
         */
        if ("StartTalk".equals(temString)){
        	MangeStartTalk.StartTalk(centralmac);
        }
        /**
         * 中控开始接听平台呼叫结束
         */
        if ("StopTalk".equals(temString)){
        	MangeStartTalk.StopTalk(centralmac);
        }
        /**
         * ProjectorOFF:投影机掉电通知
         */
        if ("ProjectorOFF".equals(temString)) {
        	ProjectorService.ProjectorOff(centralmac);
		}
		return Result;
   }
}
