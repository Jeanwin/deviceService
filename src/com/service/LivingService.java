package com.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiDevice.Info;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.auto.AutoJob;
import com.mediaServer.live.vo.RetLive2Vo;
import com.mediaServer.live.vo.RetLiveVo;
import com.mediaServer.live.vo.TranLive2vo;
import com.mediaServer.transcode.service.CodeLiveService;
import com.mediaServer.transcode.vo.CodeLiveVo;
import com.mediaServer.transcode.vo.RetCodeLive2Vo;
import com.mediaServer.transcode.vo.RetCodeLiveVo;
import com.mediaServer.vds.service.VdsLiveService;
import com.mediaServer.vds.vo.InVdsLiveVo;
import com.mediaServer.vds.vo.RetVdsLive3Vo;
import com.mediaServer.vds.vo.RetVdsLiveStatusVo;
import com.mediaServer.vds.vo.RetVdsLiveVo;
import com.util.CosJSONUtil;
import com.util.DeviceLogs;
import com.util.DeviceRtspUrlsPool;
import com.util.JsonUtil;
import com.util.LivingPool;
import com.util.Url;

/**
 * @author zfc RTMP
 * 
 *         设置地址+直播：/recording/cmd?RecordCmd=RTMPLiving
 *         直接开始直播：/recording/cmd?BroadCastCmd=StartBroadCast
 *         停止直播：/recording/cmd?BroadCastCmd=StopBrodaCast
 *         http://localhost:8080/deviceService/living?mac=000000000003
 */
public class LivingService {
	private static Logger logger = Logger.getLogger(LivingService.class);
	private static String VdsUrl = Url.getServerUrl("vds") + "/vds/web";
	private static String codeUrl = Url.getServerUrl("code")
			+ "/transcode/transinfo";

	public static Boolean useTransCode() {
		return Url.useTransCode();
	}

	public static Boolean usevds() {
		return Url.usevds();
	}

	public static String getServiceUrl(String mac, String serviceName) {
		return Url.getServiceUrl(mac, serviceName);
	}

	public static void stopLiving(String mac) {
		// 直播
		controlDirection(mac, "BroadCastCmd=StopBroadCast");
		// RetLivingVo retLivingVo = RetLivingVo.ToRetLivingVo(s);
		// livingPool.getInstance().removeMessageInbound(mac);
	}
	/**
	 * 录播机重新开启
	 * @param mac
	 * @return
	 */
	public static String stopAndStartLiving(String mac) {
		String ret = "ok";
		try {
			try {
				String result = controlDirection(mac, "BroadCastCmd=StopBroadCast");
				//System.out.println("BroadCastCmd=StopBroadCast ===》"+result);
			} catch (Exception e) {
			}
			Thread.sleep(3000);
			Map<String, String> inVdsMap = new HashMap<String, String>();
			List<Map<String, String>> listRet = new ArrayList<Map<String, String>>();
			String s = controlDirection(mac, "RecordCmd=RTMPLiving");
			RetLiveVo retLiveVo = RetLiveVo.ToRetLiveVo(s);
			if (!("ok".equals(retLiveVo.getResult())))
				ret = "";
			inVdsMap = new HashMap<String, String>();
			listRet = new ArrayList<Map<String, String>>();
			for (RetLive2Vo vo : retLiveVo.getInfo()) {
				inVdsMap.put(vo.getCard_info(), vo.getRtmp_repeater());
			}
			listRet.add(inVdsMap);
			ret = JSONArray.fromObject(listRet).toString();
			if (ret!=null||!"".equals(ret)) {
				new DeviceLogs().log(mac+" 录播机开启直播成功。。。");
			}
			LivingPool.addLiving(mac, ret);
		} catch (Exception e) {
			ret = "error";
		}
		return ret;
		
	}
	//录播主机先停止广播，然后在开始开始广播
	public static String startLiving(String mac) {
		String ret = LivingPool.getLiving(mac);
		if (ret==null||"".equals(ret)) {
			try {
				try {
					controlDirection(mac, "BroadCastCmd=StopBroadCast");
				} catch (Exception e) {
				}
				Thread.sleep(3000);
				Map<String, String> inVdsMap = new HashMap<String, String>();
				List<Map<String, String>> listRet = new ArrayList<Map<String, String>>();
				String s = controlDirection(mac, "RecordCmd=RTMPLiving");
				RetLiveVo retLiveVo = RetLiveVo.ToRetLiveVo(s);
				if (!("ok".equals(retLiveVo.getResult())))
					ret = "";
				inVdsMap = new HashMap<String, String>();
				listRet = new ArrayList<Map<String, String>>();
				for (RetLive2Vo vo : retLiveVo.getInfo()) {
					inVdsMap.put(vo.getCard_info(), vo.getRtmp_repeater());
				}
				listRet.add(inVdsMap);
				ret = JSONArray.fromObject(listRet).toString();
				if (ret!=null||!"".equals(ret)) {
					new DeviceLogs().log(mac+" 录播机开启直播成功。。。");
				}
				LivingPool.addLiving(mac, ret);
			} catch (Exception e) {
				ret = "";
			}
		}
//		if (ret==null||"".equals(ret)) {
//			logger.info(mac+"录播机开启直播成功。。。");
//		}else{
//			logger.info(mac+"录播机开启直播成功失败。。。");
//		}
		return ret;
	}

	
	public static  String GetCardByName(String name) {
		if ("teacher".equals(name)) return "card0";
		if ("full".equals(name)) return "card1";
		if ("students".equals(name)) return "card2";
		if ("vga".equals(name)) return "card4";
		if ("film".equals(name)) return "card6";
		
		if ("教师".equals(name)) return "card0";
		if ("学生".equals(name)) return "card2";
		if ("VGA".equals(name)) return "card4";
		if ("教师全景".equals(name)) return "card1";
		if ("电影".equals(name)) return "card6";
		if ("板书".equals(name)) return "card3";
		if ("学生全景".equals(name)) return "card7";
		return "";
	}
	
	public static List<String> sendCommandToMult(String mac,String param) {
		List<String> list=new ArrayList<String>();
		String urlString=getServiceUrl(mac, "recording");
		if ((urlString!=null) ||!"".equals(urlString)){
		String ret =sendGet(urlString + "/cmd", param);
			JSONObject jsonObject=CosJSONUtil.string2json(ret);
			if (jsonObject!=null){
				String temString1= jsonObject.getString("result");
				if ("ok".equals(temString1))
				  list.add(ret);
			}
		}
		urlString=getServiceUrl(mac, "centralcontroller");
		if ((urlString!=null) ||!"".equals(urlString)){
			String ret =sendGet(urlString + "/cmd", param);
			JSONObject jsonObject=CosJSONUtil.string2json(ret);
			//System.out.println("null".equals(jsonObject.toString()));
			if (jsonObject!=null){
				String temString1= jsonObject.getString("result");
				if ("ok".equals(temString1))
				  list.add(ret);
			}
		}
		return list;
	}
	
	public static String sendCommandToDevice(String mac,String param) {
		if (param == null || "".equals(param)) return "";
		String url = Url.getServiceUrl(mac, "recording");
		if (url == null || "".equals(url))
			url = Url.getServiceUrl(mac, "centralcontroller");
		if (url == null || "".equals(url)) return "";
		String ret = "";
		try {
			ret = sendGet(url + "/cmd", param);
		} catch (Exception e) {
			return "";
		}
		return ret;		
	}
	
	/**
	 * 废弃
	 * @param mac
	 * @return
	 */
	public static String getRtspUrlfromMult(String mac) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		List<String> list=sendCommandToMult(mac,"RecordCmd=QueryRtspUrls");
		if (list.isEmpty()) return "";
		Iterator it = list.iterator(); 
		while (it.hasNext()) {
			String surl = (String) it.next();
			JSONObject jsonObject=CosJSONUtil.string2json(surl);
            String temString= jsonObject.getString("info");
//            if ("\r\n".equals(temString.substring(temString.length()-2)))
//			   temString=temString.substring(0, temString.length()-2);		
			String[] s=temString.split("&");	
			for (String url : s) {
				if (url == null || "".equals(url))
					continue;		
				map = new HashMap<String, String>();
				map.put("rate", "3000k");
				map.put("framerate", "25");
				map.put("resolution", "1920*1080");
				map.put("samprate", "24000");
				map.put("channel", "2");
				map.put("audiorate", "96");
				map.put("grade", "1");
				map.put("endtime", "2070-12-15 13:12:30");
				map.put("group_id", mac);
				// map.put("uid", vo.getUid());
				String subnameString;
				if (".mpg".equals(url.substring(url.lastIndexOf(".")))){
					subnameString=jsonObject.getString(url.substring(url.lastIndexOf("/")+1));
				} else {
					subnameString=url.substring(url.lastIndexOf("_")+1);
				}
				map.put("uid", mac + "_"+GetCardByName(subnameString));
				map.put("rtmp_repeater", url);
				l.add(map);
			}
		}
		return JSONArray.fromObject(l).toString();
	}
	
	public static String getRtspUrlfromZkdm(String mac,List<String> seats) {
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonObject=new JSONObject();
			String rtspUrl=DeviceRtspUrlsPool.getRtspUrlByMac(mac);
			if (rtspUrl==null||"".equals(rtspUrl)||"rtstperror".equals(rtspUrl)){
				String subString = sendCommandToDevice(mac, "RecordCmd=QueryRtspUrls");
				jsonObject=CosJSONUtil.string2json(subString);
				if (jsonObject==null) return "";
				if ("error".equals(jsonObject.getString("result"))) return "";
				if (jsonObject.getString("info").contains("rtsp://"))
				DeviceRtspUrlsPool.setRtspUrlByMac(mac, subString);
			} else {
				jsonObject=CosJSONUtil.string2json(rtspUrl);
			}

			if (jsonObject==null) return "";
			String temString1= jsonObject.getString("result");
			if (!"ok".equals(temString1)|| temString1==null || "".equals(temString1)){
				return "";
			}
			//JSONObject jsonObject=CosJSONUtil.string2json(subString);
			String infoString= jsonObject.getString("info");
			
			//String temString=CosJSONUtil.string2json(subString).getJSONObject("info").toString();
			//String infoString=temString.substring(0, temString.length()-2);		
			String[] s=infoString.split("&");	
			for (String url : s) {
				if (url == null || "".equals(url))
					continue;		
				String subnameString;
				if (".mpg".equals(url.substring(url.lastIndexOf(".")))){
					subnameString=jsonObject.getString(url.substring(url.lastIndexOf("/")+1));
				} else {
					subnameString=url.substring(url.lastIndexOf("_")+1);
				}
				String cardString=GetCardByName(subnameString);	
				if (seats.contains(cardString)){
					map = new HashMap<String, String>();
					map.put("rate", "3000k");
					map.put("framerate", "25");
					map.put("resolution", "1920*1080");
					map.put("samprate", "24000");
					map.put("channel", "2");
					map.put("audiorate", "96");
					map.put("grade", "1");
					map.put("endtime", "2070-12-15 13:12:30");
					map.put("group_id", mac);
					map.put("uid", mac + "_"+cardString);	
					map.put("rtmp_repeater", url);
					l.add(map);
				}
			}
			
		} catch (Exception e) {
			
		}
		return JSONArray.fromObject(l).toString();
	}
	
	public static String getRtmpFromTran(String mac) {
		//http://192.168.12.46:8080/disrec   code
		//String rtsp= getRtspUrlfromZkdm(mac);
		String rtsp=getRtspUrlfromMult(mac);
		return CodeLiveService.start(codeUrl, rtsp); 
	}
	
	 
	public static String StartPullLiving(String mac,List<String> seats) {
		String ret=LivingPool.getLiving(mac);
		Map<String, String> inVdsMap = new HashMap<String, String>();
		List<Map<String, String>> listRet = new ArrayList<Map<String, String>>();
		//String tranresult=getRtmpFromTran(mac);
		String rtsp= getRtspUrlfromZkdm(mac,seats);
		//String rtsp=getRtspUrlfromMult(mac);
		logger.info(rtsp);
		if ("".equals(rtsp) || rtsp==null || "rtstperror".equals(rtsp)) return "rtstperror";
		String tranresult=CodeLiveService.start(codeUrl, rtsp);
		logger.info(tranresult);
		if ("".equals(tranresult) || tranresult==null) return "FFMPEG_ERROR  NULL";
		JSONObject jsonObject=CosJSONUtil.string2json(tranresult);
		//String temString1= jsonObject.getString("result");
		String response_code_string=jsonObject.getString("response_code_string");
        try {
    		if ("SUCCESS".equals(response_code_string)) {
                JSONArray jsonArray=JSONArray.fromObject(jsonObject.getString("content")); 
                List list = (List)JSONArray.toCollection(jsonArray, TranLive2vo.class);  
                Iterator it = list.iterator(); 
                while(it.hasNext()){
                	TranLive2vo tv = (TranLive2vo)it.next();
                	String uidString = tv.getUid();
                	inVdsMap.put(uidString.substring(uidString.lastIndexOf("_")+1), tv.getRtmp_transcoder());
                }
    			listRet.add(inVdsMap);
    			ret = JSONArray.fromObject(listRet).toString();	
    			LivingPool.addLiving(mac, ret);
    		}
    		if ("FFMPEG_ERROR".equals(response_code_string)) return response_code_string;
    		
		} catch (Exception e) {
			ret="exception";
		} 			
			
        return ret;	
	}
		
	public static String startLiving(String mac, String endTime, String isLive) {
		String ret = "";
		List<Map<String, String>> inVdsList = new ArrayList<Map<String, String>>();
		Map<String, String> inVdsMap = new HashMap<String, String>();
		List<Map<String, String>> listRet = new ArrayList<Map<String, String>>();
		try {
			String strCode;
			// Living
			String s = controlDirection(mac, "RecordCmd=RTMPLiving");
			RetLiveVo retLiveVo = RetLiveVo.ToRetLiveVo(s);
			if (!("ok".equals(retLiveVo.getResult())))
				return null;
			if (!"1".equals(isLive) || ((!(useTransCode())) && (!(usevds())))) {
				inVdsMap = new HashMap<String, String>();
				listRet = new ArrayList<Map<String, String>>();
				for (RetLive2Vo vo : retLiveVo.getInfo()) {
					inVdsMap.put(vo.getCard_info(), vo.getRtmp_repeater());
				}
				listRet.add(inVdsMap);
				ret = JSONArray.fromObject(listRet).toString();
			} else {
				if (useTransCode()) {
					List<Map<String, String>> l = new ArrayList<Map<String, String>>();
					Map<String, String> map = new HashMap<String, String>();
					for (RetLive2Vo vo : retLiveVo.getInfo()) {
						map = new HashMap<String, String>();
						map.put("rate", "3000k");
						map.put("framerate", "25");
						map.put("resolution", "1920*1080");
						map.put("samprate", "24000");
						map.put("channel", "2");
						map.put("audiorate", "96");
						map.put("grade", "1");
						map.put("endtime", endTime);
						map.put("group_id", mac);
						// map.put("uid", vo.getUid());
						map.put("uid", mac + "_" + vo.getCard_info());
						map.put("rtmp_repeater", vo.getRtmp_repeater());
						l.add(map);
					}
					strCode = JSONArray.fromObject(l).toString();
					// transcode
					String code = CodeLiveService.start(codeUrl, strCode);
					RetCodeLiveVo retCodeLiveVo = RetCodeLiveVo
							.ToRetCodeLiveVo(code);
					if (!("0".equals(retCodeLiveVo.getResponse_code())))
						return null;
					if (!usevds()) {
						ret = "";
						listRet = new ArrayList<Map<String, String>>();
						inVdsMap = new HashMap<String, String>();
						for (RetCodeLive2Vo vo : retCodeLiveVo.getContent()) {
							inVdsMap.put(vo.getUid().split("_")[1],
									vo.getRtmp_transcoder());
						}
						listRet.add(inVdsMap);
						ret = JSONArray.fromObject(listRet).toString();
					} else {
						// vds
						InVdsLiveVo inVdsLiveVo = new InVdsLiveVo();
						inVdsLiveVo.setEndtime(endTime);
						inVdsLiveVo.setGroup_id(mac);
						List<RetCodeLive2Vo> content = retCodeLiveVo
								.getContent();
						for (RetCodeLive2Vo vo : content) {
							inVdsMap = new HashMap<String, String>();
							inVdsMap.put("uid", vo.getUid());
							inVdsMap.put("rtmp_transcoder",
									vo.getRtmp_transcoder());
							inVdsList.add(inVdsMap);
						}
						inVdsLiveVo.setRtmps(inVdsList);
						String vdsCode = JSONObject.fromObject(inVdsLiveVo)
								.toString();
						String vds = VdsLiveService.start(VdsUrl, vdsCode);
						RetVdsLiveVo retVdsLiveVo = RetVdsLiveVo
								.ToRetVdsLiveVo(vds);
						if (!("0".equals(retVdsLiveVo.getResponse_code()))) {
							return null;
						}
						listRet = new ArrayList<Map<String, String>>();
						inVdsMap = new HashMap<String, String>();
						for (RetVdsLive3Vo vo : retVdsLiveVo.getContent()
								.get(0).getRtmps()) {
							inVdsMap.put(vo.getUid().split("_")[1],
									vo.getRtmp_distributer());
						}
						listRet.add(inVdsMap);
						ret = JSONArray.fromObject(listRet).toString();
					}

				} else if (usevds()) {
					// vds
					ret = "";
					InVdsLiveVo inVdsLiveVo = new InVdsLiveVo();
					inVdsLiveVo.setGroup_id(mac);
					inVdsLiveVo.setEndtime(endTime);
					for (RetLive2Vo vo : retLiveVo.getInfo()) {
						inVdsMap = new HashMap<String, String>();
						inVdsMap.put("uid", mac + "_" + vo.getCard_info());
						inVdsMap.put("rtmp_transcoder", vo.getRtmp_repeater());
						inVdsList.add(inVdsMap);
					}
					inVdsLiveVo.setRtmps(inVdsList);
					String vdsCode = JSONObject.fromObject(inVdsLiveVo)
							.toString();
					String vds = VdsLiveService.start(VdsUrl, vdsCode);
					RetVdsLiveVo retVdsLiveVo = RetVdsLiveVo
							.ToRetVdsLiveVo(vds);
					if (!("0".equals(retVdsLiveVo.getResponse_code()))) {
						return null;
					}
					listRet = new ArrayList<Map<String, String>>();
					inVdsMap = new HashMap<String, String>();
					for (RetVdsLive3Vo vo : retVdsLiveVo.getContent().get(0)
							.getRtmps()) {
						inVdsMap.put(vo.getUid().split("_")[1],
								vo.getRtmp_distributer());
					}
					listRet.add(inVdsMap);
					ret = JSONArray.fromObject(listRet).toString();
				}
			}
		} catch (Exception e) {
			return null;
		}
		try {
			RecordingService.setVolume(mac, "100");
		} catch (Exception e) {
		}
		return ret;
	}

	public static void stopVds(String mac, List<String> l) {
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map2 = new HashMap<String, String>();

		if (l == null || l.isEmpty()) {
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card0");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card1");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card2");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card3");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card4");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card5");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card6");
			list.add(map2);
		} else {
			for (String s : l) {
				map2 = new HashMap<String, String>();
				map2.put("uid", mac.toString() + "_" + s);
				list.add(map2);
			}
		}
		list.add(map2);
		map.put("uids", list);
		// map.put("group_id", mac);
		String vds = JSONObject.fromObject(map).toString();
		vds = VdsLiveService.stop(VdsUrl, vds);

//		System.out.println("stop vsd ok   :" + vds);
		// JSONObject.toBean(JSONObject.fromObject(vds), RetVo.class);
		// RetVdsLiveVo retVdsLiveVo = RetVdsLiveVo.ToRetVdsLiveVo(vds);
		// if (!"0".equals(retVdsLiveVo.getResponse_code())) {
		// System.out.println("直播失败  Response_code=0");
		// }
	}

	public static void stopCode(String mac, List<String> l) {
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map2 = new HashMap<String, String>();
		if (l == null || l.isEmpty()) {
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card0");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card1");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card2");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card3");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card4");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card5");
			list.add(map2);
			map2 = new HashMap<String, String>();
			map2.put("uid", mac.toString() + "_" + "card6");
			list.add(map2);
		} else {
			for (String s : l) {
				map2 = new HashMap<String, String>();
				map2.put("uid", mac.toString() + "_" + s);
				list.add(map2);
			}
		}
		map.put("uids", list);
		// map.put("group_id", mac);
		String vds = JSONObject.fromObject(map).toString();
		vds = CodeLiveService.stop(codeUrl, vds);
//		System.out.println("stop code ok   :" + vds);
		// RetStopVo vo = (RetStopVo) JSONObject.toBean(
		// JSONObject.fromObject(vds), RetStopVo.class);
		// System.out.println("------------------------------------"
		// + vo.getResponse_code());
		// if (!"0".equals(vo.getResponse_code())) {
		// System.out.println("stop code ok   :" + vo.getResponse_code());
		// }
	}

	public static void stopAndSet(String mac) {
		// String url = Url.getServiceUrl(mac, "recording");
		// // 分发
		// // vds = VdsLiveService.start(VdsUrl, mac);
		// 转码
		// CodeLiveVo vo = new CodeLiveVo();
		// List<Map<String, Object>> list = new ArrayList<Map<String,
		// Object>>();
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("rtmp_repeater", livingPool.getInstance().getMessage(mac));
		// map.put("uid", mac);
		// list.add(map);
		// vo.setTranscodingurl(list);
		// List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		// l.add(vo.toMap());
		// String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l)
		// .toString());
		// 直播
		// String s = LivingService.controlDirection(url + "/cmd",
		// "BroadCastCmd=StopBroadCast");
		// RetLivingVo retLivingVo = RetLivingVo.ToRetLivingVo(s);
		// livingPool.getInstance().removeMessageInbound(mac);
//		stopVds(mac, null);    limin 20150707
//		stopCode(mac, null);   limin 20150707
		
		
	}

	// public static VdsLiveVo startCode(String mac,String str) {
	// // 转码
	// CodeLiveVo vo = new CodeLiveVo();
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("rtmp_repeater", str);
	// //map.put("rtmp_repeater", "rtmp://192.168.12.103/zonekey/test");
	// map.put("uid", mac);
	// list.add(map);
	// vo.setTranscodingurl(list);
	// vo.setGrade("1");
	// vo.setGroup_id("lzg");
	// vo.setEndtime("2014-12-25 13:12:30");
	// List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
	// l.add(vo.toMap());
	// // 转码
	// String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l)
	// .toString());
	// RetCodeLiveVo retCodeLiveVo = RetCodeLiveVo.ToRetCodeLiveVo(code);
	//
	// VdsLiveVo vdsLiveVo = new VdsLiveVo();
	// vdsLiveVo.setEndtime("2014-12-25 13:12:30");
	// vdsLiveVo.setGroup_id("lzg");
	// vdsLiveVo.setRtmps(retCodeLiveVo.getContent());
	//
	// if (!"0".equals(retCodeLiveVo.getResponse_code())) {
	// System.out.println("直播失败  Response_code=0");
	// return null;
	// }
	// return vdsLiveVo;
	// }
	// public static String startAndSet(String mac,String endtime) {
	// String url=Url.getServiceUrl(mac, "recording");
	// String vds = "";
	// try {
	// // 直播
	// String s =
	// LivingService.controlDirection(url+"/cmd","RecordCmd=RTMPLiving");
	// RetLivingVo retLivingVo = RetLivingVo.ToRetLivingVo(s);
	// if (!"ok".equals(retLivingVo.getResult())){
	// return "直播失败";
	// }
	// boolean flag=true;
	// while(flag){
	// String status = GetInfoService.getLiveStatus(mac);
	// if("LivingStart".equals(status)){
	// flag=false;
	//
	// }else{
	// Thread.sleep(60000);
	// }
	//
	// }
	//
	// // 转码
	// CodeLiveVo vo = new CodeLiveVo();
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("rtmp_repeater", retLivingVo.getInfo());
	// //map.put("rtmp_repeater", "rtmp://192.168.12.103/zonekey/test");
	// map.put("uid", mac);
	// list.add(map);
	// vo.setTranscodingurl(list);
	// vo.setGrade("1");
	// vo.setGroup_id(mac);
	// vo.setEndtime(endtime);
	// List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
	// l.add(vo.toMap());
	// // 转码
	// String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l)
	// .toString());
	// RetCodeLiveVo retCodeLiveVo = RetCodeLiveVo.ToRetCodeLiveVo(code);
	//
	// VdsLiveVo vdsLiveVo = new VdsLiveVo();
	// vdsLiveVo.setEndtime(endtime);
	// vdsLiveVo.setGroup_id(mac);
	// vdsLiveVo.setRtmps(retCodeLiveVo.getContent());
	//
	// if (!"0".equals(retCodeLiveVo.getResponse_code())) {
	// System.out.println("直播失败  Response_code=0");
	// return "直播失败";
	// }
	// // 分发
	// vds = VdsLiveService.start(VdsUrl, JSONObject.fromObject(vdsLiveVo)
	// .toString());
	// RetVdsLiveVo retVdsLiveVo = RetVdsLiveVo.ToRetVdsLiveVo(vds);
	// if (!"0".equals(retVdsLiveVo.getResponse_code())) {
	// System.out.println("直播失败  Response_code=0");
	// return "直播失败";
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// // 直播失败
	// return "直播失败";
	// }
	// livingPool.getInstance().addMessage(
	// RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent().get(0).getRtmps()
	// .get(0).getRtmp_distributer(), mac);
	// if (livingPool.getInstance().getMessage(mac) == null
	// || "".equals(livingPool.getInstance().getMessage(mac)))
	// // 直播失败
	// return "直播失败";
	// System.out.println("直播成功");
	// livingPool.getInstance().forch();
	// System.out.println("--------------------------------------");
	// System.out.println("直播 流 "+RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent()
	// .get(0).getRtmps().get(0).getRtmp_distributer());
	// return
	// RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent().get(0).getRtmps().get(0).getRtmp_distributer();
	// }

	public static RetVdsLiveVo getVds(String groupId) {
		String str = VdsLiveService.getVds(groupId);
		RetVdsLiveVo retVdsLiveVo = RetVdsLiveVo.ToRetVdsLiveVo(str);
		return retVdsLiveVo;
	}

	// public static RetVdsLiveVo startVds(String mac,String endTime) {
	// String url=Url.getServiceUrl(mac, "recording");
	// String vds = "";
	// RetVdsLiveVo retVdsLiveVo;
	// try {
	// // 直播
	// String s =
	// LivingService.controlDirection(url+"/cmd","RecordCmd=RTMPLiving");
	// RetLivingVo retLivingVo = RetLivingVo.ToRetLivingVo(s);
	// if (!"ok".equals(retLivingVo.getResult()))
	// return null;
	// // 转码
	// CodeLiveVo vo = new CodeLiveVo();
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("rtmp_repeater", retLivingVo.getInfo());
	// map.put("uid", mac);
	// list.add(map);
	// vo.setTranscodingurl(list);
	// vo.setGrade("1");
	// vo.setGroup_id("lzg");
	// vo.setEndtime("2099-12-31 13:12:30");
	// List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
	// l.add(vo.toMap());
	// // 转码
	// String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l)
	// .toString());
	// RetCodeLiveVo retCodeLiveVo = RetCodeLiveVo.ToRetCodeLiveVo(code);
	//
	// VdsLiveVo vdsLiveVo = new VdsLiveVo();
	// vdsLiveVo.setEndtime(endTime);
	// vdsLiveVo.setGroup_id("lzg");
	// vdsLiveVo.setRtmps(retCodeLiveVo.getContent());
	//
	// if (!"0".equals(retCodeLiveVo.getResponse_code())) {
	// return null;
	// }
	// // 分发
	// vds = VdsLiveService.start(VdsUrl, JSONObject.fromObject(vdsLiveVo)
	// .toString());
	// retVdsLiveVo = RetVdsLiveVo.ToRetVdsLiveVo(vds);
	// if (!"0".equals(retVdsLiveVo.getResponse_code())) {
	// return null;
	// }
	// System.out.println("--------------------------------------");
	// System.out.println("直播流 is "+
	// RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent()
	// .get(0).getRtmps().get(0).getRtmp_distributer());
	//
	// } catch (Exception e) {
	// // 直播失败
	// return null;
	// }
	// return retVdsLiveVo;
	// }

	/**
	 * 
	 * @param url
	 * @param direction
	 * @param param
	 */
	public static String controlDirection(String mac, String param) {
		String url = getServiceUrl(mac, "recording");
		if ("".equals(url)) {
			return "";
		}
		String ret =sendGet(url + "/cmd", param);
		new DeviceLogs().log(mac+"。。。"+url + "/cmd?"+ param+" ... "+ret);
		new DeviceLogs().log("");
		return ret;
	}

	public static void main(String[] args) {
//		stopLiving("000000000003");
		// stopVds("000000000003", null);
		// stopCode("00E04C680001", null);
		// startAndSet("00:24:31:19:BD:E4".replaceAll(":", "_"));
		// stopVds("macxx");
		// stopCode("00:24:31:19:BD:E4".replaceAll(":", "_"));
		// stopAndSet("mac1");
	}

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			if (param != null && !"".equals(param))
				urlNameString += "?" + param;
			URL realUrl = new URL(urlNameString);

			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();

			Map<String, List<String>> map = connection.getHeaderFields();
			for (@SuppressWarnings("unused")
			String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
			}

			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				// System.out.println(line);
				result += line;
			}
		} catch (Exception e) {
			// System.out.println("发送GET请求出现异常！" + e);
			// e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;

	}

	public static String getVdsLiveStatus(String mac) {
		// get vds live status vds live status is real live status
		String vdsCode;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("group_id", mac);
			vdsCode = JSONObject.fromObject(map).toString();
			vdsCode = sendGet(VdsUrl, vdsCode);
			RetVdsLiveStatusVo vo = RetVdsLiveStatusVo.ToRetVdsLiveVo(vdsCode);
			if (vo != null && !"".equals(vo.getResponse_code())
					&& "0".equals(vo.getResponse_code())) {
				vdsCode = "1";
			} else {
				vdsCode = "0";
			}
		} catch (Exception e) {
			return "0";
		}
		return vdsCode;
	}
}
