package com.mediaServer.middle.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.mediaServer.middle.service.vo.RetMiddle2Vo;
import com.mediaServer.middle.service.vo.RetMiddleVo;

public class MiddleService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static boolean getLiveStatusByMac(String mac) {
		try {
		String str =getLiveStatus("mac");
		RetMiddleVo vo=RetMiddleVo.ToRetMiddleVo(str);
		if("SUCCESS".equals(vo.getResponse_string())){
			if("SUCCESS".equals(vo.getResponse_string())){
				List<RetMiddle2Vo> list=vo.getContent();
				for(int i=0; i<list.size(); i++)    {
					if(list.get(i)!=null&&!"".equals(list.get(i)))
						return true;
				} 
			}
			return false;
		}else{
			return false;
		}
		} catch (Exception e) {
			return false;
		}
	}
	public static String getLiveStatus(String mac) {
		if (mac == null || "".equals(mac))
			return "";

		 String codeUrl = "http://192.168.13.117:50001"+
		 "/repeater/streaminfo";
//		String codeUrl = Url.getServerUrl("middle") + "/repeater/streaminfo";
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "push");
		map.put("group_id", mac);
		String info = JSONObject.fromObject(map).toString();
		return sendGet(codeUrl, info);
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
				//e2.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String str = getLiveStatus("00e04c73006d");
		//System.out.println(str);
		RetMiddleVo v=RetMiddleVo.ToRetMiddleVo(str);
		v.toString();
		System.exit(0);
	}
}
