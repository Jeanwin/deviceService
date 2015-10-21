package com.util;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.deviceService.ClassRoomDeviceServlet;

public class DeviceInboundPool {

	// 保存连接的MAP容器
	private static final Map<String, DeviceInbound> connections = new HashMap<String, DeviceInbound>();

	public static String getUser() {
		return ClassRoomDeviceServlet.getCurrentUser();
	}

	// 获取所有的在线用户
	public static Set<String> getOnlineUser() {
		return connections.keySet();
	}

	// 向连接池中添加连接
	public static DeviceInbound getMessageInbound(String mac) {
		return connections.get(mac);
	}

	// 向连接池中添加连接
	public static void addMessageInbound(DeviceInbound inbound, String user) {
		// 添加连接
//		System.out.println("添加连接前 ...");
//		DeviceInboundPool.forch();
		// //////////////////////////////////////////////////////////////
		connections.put(user, inbound);
//		System.out.println("添加连接..." + user);
//		DeviceInboundPool.forch();
//		System.out.println("添加连接后...");
	}

	public static void removeMessageInbound(String user) {

//		System.out.println("移除连接前 ...");
//		DeviceInboundPool.forch();
		// //////////////////////////////////////////////////////////////
		connections.remove(user);
//		System.out.println("移除连接..." + user);
//		DeviceInboundPool.forch();
//		System.out.println("移除连接后...");
	}

	public static void sendMessageToUser(String user, String message) {
		try {
			// 向特定的用户发送数据
			DeviceInbound inbound = connections.get(user);
			if (inbound != null) {
				// //////////////////////////////////////////////////////////////
				inbound.getWsOutbound().writeTextMessage(
						CharBuffer.wrap(message));
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static String sendMessageToUserExt(String User,String message) {
		String Result="sendmessage start";
		try {
			for (DeviceInbound inbound : connections.values()) {
				   if (!"".equals(inbound.getName()) && User.equals(inbound.getName())){
					   inbound.getWsOutbound().writeTextMessage(
								CharBuffer.wrap(message)); 
					   Result="sendmessage success";
				   }
				  }
			return Result;
		} catch (Exception e) {
			Result="sendmessage error";
		}
		return Result;		
	}
	
	// 向所有的用户发送消息
	public static void sendMessageToAll(String message) {
		try {
			Set<String> keySet = connections.keySet();
			for (String key : keySet) {
				DeviceInbound inbound = connections.get(key);
				if (inbound != null) {
					// //////////////////////////////////////////////////////////////
					inbound.getWsOutbound().writeTextMessage(
							CharBuffer.wrap(message));
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static void sendMessage(String message) {
		try {
			Set<String> keySet = connections.keySet();
			for (String key : keySet) {
				DeviceInbound inbound = connections.get(key);
				if (inbound != null) {
					// //////////////////////////////////////////////////////////////
					inbound.getWsOutbound().writeTextMessage(
							CharBuffer.wrap(message));
				}
			}
		} catch (Exception e) {
		}
	}

	public static void forch() {
		Set<String> keySet = getOnlineUser();
		if (keySet.size() <= 0)
			return;
		System.out.print(" DeviceInboundPool forch()   在线导播台   ");
		for (String key : keySet) {
//			System.out.print("  " + key);
		}
//		System.out.println("  ");
	}

	public static void main(String[] args) {
//		System.out.println();
		System.exit(0);
	}
}
