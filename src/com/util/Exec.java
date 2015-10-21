package com.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


/**
 * @author zfc
 * 
 */
public class Exec {

	private static Exec exec= new Exec();

	private Exec() {}

	public synchronized static Exec getInstance() {
		return exec;
	}

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + param;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			realUrl = null;
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			HttpURLConnection httpUrlConnection = (HttpURLConnection) connection;
			httpUrlConnection.connect();
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestProperty("Content-type",
					"application/x-java-serialized-object");
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.connect();

			connection.connect();
			Map<String, List<String>> map = connection.getHeaderFields();
			for (@SuppressWarnings("unused")
			String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
			}
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connection = null;
			String line = "";
			while ((line = in.readLine()) != null) {
				result += line;
//				System.out.println(result);
			}
		} catch (Exception e) {
//			System.out.println("sendGet : " + e);
			//e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				in = null;
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
	}
	
}
