package com.mediaServer.vds.service;

import com.util.HttpExec;


public class VdsLiveService {
	private static String VdsUrl = "http://192.168.12.117:50201/vds/web";
	
	public static String start(String  url,String str) {
		return HttpExec.doPostWithJSON(url, str);
	}

	public static String stop(String  url,String str) {
		return HttpExec.doPutWithJSON(url, str);
	}
	
	
	public static String getVds(String str) {
		
		return HttpExec.sendGet(VdsUrl, "groupid="+str);
	}
}
