package com.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

public class DeviceServiceInbound extends MessageInbound {

	// private static int stuts = -1;

	@Override
	protected void onOpen(WsOutbound outbound) {
		// stuts++;
//		System.out.println("clint open... ");
		// WebSocketMessageInboundPool.addMessageInbound(this);
		sendMessage("server open...");
	}

	@Override
	protected void onClose(int status) {
		// stuts--;
//		System.out.println("clint close... ");
		// sendMessage("server close...");
	}

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
//		System.out.println("clint onBinaryMessage... ");
		throw new UnsupportedOperationException("Binary message not supported.");
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
//		System.out.println("server receive " + message.toString());
		sendMessage(message.toString());
	}

	public void sendMessage(String message) {
		// if(stuts<0)return;
		try {

			this.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
		} catch (Exception e) {
//			System.out.println("sendMessage");
		}
	}

	private static final DeviceServiceInbound deviceService = new DeviceServiceInbound();

	private DeviceServiceInbound() {
	}

	public synchronized static DeviceServiceInbound getInstance() {
		return deviceService;
	}
	// public static DeviceServiceInbound getInstance() {
	// if (deviceService == null) {
	// synchronized (DeviceServiceInbound.class) {
	// if (deviceService == null) {
	// deviceService = new DeviceServiceInbound();
	// }
	// }
	// }
	// return deviceService;
	// }
}
