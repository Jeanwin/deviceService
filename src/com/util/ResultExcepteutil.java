package com.util;

import net.sf.json.JSONObject;

public class ResultExcepteutil {
	private static final JSONObject jsonObject=new JSONObject();
    public static JSONObject ResultState(boolean State) {
    	if (State){
    		jsonObject.put("response_code", 0);
    		jsonObject.put("response_code_string", "success");
    	} else {
    		jsonObject.put("response_code", 1);
    		jsonObject.put("response_code_string", "error");
		}
		return jsonObject;
	}
    
    public static JSONObject ResulData(String data,int state) {
    	if ("".equals(data) ||data==null){
    		jsonObject.put("info", "");
    	} else {
    		jsonObject.put("info", data);	
		}
    	jsonObject.put("result", state);
    	return jsonObject;		
	}
}
