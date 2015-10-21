//package com.service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.mediaServer.transcode.service.CodeLiveService;
//import com.mediaServer.transcode.vo.CodeLiveVo;
//import com.util.CosJSONUtil;
//import com.util.HttpExec;
//import com.util.Url;
//
///**
// * @author zfc
// * 
// *         http://192.168.12.47/:10006/recording/cmd?RecordCmd=RTMPLiving
// */
//public class RTMPLivingService {
//
//	//private static String url = "http://192.168.12.4:10006/recording/cmd";
//	public static final String RTMPLIVING = "RTMPLiving";// 录像开始
//
//	// public static final String PAUSERECORD = "PauseRecord";//录像暂时
//	// public static final String STOPRECORD = "StopRecord";//录像结束
//	// public static final String RESUUMERECORD = "ResumeRecord";//录像恢复
//
//	public static String livingStart(String mac) {
//		String url=Url.getServiceUrl(mac, "recording");
//		return controlDirection(url+"/cmd", "RecordCmd=" + RTMPLIVING);
//	}
//
//	public static String controlDirection(String url ,String param) {
//		return Url.sendGet(url, param);
//
//	}
//
//	
//	
//	
//	
//	
//	
//	
//	public static void main(String[] args) {
//		// System.out.println(livingStart());
//		// System.out.println();
//		// System.out.println();
//		//livingStart();
//		//livingAll();
//	}
//
//	public static String livingAll(String mac) {
//		// System.out.println();
//		livingStart(mac);
//
//		// System.out.println();
//		// System.out.println();
//
//		String codeUrl = "http://192.168.12.211:50501/transcode/transinfo";
//		CodeLiveVo vo = new CodeLiveVo();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("rtmp_repeater", "rtmp://192.168.12.103:1931/zonekey/test");
//		map.put("uid", "xx-xxxx-xxxx-xxx");
//		list.add(map);
//		vo.setRate("3000k");
//		vo.setFramerate("25");
//		vo.setResolution("1920*1080");
//		vo.setSamprate("24000");
//		vo.setTranscodingurl(list);
//		vo.setRtmp_repeater("rtmp://192.168.12.103:1931/zonekey/test");
//		vo.setUid("xx-xxxx-xxxx-xxx");
//		vo.setChannel("2");
//		vo.setAudiorate("96");
//		vo.setGrade("1");
//		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
//		l.add(vo.toMap());
//		String str = CosJSONUtil.json2Map(
//				CosJSONUtil.string2json(CodeLiveService.start(codeUrl,
//						CodeLiveVo.toJson(l).toString()))).get("content");
////		System.out.println("--------------------------------------");
////		System.out.println(str);
//		String url = "http://192.168.12.117:50201/vds/web";
//		HttpExec.doPostWithJSON(url, str);
//		return "";
//	}
//}
