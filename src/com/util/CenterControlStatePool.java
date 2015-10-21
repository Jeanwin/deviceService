package com.util;

import java.util.HashMap;
import java.util.Map;

public class CenterControlStatePool {
	private static final Map<String, Map<String,String>> CenterControlState = new HashMap<String, Map<String,String>>();
	
	public static void addCenterControlstate(String mac,Map<String, String> state) {
		CenterControlState.put(mac, state);
	}
	
    public static Map<String, String> getCenterControlAllStateByMac(String mac) {
    	return CenterControlState.get(mac);
		
	}
    
    public static String getCenterControlAttributeStateByMacAtt(String mac,String attribute) {
    	return CenterControlState.get(mac).get(attribute);
	}
    
    public static void clear() {
    	CenterControlState.clear();
		
	}
    
}
