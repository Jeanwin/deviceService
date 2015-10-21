package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.disrec.vo.OnUserVo;
import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.IDMacPool;
import com.util.Id;
import com.util.OnUsreNamePerConsolePool;

/**
 * 发送线程
 * 
 *
 */
public class M2MSend extends Thread {
	
	//用户集合
	private Map<String,Socket> map =  TCPServer.getMap();
	//当前用户
	private Socket s;
	
	private String name;
	
	public M2MSend(Socket s){
		this.s = s;
	}
	
	public void run(){
		try {
			//读取用户信息
			BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
			//不断的读取写出数据
			while(true){
				//接收数据
				String clientInputStr = null;
				//如果读取信息不为空
 				if((clientInputStr=reader.readLine()) != null){
					// 处理客户端数据
					System.out.println("客户端发过来的内容:" + clientInputStr);
					Map<String, String> map2 = new HashMap<String, String>();
					map2.put("result", "0");
					String[] arrts = clientInputStr.split("&");
					String flag = "";
					String reqUserName = "";
					String mac = "";
					String othername = "";
					if(arrts.length == 1){
						String[] str1 = arrts[0].split("=");
						if (str1.length > 1) {
							flag = str1[1];
						}
					}
					if(arrts.length > 1 && arrts.length <= 3){
						String[] str1 = arrts[0].split("=");
						String[] str2 = arrts[1].split("=");
						String[] str3 = arrts[2].split("=");
						
						if (str1.length > 1) {
							flag = str1[1];
						}
						if (str2.length > 1) {
							reqUserName = str2[1];
						}
						
						if (str3.length > 1) {
							mac = str3[1];
						}
					} 
					if(arrts.length > 3){
						String[] str1 = arrts[0].split("=");
						String[] str2 = arrts[1].split("=");
						String[] str3 = arrts[2].split("=");
						String[] str4 = arrts[3].split("=");
						
						if (str1.length > 1) {
							flag = str1[1];
						}
						if (str2.length > 1) {
							reqUserName = str2[1];
						}
						
						if (str3.length > 1) {
							mac = str3[1];
						}
						if (str4.length > 1) {
							othername = str4[1];
						}
					}
					if (flag.equals("heartbeat")) {
						map2.put("result", "10");//心跳
//						map2.put("username", reqUserName);
					}
					
					if (flag.equals("disagree")) {
						sendMessage(map,reqUserName,othername,"4");
						map2.put("result", "6");//不同意
						map2.put("username", reqUserName);
//						 Thread.sleep(1000);
						map.remove(othername);
					}
					
					if (flag.equals("agree")) {
						if (StringUtils.isNotBlank(reqUserName)
								&& StringUtils.isNotBlank(mac)) {
							String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
							IDMacPool.removeHost(id);
							OnUsreNamePerConsolePool.removeMessageInbound(mac);
							map2.put("result", "0");
							
							OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
							if (vo == null) {
								vo = new OnUserVo();
								vo.setOnUserName(othername);
								vo.setMac(mac);
								vo.setId(Id.getId());
								OnUsreNamePerConsolePool.addMessage(vo, mac);
								IDMacPool.addHost(Id.getId(), mac);
							} 
						}
						sendMessage(map,reqUserName,othername,"3");//同意
						map.remove(reqUserName);
					}
					if (flag.equals("quit")) {
						if (StringUtils.isNotBlank(reqUserName)
								&& StringUtils.isNotBlank(mac)) {
							OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
							if(vo != null){
								String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
								IDMacPool.removeHost(id);
								OnUsreNamePerConsolePool.removeMessageInbound(mac);
								map.remove(reqUserName);
								map2.put("result", "0");
							}else{
								map2.put("result", "0");
							}
						}
					}
					if (flag.equals("regist")) {
						if (StringUtils.isNotBlank(reqUserName)
								&& StringUtils.isNotBlank(mac)) {
							map.put(reqUserName, s);
							OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
							if (vo == null) {
								vo = new OnUserVo();
								vo.setOnUserName(reqUserName);
								vo.setMac(mac);
								vo.setId(Id.getId());
								OnUsreNamePerConsolePool.addMessage(vo, mac);
								IDMacPool.addHost(Id.getId(), mac);
							} else {
								if(vo.getOnUserName().equals(reqUserName)){
									map2.put("result", "5");//已经存在
									map2.put("msg", "导播已经开启");
								}else{
									map2.put("result", "1");
									map2.put("username", vo.getOnUserName());
									sendMessage(map,reqUserName,vo.getOnUserName(),"2");
								}
							}
						} else {
							map2.put("result", "0");
						}
					}
					sendMessage2(map2,s);
				}
			}
			
		} catch (Exception e) {
			//用户下线
//			System.err.println(ip + " 已下线 , 当前在线人数为: " + list.size() + " 人 !");
		}
		
		
	}

	private void sendMessage2(Map<String, String> map2, Socket s) throws IOException {
		JSONObject json = CosJSONUtil.toJsonObjectFromMap(map2);
//		//获取对象的输出流
		PrintWriter pw = new PrintWriter(s.getOutputStream());
		//System.out.println("sendMessage2 === >benren"+json.toString());
		//写入信息  某人正在使用
		pw.println(json.toString());
		pw.flush();
		
	}

	private void sendMessage(Map<String, Socket> map, String reqUsername, String othername, String resultflag) throws Exception {
		Map<String, String> sendMap = new HashMap<String, String>();
		Socket other = map.get(othername);
		sendMap.put("result", resultflag);// 4 回复不同意 3 同意 2申请
		sendMap.put("username", reqUsername);// 同意不同意的对象   申请对象
		JSONObject json = CosJSONUtil.toJsonObjectFromMap(sendMap);
//		//获取对象的输出流
		PrintWriter pw = new PrintWriter(other.getOutputStream());
		//写入信息
		//System.out.println("sendMessage === >other"+json.toString());
		pw.println(json.toString());
		pw.flush();
		
	}

}
