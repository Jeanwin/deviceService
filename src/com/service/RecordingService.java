package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.net.tcp.TcpClinet;
import com.util.CosJSONUtil;
import com.util.DeviceRtspUrlsPool;
import com.util.DeviceTran;
import com.util.Url;
import com.web.PerConsoleController;

/**
 * @author zfc
 * 
 * 
 *         设置总路径 RecordCmd=SetFileProperty&FileFormat=mp4&TotalFilePath=D:/zfc
 *         设置子路径 RecordCmd=SetFileFolder&SubFileFolder=yyyy-mm-dd 设置录制模式
 *         RecordCmd=SetRecordMode&RecordMode=All [All/Resource/Movie] 录像开始
 *         RecordCmd=StartRecord 录像暂停 RecordCmd=PauseRecord 录像停止
 *         RecordCmd=StopRecord 恢复录像 recording/cmd?RecordCmd=ResumeRecord
 * 
 *         设置每一路视频的声音：RecordCmd=SetVolume&CardPort={0}&Volume={1}
 * 
 *         RecordCmd=SetVolume&CardPort={0}&Volume={1}
 */
@Service
public class RecordingService {
	private static Logger logger = Logger.getLogger(PerConsoleController.class);

	// private static String url = "http://192.168.12.47:10006/recording/cmd";
	// private static String url = "http://192.168.12.4:10006/recording/cmd";
	// private static String url = "http://192.168.12.47:10007/recording/cmd";
	// private static String url = Url.getRecordingUrl();
	public static final String STARTRECORD = "StartRecord";// 录像开始
	public static final String PAUSERECORD = "PauseRecord";// 录像暂时
	public static final String STOPRECORD = "StopRecord";// 录像结束
	public static final String RESUUMERECORD = "ResumeRecord";// 录像恢复

	public static void SetFileProperty(String mac, String TotalFilePath) {
		String string = "RecordCmd=SetFileProperty&FileFormat=mp4&TotalFilePath="
				+ TotalFilePath;
		String url = Url.getServiceUrl(mac, "recording");
		controlDirection(url + "/cmd", string);
	}

	public String SetFileFolder(String mac, String TotalFilePath) {

		String string = "RecordCmd=SetFileFolder&SubFileFolder="
				+ TotalFilePath;
		String url = Url.getServiceUrl(mac, "recording");

		return controlDirection(url + "/cmd", string);
	}

	public static void SetRecordMode(String mac, String RecordMode) {
		String url = Url.getServiceUrl(mac, "recording");
		String string = "RecordCmd=SetRecordMode&RecordMode=" + RecordMode;
		controlDirection(url + "/cmd", string);
	}

	public static String SendCmdtoRecord(String mac, String para) {
		String url = Url.getServiceUrl(mac, "recording");
		return controlDirection(url + "/cmd", para);
	}

	public static void recording(String mac, String para) {
		String url = Url.getServiceUrl(mac, "recording");
		String stuts = GetInfoService.getRecordStatus(mac);
		
		if ("Recording".equals(stuts)) {
			if (PAUSERECORD.equals(para) || STOPRECORD.equals(para)) {
				controlDirection(url + "/cmd", "RecordCmd=" + para);
			} else {
				controlDirection(url + "/cmd", "RecordCmd=PauseRecord");
			}
		} else if ("RecordPaused".equals(stuts)) {
			if (RESUUMERECORD.equals(para) || STOPRECORD.equals(para)) {
				controlDirection(url + "/cmd", "RecordCmd=" + para);
			} else {
				controlDirection(url + "/cmd", "RecordCmd=" + RESUUMERECORD);
			}
		} else {
			if (STOPRECORD.equals(para))
				return;
			controlDirection(url + "/cmd", "RecordCmd=" + STARTRECORD);
		}
	}

	public static String setVolume(String mac, String volume) {
		String url = Url.getServiceUrl(mac, "recording");
		String string = "RecordCmd=SetVolume&CardPort=3000&Volume=" + volume;
		return controlDirection(url + "/cmd", string);
		// 20150109 zfc
		// don't delete is extenting...
		// string="RecordCmd=SetVolume&CardPort=3001&Volume="+volume;
		// controlDirection(url+"/cmd",string);
		// string="RecordCmd=SetVolume&CardPort=3002&Volume="+volume;
		// controlDirection(url+"/cmd",string);
		// string="RecordCmd=SetVolume&CardPort=3003&Volume="+volume;
		// controlDirection(url+"/cmd",string);
		// string="RecordCmd=SetVolume&CardPort=3004&Volume="+volume;
		// controlDirection(url+"/cmd",string);
		// string="RecordCmd=SetVolume&CardPort=3005&Volume="+volume;
		// controlDirection(url+"/cmd",string);
	}

	/**
	 * 
	 * @param url
	 * @param direction
	 * @param param
	 */
	public static String controlDirection(String url, String param) {
		return Url.sendGet(url, param);

	}

	public static String execute(String mac, String para) {
		// String url = Url.getServiceUrl(mac, "recording");
		// if (para == null || "".equals(para))
		// return "";
		// String ret = "";
		// if (url == null || "".equals(url)) {
		// return "";
		// } else {
		// try {
		// ret = sendGet(url + "/cmd", para);
		// } catch (Exception e) {
		// return "";
		// }
		// return ret;
		// }
		if (para == null || "".equals(para))
			return "";
		String DeviceType = "&DeviceType=other";
		String url = Url.getServiceUrl(mac, "recording");
		DeviceType = "&DeviceType=recording";
		if (url == null || "".equals(url)) {
			url = Url.getServiceUrl(mac, "centralcontroller");
			DeviceType = "&DeviceType=centralcontroller";
		}
		if (url == null || "".equals(url))
			return "";
		JSONObject jObject;
		String ret = "";
		try {
			ret = sendGet(url + "/cmd", para);
		} catch (Exception e) {
			return "";
		}
		return ret;
	}

	public static String QueryRAllInfo(String mac, String para) {
		if (para == null || "".equals(para))
			return "";
		String DeviceType = "&DeviceType=other";
		String url = Url.getServiceUrl(mac, "recording");
		DeviceType = "&DeviceType=recording";
		if (url == null || "".equals(url)) {
			url = Url.getServiceUrl(mac, "centralcontroller");
			DeviceType = "&DeviceType=centralcontroller";
		}
		if (url == null || "".equals(url))
			return "";
		JSONObject jObject;
		String ret = "";
		try {
			ret = sendGet(url + "/cmd", para);
			jObject = CosJSONUtil.string2json(ret);
			if ("ok".equals(jObject.getString("result"))) {
				String temString = jObject.getString("info");
				temString = temString + DeviceType;
				// temString=
				// temString.substring(0,temString.length()-2)+DeviceType;
				jObject.put("info", temString);
			}
		} catch (Exception e) {
			return "";
		}
		return jObject.toString();
	}

	public static void main(String[] args) {

		// new ClassRoomDeviceVo();
		// SetFileProperty("D:/zfc");
		// SetFileFolder("yyyy-mm-dd");
		// SetRecordMode("All");
		// recording("StartRecord");
		// recording("StopRecord");
		// JSONObject jsonObject = JSONObject.fromObject("{'aa':'xxx'}");
		// System.out.println(jsonObject);

	}

	/**
	 * 设置录像时间
	 * 
	 * @param mac
	 *            mac地址
	 * @param time
	 *            设置时间
	 */
	public static String setDeviceTime(String mac, String endtimie) {
		String url = Url.getServiceUrl(mac, "recording");
		String string = "RecordCmd=UpdateClassSchedule&endtime=" + endtimie;
		return controlDirection(url + "/cmd", string);
	}

	public static String queryAllRecordMode(String mac, String deviceType) {
		String resulString = "";
		try {
			String url = Url.getServiceUrl(mac, deviceType);
			if (null != url) {
				String resutlString = Url.sendGet(url + "/cmd","RecordCmd=QueryRAllInfo");
				JSONObject json = CosJSONUtil.string2json(resutlString);
				if ("error".equals(json.getString("result").toString())&& (json == null))
					return "error";
				String temString = json.getString("info").toString();
				if ("ok".equals(json.getString("result").toString())) {
					// System.out.println(temString);
					String[] Sarry = temString.split("&");
					for (String s : Sarry) {
						if (s == null || "".equals(s))
							continue; // recordMode=Resource
						if (s.contains("recordMode")) {
							resulString = s;
							return s;
						}
					}
				}
			}

		} catch (Exception e) {
			return "error";
		}
		return resulString;
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
			for (String key : map.keySet()) {
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
				// e2.printStackTrace();
			}
		}

		return result;

	}

	/**
	 * 根据设备的mac地址和设备类型从代理获取rtsp直播地址
	 * 
	 * @param mac
	 * @param deviceType
	 * @return
	 */
	public static JSONObject GetRtspUrlFromZkdmByMac(String mac,
			String deviceType) {

		// String subString = controlDirection(mac, "RecordCmd=QueryRtspUrls");
		JSONObject jsonObject = new JSONObject();
		JSONObject resultJsonObject = new JSONObject();
		try {
			String rtspUrl = DeviceRtspUrlsPool.getRtspUrlByMac(mac);
			logger.info("rtspUrl..缓存地址.." + rtspUrl);
			if (rtspUrl == null || "".equals(rtspUrl)
					|| "rtstperror".equals(rtspUrl)) {
				String url = Url.getServiceUrl(mac, deviceType);
				logger.info("代理设备服务地址::::" + ":" + url);
				String resutlString = Url.sendGet(url + "/cmd",
						"RecordCmd=QueryRtspUrls");
				logger.info("向代理获取rtsp地址===>" + resutlString);
				jsonObject = CosJSONUtil.string2json(resutlString);			
				if ("error".equals(jsonObject.getString("result")))
					return jsonObject;
				if (jsonObject.getString("info").contains("rtsp://"))
				DeviceRtspUrlsPool.setRtspUrlByMac(mac, resutlString);
			} else {
				jsonObject = CosJSONUtil.string2json(rtspUrl);
			}
			String infoString = jsonObject.getString("info");
			String[] paths = infoString.split("&");
			for (String string : paths) {
				if (".mpg".equals(string.substring(string.lastIndexOf(".")))) {
					String subnameString = string.substring(string
							.lastIndexOf("/") + 1);
					String tem = jsonObject.getString(subnameString);
					resultJsonObject.put(DeviceTran.TranName(tem), string);
				} else {
					resultJsonObject.put(
							string.substring(string.lastIndexOf("_") + 1),
							string);
				}
			}
			return resultJsonObject;

		} catch (Exception e) {
			logger.info(e.getMessage());
			jsonObject.put("info", "rtsp error");
			jsonObject.put("result", "error");
			return null; 
		}
	}

	public static Map<String, String> sendToRecord(String ip, String RecordCmd)
			throws UnknownHostException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		boolean status = InetAddress.getByName(ip).isReachable(3000);
		if (status) {
			TcpClinet tcpClinet = new TcpClinet(ip, 1230, "RecordCmd="
					+ RecordCmd);
			map = tcpClinet.QuerRstpUrl();
		}
		return map;
	}
}
