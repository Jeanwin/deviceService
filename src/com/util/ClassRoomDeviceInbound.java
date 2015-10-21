package com.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

public class ClassRoomDeviceInbound extends MessageInbound {

	//当前连接的用户名称
	//private final String user;
//	private static String user;
	private String user;

	public ClassRoomDeviceInbound(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}

	//建立连接的触发的事件
	@Override
	protected void onOpen(WsOutbound outbound) {

		/**
		 * 触发连接事件，在连接池中连接
		 */
		JSONObject result = new JSONObject();
		result.element("type", "user_join");
		result.element("user", this.user);
		//向所有在线用户推送当前用户上线的消息
		ClassRoomDeviceInboundPool.sendMessage(result.toString());

		
		/**
		 * system info 游客  连接...
		 */
		result.element("from", user);
		result.element("content", "system info  "+user+" 连接...");
		result.element("timestamp", "111");
		result.element("type", "message");
		ClassRoomDeviceInboundPool.sendMessage(result.toString());
		
		/**
		 * 在线用户的列表	维护
		 */
		result = new JSONObject();
		result.element("type", "get_online_user");
		result.element("list", ClassRoomDeviceInboundPool.getOnlineUser());
		//向连接池添加当前的连接对象
		////////////////////////////////////////////////////////////////
		ClassRoomDeviceInboundPool.addMessageInbound(this,this.user);
		//向当前连接发送当前在线用户的列表
		ClassRoomDeviceInboundPool.sendMessageToUser(this.user, result.toString());
//		WebSocketMessageInboundPool.sendMessageToUser(WebSocketMessageServlet.getCurrentUser(), result.toString());
	}

	@Override
	protected void onClose(int status) {

		// 触发关闭事件，在连接池中移除连接
		////////////////////////////////////////////////////////////////
		ClassRoomDeviceInboundPool.removeMessageInbound(user);

		/**
		 * 在线用户的列表	维护
		 */
		JSONObject result = new JSONObject();
		result.element("type", "user_leave");
		result.element("user",getUser());
		//向在线用户发送当前用户退出的消息
		ClassRoomDeviceInboundPool.sendMessage(result.toString());

		/**
		 * system info 游客  退出...
		 */
		result.element("from", user);
		result.element("content", "system info  "+user+" 退出...");
		result.element("timestamp", "test");
		result.element("type", "message");
		ClassRoomDeviceInboundPool.sendMessage(result.toString());
	}

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
		//throw new UnsupportedOperationException("Binary message not supported.");
	}

	//客户端发送消息到服务器时触发事件
	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		ClassRoomDeviceInboundPool.getOnlineUser();
		//向所有在线用户发送消息
		ClassRoomDeviceInboundPool.sendMessage(message.toString());
	}
	

//	public void sendMessage(String message) {
//		try {
//			this.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
//		} catch (IOException e) {
//			System.out.println("sendMessage");
//		}
//	}
}
