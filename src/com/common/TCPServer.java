package com.common;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class TCPServer {
	public static final int PORT = 1234;// 监听的端口号
	private static Map<String,Socket> map = new HashMap<String,Socket>();
	protected Socket s = null;
	//用户 
	protected String ip = null;
	/**
	 * @throws Exception
	 */
	public TCPServer() throws Exception {
		this(PORT);
	}

	/**
	 * @param localport
	 * @throws Exception
	 */
	public TCPServer(int localport) throws Exception {
		//构建服务器对象
		ServerSocket ss = new ServerSocket(localport);

		//循环监听
		while(true){
			
			//上线用户
			s = ss.accept();
			
			//获取 Socket IP
			ip = s.getInetAddress().getHostAddress();
			//构建 发送信息线程
			M2MSend send = new M2MSend(s);
			send.start();
		
		}
	}

	public static Map<String, Socket> getMap() {
		return map;
	}

	public static void setMap(Map<String, Socket> map) {
		TCPServer.map = map;
	}


}