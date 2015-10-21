package com.util;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.deviceService.ClassRoomDeviceServlet;

public class ClassRoomDeviceInboundPool {

	//保存连接的MAP容器
	private static final Map<String,ClassRoomDeviceInbound > connections = new ConcurrentHashMap<String, ClassRoomDeviceInbound>();
	

	public static String getUser(){
		return ClassRoomDeviceServlet.getCurrentUser();
	}
	
	//获取所有的在线用户
	public static Set<String> getOnlineUser(){
		return connections.keySet();
	}
	
	
	//向连接池中添加连接
	public static void addMessageInbound(ClassRoomDeviceInbound inbound,String user){
		//添加连接
//		System.out.println("添加连接前 ...");
//		ClassRoomDeviceInboundPool.forch();
		////////////////////////////////////////////////////////////////	
		connections.put(user, inbound);
//		System.out.println("添加连接..."+user);
//		ClassRoomDeviceInboundPool.forch();
//		System.out.println("添加连接后...");
	}
	

	public static void removeMessageInbound(String user){

//		System.out.println("移除连接前 ...");
//		ClassRoomDeviceInboundPool.forch();
		////////////////////////////////////////////////////////////////
		connections.remove(user);
//		System.out.println("移除连接..."+user);
//		ClassRoomDeviceInboundPool.forch();
//		System.out.println("移除连接后...");
	}
	
	public static void sendMessageToUser(String user,String message){
		try {
			//向特定的用户发送数据
			ClassRoomDeviceInbound inbound = connections.get(user);
			if(inbound != null){
				////////////////////////////////////////////////////////////////
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
			}
		} catch (Exception e) {
		}
	}
	
	//向所有的用户发送消息
	public static void sendMessage(String message){
		try {
			Set<String> keySet = connections.keySet();
			for (String key : keySet) {
				ClassRoomDeviceInbound inbound = connections.get(key);
				if(inbound != null){
					////////////////////////////////////////////////////////////////
					inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
				}
			}
		} catch (Exception e) {
		}
	}

	public static void forch(){
			Set<String> keySet = getOnlineUser();
			if(keySet.size()<=0) return;
//			System.out.print(" ClassRoomDeviceInbound forch()   当前在线教室    ");
			for (String key : keySet) {
//				System.out.print("  " + key );
			}
//			System.out.println("  ");
	}
	
}
