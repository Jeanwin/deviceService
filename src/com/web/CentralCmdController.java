package com.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.CardMangeService;
import com.service.CentralControllService;
import com.util.CenterControlStatePool;
import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.JsonUtil;
import com.util.ResultExcepteutil;
import com.util.Url;

@Controller
public class CentralCmdController {
	/*
	 * 向中控发送命令
	 * 参数说明:
	 * *cmd:想中控发送的命令
	 * *mac:中控mac地址
	 */
	@RequestMapping(value = "/sendCmdToCentralControl", method = RequestMethod.GET)
	public void sendCmdToCentralControl(HttpServletRequest request,HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		if (cmd == null || "".equals(cmd))
		return;
		String mac=request.getParameter("mac");
		if (mac==null ||"".equals(mac))
			return;
		String reusltString=CentralControllService.sendCmdToCentralControl(mac, cmd);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(reusltString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	
	/*
	 * 向多个中控发送命令
	 * 参数说明:
	 * *cmd:想中控发送的命令
	 * *mac:中控mac地址, 用逗号隔开
	 */
	@RequestMapping(value = "/sendCmdToCentralControls", method = RequestMethod.GET)
	public void sendCmdToCentralControls(HttpServletRequest request,HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		if (cmd == null || "".equals(cmd))
		return;
		String macs=request.getParameter("mac");
		if (macs==null ||"".equals(macs))
			return;
		Map<String, String> map=new HashMap<String,String>();
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		String reusltString;
		String[] mac=macs.split(",");
		for (String m:mac) {
			reusltString=CentralControllService.sendCmdToCentralControl(m, cmd);
			JSONObject jsonObject=CosJSONUtil.string2json(reusltString);
			   if (jsonObject!=null){
				   reusltString=jsonObject.getString("result");
			       map.put("mac", m);
			       map.put("values", reusltString);
			       list.add(map);
			   }
		}
		String result=JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	/**
	 * 向中控批量发送命令
	 * 
	 */
	@RequestMapping(value = "/sendBatCmdToCentralControls", method = RequestMethod.POST)
	public void sendBatCmdToCentralControls(HttpServletRequest request,HttpServletResponse response) {
		String result=CentralControllService.sendBatCmdToCentralControls(request);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	/*
	 * 直接查询中控以及中控子设备的状态, 用于服务器重启,或者页面强行刷新 
	 * 参数说明:
	 * *mac:中控的mac地址 
	 */
	@RequestMapping(value = "/selectCentralState", method = RequestMethod.GET)
	public void selectCentralState(HttpServletRequest request,HttpServletResponse response) {
		String mac=request.getParameter("mac");
		if (mac==null ||"".equals(mac))
			return;		
		String[] macs=mac.split(",");
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		for(String mac1:macs){
		    JSONObject jsonObject=CentralControllService.selectCentralState(mac1);	
//		    map.put("mac",mac1);
//		    map.put("value", jsonObject);
		    Map<String, Object> map=new HashMap<String,Object>();
		    map.put("mac", mac1);
		    map.put("value", jsonObject);
		    list.add(map);
		}
		   
		//String result=ResultExcepteutil.ResulData(reusltString);
		String result=JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}		
	}
	
	/*
	 * 浏览器端查询服务器缓存中的设备状态, 浏览器定时刷新  map<ipstring,map<devicetype,state>>
	 * 参数说明:
	 * *ips:中控的ip地址 
	 */
	@RequestMapping(value = "/selectCentralStateFromEache", method = RequestMethod.GET)
	public void selectCentralStateFromEache(HttpServletRequest request,HttpServletResponse response) {
		String macs=request.getParameter("mac");
		if (macs==null ||"".equals(macs))
			return;	
		String[] macall=macs.split(",");
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		for(String mac:macall){
		    JSONObject json=CentralControllService.selectCentralStateFromEache(mac);	
		    Map<String, Object> map=new HashMap<String,Object>();
		    map.put("mac", mac);
		    map.put("value", json);
		    list.add(map);
		}
		String result=JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	/*
	 * 中控设备的状态发送变化, 反馈给平台的缓存,  有zkdm请求,发送数据返回   写入map<ipstring,map<devicetype,state>>
	 */
	@RequestMapping(value = "/centralStateChange", method = RequestMethod.GET)
	public void centralStateChange(HttpServletRequest request,HttpServletResponse response) {
		
	}
	
	/*
	 * 教室设备发送警报,中控返回警报给zkdm,zkdm请求该方法,然后该方法请求平台,通过邮件通知相关的工作人员 
	 */
	@RequestMapping(value = "/devicewalarm", method = RequestMethod.GET)
	public void devicewalarm(HttpServletRequest request,HttpServletResponse response) {
		
	}
	/*
	 * 代理注册自己和它所代理的中控    map<centralip,zkdmip>
	 */
	@RequestMapping(value = "/registerZkdm", method = RequestMethod.GET)
	public void registerZkdm(HttpServletRequest request,HttpServletResponse response) {
		
	}
	
	@RequestMapping(value = "/heartbeatZkdm", method = RequestMethod.GET)
	public void heartbeatZkdm(HttpServletRequest request,HttpServletResponse response) {
		
	}
	
	@RequestMapping(value = "/centralReturnData", method = RequestMethod.GET)
	public void centralReturnData(HttpServletRequest request,HttpServletResponse response) {
		String resultsString="centralReturnData error";
		try {
			String data=request.getParameter("data");  //centralcontroller
			String  centralmac=request.getParameter("mac");
			String url = Url.getServiceUrl(centralmac, "centralcontroller");
			if (!"".equals(url)||url!=null){
				resultsString=CentralControllService.centralReturnData(centralmac,data);	
		        resultsString=ResultExcepteutil.ResulData(resultsString,1).toString();
			}
		} catch (Exception e) {
			resultsString=ResultExcepteutil.ResulData(resultsString,0).toString();
		}
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(resultsString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
		
	}
  
	@RequestMapping(value = "/SetProjectorLedTime", method = RequestMethod.GET)
	public void SetProjectorLedTime(HttpServletRequest request,HttpServletResponse response){
		String blublife = request.getParameter("blublife");
		if (blublife == null || "".equals(blublife))
		return;
		String usetime = request.getParameter("usetime");
		if (usetime == null || "".equals(usetime))
		return;
		String mac=request.getParameter("mac");
		if (mac==null ||"".equals(mac))
			return;
		
        String reusltString=CentralControllService.SetProjectorLedTime(mac, blublife,usetime);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(reusltString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}		
	}
	/**
	 * 代理根据卡号获取卡号相关的老师登录名称，为老师刷卡，上课做准备
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCardInformation", method = RequestMethod.GET)
	public void getCardInformation(HttpServletRequest request,HttpServletResponse response,String cardNumber) {
		if (cardNumber!=null&& !"".equals(cardNumber)){		
			String reusltString=CardMangeService.getCardInformation(cardNumber);
			response.setContentType("text/html;charset=UTF-8");
			try {
				PrintWriter pw = response.getWriter();
				pw.print(reusltString);
				pw.flush();
				pw.close();
			} catch (Exception e) {
			}	
		}
	}
	
	@RequestMapping(value = "/M1cardNum", method = RequestMethod.GET)
	public void M1cardNum(HttpServletResponse response,String mac,String CardNum) {
		String reusltString;
		try {
			reusltString=CardMangeService.M1cardNum(mac, CardNum);
		} catch (Exception e) {
			reusltString="Exception";
		}
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(reusltString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	@RequestMapping(value = "/centralDbSynchronization", method = RequestMethod.GET)
	public void centralDbSynchronization(HttpServletRequest request,HttpServletResponse response) {
		String cmd = request.getParameter("cmd");
		if (cmd == null || "".equals(cmd))
		return;
		String mac=request.getParameter("mac");
		if (mac==null ||"".equals(mac))
			return;
		
        String reusltString=CentralControllService.centralDbSynchronization(mac, cmd);
		
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(reusltString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}		
	}
//	@RequestMapping(value = "/Websocekttest", method = RequestMethod.GET)
//	public void Websocekttest(HttpServletRequest request,HttpServletResponse response) {
//		DeviceInboundPool.sendMessageToUserExt("limin", "aaaaa");
//	}
}
