package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.util.CosJSONUtil;

/**
 * 接收信息
 * 
 *
 */
public class M2MReceive extends Thread {
	
	private Socket s;
	private HttpServletResponse response;
	public M2MReceive(Socket s, HttpServletResponse response){
		this.s = s;
		this.response=response;
	}

	public void run(){
		
		try {
			
			//构建输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			//不断的接收信息
			while(true){
				
				String info = null;
				
				//接收信息
				if((info=br.readLine()) != null){
					//System.out.println(info);
					
					 if ("{\"result\":\"1\"}".equals(info)) {  
			                //System.out.println("客户端将关闭连接");  
			                Thread.sleep(500);  
			                break;  
			            }
					
//					JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
					response.setContentType("text/html;charset=UTF-8");
					try {
						PrintWriter pw = response.getWriter();
						if (info != null && !info.isEmpty()) {
							pw.print(info.toString());
						}
						pw.flush();
						pw.close();
					} catch (Exception e) {
					}
				}
				
			}
			
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
