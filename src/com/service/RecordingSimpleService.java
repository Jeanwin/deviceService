//package com.service;
//
//import com.util.Url;
//
///**
// * @author zfc
// * 
// * 
// *         设置总路径 RecordCmd=SetFileProperty&FileFormat=mp4&TotalFilePath=D:/zfc
// *         设置子路径 RecordCmd=SetFileFolder&SubFileFolder=yyyy-mm-dd 设置录制模式
// *         RecordCmd=SetRecordMode&RecordMode=All [All/Resource/Movie] 录像开始
// *         RecordCmd=StartRecord 录像暂停 RecordCmd=PauseRecord 录像停止
// *         RecordCmd=StopRecord 恢复录像 recording/cmd?RecordCmd=ResumeRecord
// * 
// *         设置每一路视频的声音：RecordCmd=SetVolume&CardPort={0}&Volume={1}
// * 
// *         RecordCmd=SetVolume&CardPort={0}&Volume={1}
// */
//public class RecordingSimpleService {
//
//	// private static String url = "http://192.168.12.47:10006/recording/cmd";
//	//private static String url = "http://192.168.12.4:10006/recording/cmd";
//	// private static String url = "http://192.168.12.47:10007/recording/cmd";
//	// private static String url = Url.getRecordingUrl();
//	public static final String STARTRECORD = "StartRecord";// 录像开始
//	public static final String PAUSERECORD = "PauseRecord";// 录像暂时
//	public static final String STOPRECORD = "StopRecord";// 录像结束
//	public static final String RESUUMERECORD = "ResumeRecord";// 录像恢复
//
//
//
//	public static void startRecording(String mac,String stuts) {
//		String url=Url.getServiceUrl(mac, "recording");
//		controlDirection(url+"/cmd","RecordCmd="+stuts);
//	}
//
//	//////////////////////////////////////////////////////////////////
//	public static void SetFileProperty(String mac,String TotalFilePath) {
//		String string = "RecordCmd=SetFileProperty&FileFormat=mp4&TotalFilePath="
//				+ TotalFilePath;
//		String url=Url.getServiceUrl(mac, "recording");
//		controlDirection(url+"/cmd",string);
//	}
//
//	public static void SetFileFolder(String mac,String TotalFilePath) {
//		String url=Url.getServiceUrl(mac, "recording");
//		String string = "RecordCmd=SetFileFolder&SubFileFolder="
//				+ TotalFilePath;
//		controlDirection(url+"/cmd",string);
//	}
//
//	public static void SetRecordMode(String mac,String RecordMode) {
//		String url=Url.getServiceUrl(mac, "recording");
//		String string = "RecordCmd=SetRecordMode&RecordMode=" + RecordMode;
//		controlDirection(url+"/cmd",string);
//	}
//	
//	public static void recording(String mac,String stuts) {
//		String url=Url.getServiceUrl(mac, "recording");
//		// System.out.println(stuts);
//		System.out.println(GetInfoService.getRecordStatus(mac));
//		String str = GetInfoService.getRecordStatus(mac);
//		if ("Recording".equals(str)) {
//			if (PAUSERECORD.equals(stuts) || STOPRECORD.equals(stuts)) {
//				controlDirection(url+"/cmd",stuts);
//			} else {
//				return;
//			}
//		} else if ("RecordPaused".equals(str)) {
//			if (RESUUMERECORD.equals(stuts) || STOPRECORD.equals(stuts)) {
//				controlDirection(url+"/cmd",stuts);
//			} else {
//				return;
//			}
//		} else {
//			controlDirection(url+"/cmd",stuts);
//		}
//	}
//
//	public static void setVolume(String mac,String volume) {
//		String url=Url.getServiceUrl(mac, "recording");
//		String string = "RecordCmd=SetVolume&CardPort={0}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//		string = "RecordCmd=SetVolume&CardPort={3000}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//		string = "RecordCmd=SetVolume&CardPort={3001}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//		string = "RecordCmd=SetVolume&CardPort={3002}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//		string = "RecordCmd=SetVolume&CardPort={3003}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//		string = "RecordCmd=SetVolume&CardPort={3004}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//		string = "RecordCmd=SetVolume&CardPort={3005}&Volume=" + volume;
//		controlDirection(url+"/cmd",string);
//	}
//
//	/**
//	 * 
//	 * @param url
//	 * @param direction
//	 * @param param
//	 */
//	public static void controlDirection(String url,String param) {
//		// System.out.println(url +"?"+string);
//		// ConsoleOperation.sendGet(url ,"?"+string);
//		Url.sendGet(url, param);
//
//	}
//
//	public static String execute(String mac,String para) {
//		String url=Url.getServiceUrl(mac, "recording");
//		if (para == null || "".equals(para))
//			return null;
//		return Url.sendGet(url+"/cmd" ,para);
//	}
//
//	public static void main(String[] args) {
//
//		//startRecording("StartRecord");
//		
//		
//		// new ClassRoomDeviceVo();
//		// SetFileProperty("D:/zfc");
//		// SetFileFolder("yyyy-mm-dd");
//		// SetRecordMode("All");
//		//recording("StartRecord");
//		// recording("StopRecord");
//		// JSONObject jsonObject = JSONObject.fromObject("{'aa':'xxx'}");
//		// System.out.println(jsonObject);
//
//	}
//}
