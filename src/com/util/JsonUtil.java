package com.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;



public class JsonUtil {
	 private static Gson gson = new Gson();
	 
    public static String toJson(Object object) {
        String json = gson.toJson(object);
        return json;
    }

    public static <T> T jsonToObject(Object json, Class<T> clazz) {
    	if(json instanceof HttpServletRequest){
    		HttpServletRequest req = (HttpServletRequest) json;
    		try {
				String str = new BufferedReader(new InputStreamReader(req.getInputStream(),"utf-8")).readLine();
				return gson.fromJson(str, clazz);
			} catch (Exception e) {
				return null;
			}
    	}
        return gson.fromJson(String.valueOf(json), clazz);
    }
    
   
    
}
