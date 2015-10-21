package com.util;

import java.util.List;
import java.util.Map;

import com.vo.ServiceInfo;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class DeviceCache {

	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

//	public List<Element> verifyReadAll() {
//		@SuppressWarnings("rawtypes")
//		List list = cache.getKeys();
//		for (int i = 0; i < list.size(); i++) {
//			list.get(i).toString();
//			System.out.println(list.get(i).toString());
//		}
//		return cache.getKeys();
//	}
	
//	public List<?> verifyReadAllKeys() {
//		return cache.getKeys();
//	}
//
//	public Object verifyRead(String key) {
//		Element element = cache.get(key);
////		System.out.println((element == null) ? "null" : element
////				.getObjectValue());
//		return (element == null) ? null : element.getObjectValue();
//	}
//
//	public void verifyWrite(String key, String value) {
//		Element obj = new Element(key, value);
//		cache.put(obj);
//	}
//	public void verifyWrite(String key, Object value) {
//		Element obj = new Element(key, value);
//		cache.put(obj);
//	}
//
//	public void verifyDel(String key) {
//		cache.remove(key);
//	}

	

	public Map<String, Map<String,ServiceInfo>> verifyReadAll() {		
		return RegServicePool.getAllServiceListAllKeys();
	}

	public  Map<String,ServiceInfo> verifyRead(String key) {
//		Element element = cache.get(key);
//		System.out.println((element == null) ? "null" : element
//				.getObjectValue());
		return RegServicePool.getService(key);
	}

	public void verifyWrite(String key,  Map<String,ServiceInfo> list) {
//		Element obj = new Element(key, value);
//		cache.put(obj);
		RegServicePool.addService(key, list);
	}
//	public void verifyWrite(String key, Object value) {
//		Element obj = new Element(key, value);
//		cache.put(obj);
//	}

	public void verifyDel(String key) {
//		cache.remove(key);
		RegServicePool.removeService(key);
	}
}
