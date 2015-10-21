package com.mediaServer.transcode.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import com.util.HttpExec;


public class CodeLiveService implements Serializable {
	private static final long serialVersionUID = 1L;
//	private static String url="http://192.168.12.103:8899/transcode/transinfo";
//	private static String url=Url.getMediaCodeIpUrl() +"/transcode/transinfo";

	public static String start(String  url,String str) {
//		return HttpExecute.doPostWithJSON(url, str);
//		return HttpExec.doPostWithJSON(url, str);
		return sendPost(url, str);
	}

	public static String stop(String  url,String str) {
		return HttpExec.doPutWithJSON(url, str);
//		return sendPost(url, str);
	}


	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		}
		return result;
	}
}
