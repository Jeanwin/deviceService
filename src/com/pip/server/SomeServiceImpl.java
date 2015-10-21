package com.pip.server;

import java.util.HashMap;
import java.util.Map;

public class SomeServiceImpl implements ISomeService {
	
	public Map<String, Object> getinfo(Map<String, String> disrecInfo) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "deviceService");
		return map;
	}
} 
