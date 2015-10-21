package com.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mediaServer.vds.vo.RetVdsLiveVo;
import com.service.GetInfoService;
import com.service.LivingService;
import com.util.LivingPool;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class LivingController {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/cleanLiving", method = RequestMethod.GET)
	@ResponseBody
	public void cleanLiving(HttpServletRequest request,
			HttpServletResponse response) {
		LivingPool.removeAll();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print("ok");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/stopLiving", method = RequestMethod.GET)
	@ResponseBody
	public void stopLiving(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		LivingService.stopLiving(mac);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/living", method = RequestMethod.GET)
	public void RTMPLiving(HttpServletRequest request,
			HttpServletResponse response) {
		String isLive = request.getParameter("isLive");
		if (isLive == null || "".equals(isLive)||"0".equals(isLive)){
			isLive="0";
		}else{
			isLive="1";
		}
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		String para = request.getParameter("para");
		if (para == null || "".equals(para)){
			para="start";
		}
		String str="";
		if("stop".equals(para)){
			//LivingService.stopAndSet(mac);
		}else{
			String endtime = request.getParameter("endTime");
			if (endtime == null || "".equals(endtime)){
				endtime="2099-01-31 12:00:00";
			}
			List<String> seats=new ArrayList<>();
			seats.add("card0");
			seats.add("card2");
			seats.add("card4");
			if (!"1".equals(isLive)){
				//str=LivingService.startLiving(mac);  //modify  by limin 20150618
				str=LivingService.StartPullLiving(mac,seats);    
			}else{
				//str=LivingService.startLiving(mac,endtime,isLive);
				str=LivingService.StartPullLiving(mac,seats);
			}
		}

		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	@RequestMapping(value = "/livingStart", method = RequestMethod.GET)
	@ResponseBody
	public void RTMPLivingStart(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		String endTime = request.getParameter("endTime");
		if (endTime == null || "".equals(endTime))
			endTime="2019-01-31 12:00:00";

		String str="";
		RetVdsLiveVo retVdsLiveVo = LivingService.getVds(mac);
		if(retVdsLiveVo==null){
			String isLive ="1";
			str=LivingService.startLiving(mac,endTime,isLive);
		}
		
//		JSONObject jSONObject=JSONObject.fromObject(retVdsLiveVo.getContent());
//		String str =jSONObject.toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/livingStauts", method = RequestMethod.GET)
	@ResponseBody
	public void stuts(HttpServletRequest request, HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String str =GetInfoService.getLiveStatus(mac);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}else{
				pw.print("返回数据为空");
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	// public void RTMPLivingXXX(HttpServletRequest request,
	// HttpServletResponse response) {
	// RTMPLivingService.livingStart();
	// // RTMPLivingService.livingAll();
	// // System.out.println();
	// // System.out.println();
	//
	// String codeUrl = "http://192.168.12.211:50501/transcode/transinfo";
	// CodeLiveVo vo = new CodeLiveVo();
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("rtmp_repeater", "rtmp://192.168.12.103:1931/zonekey/test");
	// map.put("uid", "xx-xxxx-xxxx-xxx");
	// list.add(map);
	// vo.setRate("3000k");
	// vo.setFramerate("25");
	// vo.setResolution("1920*1080");
	// vo.setSamprate("24000");
	// vo.setTranscodingurl(list);
	// vo.setRtmp_repeater("rtmp://192.168.12.103:1931/zonekey/test");
	// vo.setUid("xx-xxxx-xxxx-xxx");
	// vo.setChannel("2");
	// vo.setAudiorate("96");
	// vo.setGrade("1");
	// List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
	// l.add(vo.toMap());
	// String str = CosJSONUtil.json2Map(
	// CosJSONUtil.string2json(CodeLiveService.start(codeUrl,
	// CodeLiveVo.toJson(l).toString()))).get("content");
	// System.out.println("--------------------------------------");
	// System.out.println(str);
	// String url = "http://192.168.12.117:50201/vds/web";
	// HttpExec.doPostWithJSON(url, str);
	// }
}
