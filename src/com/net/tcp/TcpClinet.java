package com.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.util.CosJSONUtil;

public class TcpClinet {
	private String serverip;
	private int port;
	private String cmdString;
	public String getCmdString() {
		return cmdString;
	}
	public void setCmdString(String cmdString) {
		this.cmdString = cmdString;
	}
	public TcpClinet(String serverip, int port,String cmdurl) {
		this.serverip = serverip;
		this.port = port;
		this.cmdString=cmdurl;
	}
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	private boolean isonliving() throws UnknownHostException, IOException {
		String host = this.getServerip();
		int timeOut = 3000;
		return InetAddress.getByName(host).isReachable(timeOut);
	}
	
	public Map<String,String> QuerRstpUrl() throws UnknownHostException, IOException {	
		Map<String,String> result;
		Socket s = new Socket(this.getServerip(), this.getPort());
		InputStream ips = s.getInputStream();
		OutputStream ops = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(ops);
		BufferedReader brNet = new BufferedReader(new InputStreamReader(ips));
		dos.writeBytes(this.getCmdString());
		result=CosJSONUtil.CharSplitToList(3, "&", brNet.readLine());
		dos.close();
		brNet.close();
		s.close();		
		return result;
	}
	
	public String QueryRecordMode() throws UnknownHostException, IOException {
		String Tem;
		Socket s = new Socket(this.getServerip(), this.getPort());
		InputStream ips = s.getInputStream();
		OutputStream ops = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(ops);
		BufferedReader brNet = new BufferedReader(new InputStreamReader(ips));
		dos.writeBytes(this.getCmdString());
		Tem=brNet.readLine();
		//System.out.println(Tem);
		//recordModeMap=CosJSONUtil.CharSplitToList(3, "&", brNet.readLine());
		Tem=Tem.substring(3);
		String[] Sarry=Tem.split("&");
		Map<String, String> recordModeMap = new HashMap<String, String>();
		for (String url : Sarry) {
			if (url == null || "".equals(url))
				continue;  //recordMode=Resource
//			System.out.println(url.substring(0,url.lastIndexOf("=")));
//			System.out.println(url.substring(url.lastIndexOf("=")+1));
			if (url.substring(url.lastIndexOf("=")).equals(""))
				recordModeMap.put(url.substring(0, url.lastIndexOf("=")), "");
			else
			   recordModeMap.put(url.substring(0, url.lastIndexOf("=")), url.substring(url.lastIndexOf("=")+1));
		}
		
//		System.out.println(recordModeMap.get("recordMode"));
		dos.close();
		brNet.close();
		s.close();		
		if (!recordModeMap.isEmpty())
			return recordModeMap.get("recordMode");
		else return "";		
	}

}
