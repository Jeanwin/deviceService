package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disrec.service.Disrec2DeviceService;
import com.mediaServer.transcode.service.CodeLiveService;
import com.mediaServer.transcode.vo.CodeLiveVo;
import com.mediaServer.vds.service.VdsLiveService;
import com.net.tcp.TcpClinet;
import com.service.GetInfoService;
import com.service.LivingService;
import com.service.RecordingService;
import com.util.CosJSONUtil;
import com.util.DeviceRtspUrlsPool;
import com.util.JsonUtil;
import com.util.Url;
import com.vo.QueryRAllInfo;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class RecordingController {
	@Resource
	RecordingService recordingService;

	/**
	 * 刷新
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/consoleFlush", method = RequestMethod.GET)
	public void consoleFlush(HttpServletRequest request,HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String str=LivingService.stopAndStartLiving(mac);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	/**
	 * 预览 
	 * @param request
	 * @param response
	 * 1. 给平台发出 getServerUrl?type=middle 命令，得到中继地址
       2. 给中继发出 prepublishbatch 命令，得到一组 rtmp 地址；
       3. 把收到的 rtmp 地址发给录播机，用的是 BroadCastCmd=RtmpUrlS
       4. 给录播机发送 BroadCastCmd=StartBroadCast 命令
	 */
	@RequestMapping(value = "/rtspPreview", method = RequestMethod.GET)
	public void rtspPreview(HttpServletRequest request,HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		//String str = PreView.RtspPreview(mac);
		String isLive ="0";
        Calendar c = Calendar.getInstance();  
        c.setTime(new Date());
        c.add(Calendar.HOUR, 1);
        Date date = c.getTime(); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = formatter.format(date);
		//String str =LivingService.startLiving(mac,endTime,isLive);
		//开始让录播进行直播 
		String str =LivingService.startLiving(mac);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	/**
	 * 平台标题
	 */
	@RequestMapping(value = "/consoleTitile", method = RequestMethod.GET)
	public void consoleTitiel(HttpServletRequest request,HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		QueryRAllInfo queryRAllInfo = GetInfoService
				.getRecordStatusFromCreateQueryRAllInfo(RecordingService
						.execute(mac,"RecordCmd=QueryRAllInfo"));
		Map<String, String> map = new HashMap<String, String>();
		if(queryRAllInfo == null){
			queryRAllInfo = new QueryRAllInfo();
		}
		map.put("a0", queryRAllInfo.getCardInfo0());
		map.put("a1", queryRAllInfo.getCardInfo1());
		map.put("a2", queryRAllInfo.getCardInfo2());
		map.put("a3", queryRAllInfo.getCardInfo3());
		map.put("a4", queryRAllInfo.getCardInfo4());
		map.put("a5", queryRAllInfo.getCardInfo5());
		JSONObject jSONObject=CosJSONUtil.toJsonObjectFromMap(map);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(jSONObject.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/recording", method = RequestMethod.GET)
	public void recording(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String para = request.getParameter("para");
		//设置子目录
		String resourceFloder = request.getParameter("resourcefloder");
		if(resourceFloder != null){
			recordingService.SetFileFolder(mac,resourceFloder);
		}
		if (para == null || "".equals(para))
			return;
//		System.out.println("para------- : " + para);
		RecordingService.recording(mac,para);
	}
	
	@RequestMapping(value = "/sendCmdtoRecord", method = RequestMethod.GET)
	public void sendCmdtoRecord(HttpServletRequest request,HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String para = request.getParameter("cmd");   
		if (para == null || "".equals(para))
			return;
		String str=RecordingService.SendCmdtoRecord(mac,para);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str);
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	@RequestMapping(value = "/sendCmdtoRecords", method = RequestMethod.GET)
	public void sendCmdtoRecords(HttpServletRequest request,HttpServletResponse response) {
		String macs = request.getParameter("mac");
		if (macs == null || "".equals(macs))
		return;
		String para = request.getParameter("cmd");
		if (para == null || "".equals(para))
			return;
		Map<String, String> map=new HashMap<String,String>();
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		String[] arrmac=macs.split(",");
		for(String mac:arrmac){
		   String str=RecordingService.SendCmdtoRecord(mac,para);
		   JSONObject jsonObject=CosJSONUtil.string2json(str);
		   if (jsonObject!=null){
			   str=jsonObject.getString("result");
		       map.put("mac", mac);
		       map.put("values", str);
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
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/recordingStauts", method = RequestMethod.GET)
	@ResponseBody
	public void stuts(HttpServletRequest request, HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;

		String str = GetInfoService.getRecordStatus(mac);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}

		// String str =GetInfoService.getRecordStatus();
		// System.out.println(str);
		// return "RecordStopped";
	}

	/**
	 * RecordCmd=SetVolume&CardPort={0}&Volume={1}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/setVolume", method = RequestMethod.GET)
	@ResponseBody
	public void setVolume(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String volume = request.getParameter("volume");
		if (volume == null || "".equals(volume))
			return;
		String result = RecordingService.setVolume(mac,volume);
		//System.out.println("setVolume  == >"+result);
	}
	
	/**
	 * RecordCmd=setDeviceTime&classbatch={0}&endtime={1}
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/setDeviceTime", method = RequestMethod.GET)
	@ResponseBody
	public void setDeviceTime(HttpServletRequest request) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String endtime = request.getParameter("endtime");
		if (endtime == null || "".equals(endtime))
			return;
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)){
			String result = Disrec2DeviceService.updateEndtimeByBatch(id,endtime);
			if(result.contains("ok")){
				String res = RecordingService.setDeviceTime(mac,endtime);
				//System.out.println("setDeviceTime  == >"+res);
			}
		}
	}
	/*
	 * 向录播发送操作命令方法
	 * 比如：获取rtsp地址：命令发送 “RecordCmd=QueryRtspUrls”
	 * 参数说明：sendToRecord
	 * *参数 IP为录播的ip地址
	 */
	@RequestMapping(value = "/sendToRecord", method = RequestMethod.GET)
	public void sendToRecord(HttpServletRequest request,HttpServletResponse response) throws UnknownHostException, IOException {
		String IP = request.getParameter("IP");
		String RecordCmd=request.getParameter("RecordCmd");
		if (IP==null ||"".equals(IP))
			return;
	    if (RecordCmd==null || "".equals(RecordCmd))
	    	return;

		Map<String, String> map=recordingService.sendToRecord(IP, RecordCmd);
		JSONObject jSONObject=CosJSONUtil.toJsonObjectFromMap(map);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(jSONObject.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	
		
	}

	/*
	 * 查询录播机子的录像模式
	 * 录像模式分为三中：Resours，Movie，All
	 * 参数说明：/queryRecordMode"
	 * *Ip为录播ip地址
	 */
	@RequestMapping(value = "/queryRecordMode", method = RequestMethod.GET)
	public void queryRecordMode(HttpServletRequest request,HttpServletResponse response) throws UnknownHostException, IOException {
		String IP = request.getParameter("IP");
		if (IP==null ||"".equals(IP))
			return;
		String host = IP;
		int timeOut = 3000;
		boolean status = InetAddress.getByName(host).isReachable(timeOut);
		if (status) 
		{
			//Map<String, String> map = new HashMap<String, String>();
			TcpClinet tcpClinet=new TcpClinet(IP, 1230,"RecordCmd=QueryRAllInfo");
			String RecordMode=tcpClinet.QueryRecordMode();	
			JSONObject jSONObject=new JSONObject();
			jSONObject.put("RecordMode", RecordMode);
			response.setContentType("text/html;charset=UTF-8");
			try {
				PrintWriter pw = response.getWriter();
				pw.print(jSONObject.toString());
				pw.flush();
				pw.close();
			} catch (Exception e) {
			}
		}		
	}
	
	/**
	 * 录像信息
	 * @param request
	 * @param response
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryAllRecordMode", method = RequestMethod.GET)
	public void queryAllRecordMode(HttpServletRequest request,HttpServletResponse response) throws UnknownHostException, IOException {
		String mac = request.getParameter("mac");
		if (mac==null ||"".equals(mac))
			return;
		String deviceType = request.getParameter("deviceType");
		if (deviceType==null ||"".equals(deviceType))
			return;		
		JSONObject jSONObject=new JSONObject();
		try {
			String RecordMode=RecordingService.queryAllRecordMode(mac,deviceType);
			//System.out.println(RecordMode);
			if ("error".equals(RecordMode)||RecordMode==null || "".equals(RecordMode)){
				jSONObject.put("RecordMode", "error");
			}else {
				jSONObject.put("RecordMode", RecordMode);
			}
		} catch (Exception e) {
			jSONObject.put("RecordMode", "error");
		}
		
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(jSONObject.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}		
	}
	
	
	/*
	 * 根据录播的rtsp地址获取,开始转码服务
	 * 参数说明：
	 * *IP为流媒体服务器的ip地址
	 * *PORT为流媒体服务器的端口
	 * *RecordParm是给流媒体发送的参数。具体参考拉流文档
	 */
	@RequestMapping(value = "/startTranscodeLiving", method = RequestMethod.POST)
	@ResponseBody
	public void startTranscodeLiving(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> data = JsonUtil.jsonToObject(request, Map.class);
		String IP=data.get("IP").toString();
		String port=data.get("PORT").toString();
		String paramString = data.get("RecordParm").toString();
		
//		String port=request.getParameter("PORT");
		
		String codeUrl = "http://"+IP+":"+port+"/transcode/transinfo";
		
		String writeString=CodeLiveService.start(codeUrl, paramString);
		
		//System.out.println(writeString);
		
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(writeString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	/*
	 * 停止向流媒体服务器拉流方法
	 * 参数说明：
	 * *IP为流媒体服务器的ip地址
	 * *PORT为流媒体服务器的端口号
	 * *RecordParm为向流媒体服务器发送停止的相关参数
	 */
	@RequestMapping(value = "/stopTranscodeLiving", method = RequestMethod.POST)
	public void stopTranscodeLiving(HttpServletRequest request,HttpServletResponse response) {
		String paramString = request.getParameter("RecordParm");
		String IP=request.getParameter("IP");
		String port=request.getParameter("PORT");
		String codeUrl = "http://"+IP+":"+port+"/transcode/transinfo";
		
		String writeString=CodeLiveService.stop(codeUrl, paramString);
		
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(writeString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}		
	}
	/**
	 * 从代理获取RTSP地址,为导播,转码,分发做准备
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRtspUrlFromZddm", method = RequestMethod.GET)
	public void getRtspUrlFromZddm(HttpServletRequest request,HttpServletResponse response){
		String mac=request.getParameter("mac");
		String deviceType=request.getParameter("deviceType");
		if (mac==null ||"".equals(mac))
			return;
		if (deviceType==null ||"".equals(deviceType))
			return;	
		JSONObject jsonObject=RecordingService.GetRtspUrlFromZkdmByMac(mac, deviceType);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(jsonObject.toString());
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}		
	}
	
	/*
	 * 开始分发方法,其中rtmp地址是从转码中获取的
	 * 参数说明：
	 * *IP为流媒体服务器的ip地址
	 * *PORT为流媒体服务器的端口
	 * *RecordParm是给流媒体发送的参数。具体参考拉流文档
	 */
	@RequestMapping(value = "/startVdsLiving", method = RequestMethod.POST)
	@ResponseBody
	public void startVdsLiving(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> data = JsonUtil.jsonToObject(request, Map.class);
		String IP=data.get("IP").toString();
		String port=data.get("PORT").toString();
		String paramString = data.get("RecordParm").toString();	
		
		String url="http://"+IP+":"+port+"/vds/web";
		String vds = VdsLiveService.start(url, paramString);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(vds);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
	/*
	 * 根据转码返回的rtmp地址,获取分发的rtmp地址
	 * 参数说明：
	 * *IP为流媒体服务器的ip地址
	 * *PORT为流媒体服务器的端口
	 * *RecordParm是给流媒体发送的参数。具体参考拉流文档
	 */
	@RequestMapping(value = "/stopVdsLiving", method = RequestMethod.POST)
	@ResponseBody
	public void stopVdsLiving(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> data = JsonUtil.jsonToObject(request, Map.class);
		String IP=data.get("IP").toString();
		String port=data.get("PORT").toString();
		String paramString = data.get("RecordParm").toString();	
		
		String url="http://"+IP+":"+port+"/vds/web";
		String vds = VdsLiveService.stop(url, paramString);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(vds);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}	
	
}
