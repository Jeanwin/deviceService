package com.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.disrec.vo.MessageOnLineUser;

public class MessageOnLineUserPool {

	private static final Map<String,Map<MessageOnLineUser, DeviceInbound>> socketList = new HashMap<String,Map<MessageOnLineUser, DeviceInbound>>();
	
	public static synchronized Map<String,Map<MessageOnLineUser, DeviceInbound>> getSocketList() {
        return socketList;
    }
	
	private static synchronized Map<MessageOnLineUser, DeviceInbound> getUserMap(String username) {
		Map<MessageOnLineUser, DeviceInbound> temmap = new HashMap<MessageOnLineUser,DeviceInbound>();
		Iterator iterator=socketList.entrySet().iterator();
		while(iterator.hasNext()){
		    Map.Entry entry = (Map.Entry) iterator.next();
		    Map<MessageOnLineUser, DeviceInbound> valuemap = (Map<MessageOnLineUser, DeviceInbound>)entry.getValue();
		    Iterator vit=valuemap.entrySet().iterator();
		    while (vit.hasNext()) {
		    	Map.Entry ventry = (Map.Entry) vit.next();
		    	MessageOnLineUser mou=(MessageOnLineUser)ventry.getKey();
		    	if (mou.getUsername().equals(username)){
		    		temmap.put(mou, (DeviceInbound)ventry.getValue());
		    	}
			}
		}
		return temmap;
	}
	
	public static synchronized void addOnLineUser(String id,String username,DeviceInbound inbound) {
		MessageOnLineUser mouLineUser=new MessageOnLineUser();
		mouLineUser.setId(Id.getId());
		mouLineUser.setOnLineDate(new Date());
		mouLineUser.setUsername(username);
		Map<MessageOnLineUser, DeviceInbound> map=  new HashMap<MessageOnLineUser,DeviceInbound>();
		map.put(mouLineUser, inbound);
		socketList.put(id, map);
	}
	
	public static synchronized void removeOnLineUser(String id){
		socketList.remove(id);
	}
}
