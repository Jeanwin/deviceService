package com.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Date;
import java.util.Map;

import javax.websocket.OnMessage;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.auto.DeviceStatusThread;
import com.disrec.vo.OnUserVo;

public class DeviceInbound extends MessageInbound {

	private static String stauts="0";
	public static void setStauts(String stauts) {
		DeviceInbound.stauts = stauts;
	}
	public static String getStauts() {
		return stauts;
	}
	private String mac="";
	private String username="";
	public String getMac() {
		if("".equals(mac)){
			this.mac = Id.getId();
		}
		return mac;
	}
	public String getName() {
		return this.username;
	}
	
	
//	public void setMac(String mac) {
//		this.mac = Id.getId();
//	}
	
	//当前连接的用户名称
	//private final String user;
//	private static String user;
//	private String user;
	public DeviceInbound() {
	}
	public DeviceInbound(String username) {
		this.username=username;
	}
//	public DeviceInbound(String user) {
//		this.user = user;
//	}

//	private String user;
//	public DeviceInbound(String mac) {
//		this.user = mac;
//	}
//	public String getUser() {
//		return this.user;
//	}
	

	//建立连接的触发的事件
	@Override
	protected void onOpen(WsOutbound outbound) {

		/**
		 * 触发连接事件，在连接池中连接
		 */
//		JSONObject result = new JSONObject();
//		result.element("type", "user_join");
//		result.element("user", this.mac);
//		//向所有在线用户推送当前用户上线的消息
//		DeviceInboundPool.sendMessage(result.toString());
//
//		
//		/**
//		 * system info 游客  连接...
//		 */
//		result.element("from", mac);
//		result.element("content", "system info  "+mac+" 连接...");
//		result.element("timestamp", "111");
//		result.element("type", "message");
//		DeviceInboundPool.sendMessage(result.toString());
//		
//		/**
//		 * 在线用户的列表	维护
//		 */
//		result = new JSONObject();
//		result.element("type", "get_online_user");
//		result.element("list", DeviceInboundPool.getOnlineUser());
//		//向连接池添加当前的连接对象
//		////////////////////////////////////////////////////////////////
//		DeviceInboundPool.addMessageInbound(this,this.mac);
//		//向当前连接发送当前在线用户的列表
//		DeviceInboundPool.sendMessageToUser(this.mac, result.toString());
		//String id=Id.getId();
		DeviceInboundPool.addMessageInbound(this,getMac());
		//MessageOnLineUserPool.addOnLineUser(getMac(), username, this);
//		binding(this,getMac());
		DeviceInboundPool.sendMessageToUser(getMac(),"connectId:"+getMac());
		DeviceStatusThread.sendDeviceStatusInfoToConsole();
	}

	@Override
	protected void onClose(int status) {

		// 触发关闭事件，在连接池中移除连接
		////////////////////////////////////////////////////////////////
		String str=getMac();
		OnUsreNamePerConsolePool.removeMessageInbound(IDMacPool.getHost(str));
		DeviceInboundPool.removeMessageInbound(str);
		//MessageOnLineUserPool.removeOnLineUser(str);
		IDMacPool.removeHost(str);
		Monitoring.display();

//		/**
//		 * 在线用户的列表	维护
//		 */
//		JSONObject result = new JSONObject();
//		result.element("type", "user_leave");
//		result.element("user",this.getMac());
//		//向在线用户发送当前用户退出的消息
//		DeviceInboundPool.sendMessage(result.toString());
//
//		/**
//		 * system info 游客  退出...
//		 */
//		result.element("from", this.getMac());
//		result.element("content", "system info  "+this.getMac()+" 退出...");
//		result.element("timestamp", "test");
//		result.element("type", "message");
//		DeviceInboundPool.sendMessage(result.toString());
	}

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
		//throw new UnsupportedOperationException("Binary message not supported.");
		System.out.println("aaa");
	}

	//客户端发送消息到服务器时触发事件
	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
//		DeviceInboundPool.getOnlineUser();
		String mac = message.toString();
		
		OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
		if(vo != null){
			if(vo.getId() !=null){
				Date d = new Date();
				long time = d.getTime();
				HeartBeatPool.addMessage(time, mac);
			}
		}
		
	
		Date d1 = new Date();
		long time1 = d1.getTime();
		long l = HeartBeatPool.getMessage(mac);
		if(time1 - l > 300000){
			OnUsreNamePerConsolePool.removeMessageInbound(mac);
		}
		
	
		
		//向所有在线用户发送消息 // 这里处理的是文本数据
		//System.out.println("onTextMessage===>"+message);
//		DeviceInboundPool.sendMessage(message.toString());
	}
	

//	public void sendMessage(String message) {
//		try {
//			this.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
//		} catch (IOException e) {
//			System.out.println("sendMessage");
//		}
//	}

	public static void binding(DeviceInbound outbound ,String id) {
		DeviceInboundPool.addMessageInbound(outbound,id);
	}
}
