package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.net.tcp.TcpClinet;
import com.util.CosJSONUtil;

public class test3 {
	
	public static void main(String[] args) throws InterruptedException, Exception {
		
//		CosJSONUtil.parseJSON2Map(RecordingService.execute("RecordCmd=QueryRecordTime")).get("info");
//		CosJSONUtil.parseJSON2Map(RecordingService.execute("RecordCmd=QueryDiskStatus")).get("info");
		String host = "192.168.12.57";
		int timeOut = 3000;
		boolean status = InetAddress.getByName(host).isReachable(timeOut);
		System.out.println(status);
//		TcpClinet tcpClinet=new TcpClinet("192.168.12.57", 1230,"CsnCmd=StartInteractive");
//		//TcpClinet tcpClinet=new TcpClinet("192.168.13.200", 1230,"RecordCmd=QueryRAllInfo");
//		System.out.println(tcpClinet.QuerRstpUrl());
		
		Socket s = new Socket("192.168.12.57", 1230);
		InputStream ips = s.getInputStream();
		OutputStream ops = s.getOutputStream();
		DataOutputStream dos = new DataOutputStream(ops);
		BufferedReader brNet = new BufferedReader(new InputStreamReader(ips));
		dos.writeBytes("CsnCmd=StartInteractive");
		System.out.println(brNet.readLine().toString());
		//result=CosJSONUtil.CharSplitToList(3, "&", brNet.readLine());
		dos.close();
		brNet.close();
		s.close();		
	}
}
