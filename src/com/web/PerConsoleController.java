package com.web;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.auto.AutoJob;
import com.common.M2MReceive;
import com.common.TCPServer;
import com.disrec.vo.OnUserVo;
import com.disrec.vo.ReqUserVo;
import com.util.CosJSONUtil;
import com.util.DeviceInboundPool;
import com.util.IDMacPool;
import com.util.Monitoring;
import com.util.OnUsreNamePerConsolePool;
import com.util.ReqUserNamePerConsolePool;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class PerConsoleController {
	private static Logger logger = Logger.getLogger(PerConsoleController.class);
	// private static OnUsreNamePerConsolePool getOnUsreNameInstance() {
	// return OnUsreNamePerConsolePool.getInstance();
	// }

	// private static ReqUserNamePerConsolePool getReqUserNameInstance() {
	// // return ReqUserNamePerConsolePool.getInstance();
	// return null ;
	// }
	private boolean flag = false;
	@RequestMapping(value = "/socketServer", method = RequestMethod.GET)
	public void socketServer(HttpServletRequest request,
			HttpServletResponse response) {
//		  logger.info("服务器启动...\n");  
		  try {
			  JSONObject json = JSONObject.fromObject("{\"message\":\"server already start...\"}");
			  JSONObject json1 = JSONObject.fromObject("{\"message\":\"server is starting...\"}");
				PrintWriter pw = response.getWriter();
				if(flag){
					pw.print(json);
				}else{
					pw.print(json1);
				}
				pw.flush();
				pw.close();
			} catch (Exception e) {
			}
		  if(!flag){
			  flag = true;
			  try {
				  logger.info("服务器准备就绪 ...");
				  new TCPServer();
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }
		
	}
	/**
	 * http://localhost:8080/deviceService/perConsole?mac=11&reqUserName=tom
	 * 
	 * {"result":"0"} {"result":"1","username":"tom"}
	 * 独占功能   当result=0时，表示没有使用
	 * 当result=1 username=tom 表示tom正在导播
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/perConsole", method = RequestMethod.GET)
	public void perConsole(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "0");

		String reqUserName = request.getParameter("reqUserName");   
		String mac = request.getParameter("mac");
		if (reqUserName == null || "".equals(reqUserName))
			return;
		if (mac == null || "".equals(mac))
			return;
		
		logger.info("perConsole  == >进入导播台的各种信息===>reqUserName="+reqUserName+",mac="+mac);
		OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
		if (vo == null) {
			vo = new OnUserVo();
			vo.setOnUserName(reqUserName);
			vo.setMac(mac);
			OnUsreNamePerConsolePool.addMessage(vo, mac);
			logger.info("mac="+mac+",没有存入OnUsreNamePerConsolePool库中，表示该mac无人导播");
		} else {
			if (vo.getId() != null){
				map.put("result", "1");
				map.put("username", vo.getOnUserName());
				
				DeviceInboundPool.sendMessageToUser(vo.getId(), "reqUserName:"
						+ reqUserName);
				logger.info("mac="+mac+",表示该mac有人正在导播username："+vo.getOnUserName()+"   同时发送信息==》给"+reqUserName);
			}else{
				map.put("result", "0");
			}
		}

		display("/perConsole");

		JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (json != null && !json.isEmpty()) {
				pw.print(json.toString());
			}
			pw.flush();
			pw.close();
			logger.info("返回数据："+json);
		} catch (Exception e) {
		}
	}

	/**
	 * http://localhost:8080/deviceService/bind?mac=11&connectId=xxx
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.GET)
	public void bind(HttpServletRequest request, HttpServletResponse response) {

		String connectId = request.getParameter("connectId");
		String mac = request.getParameter("mac");
		String reqUserName = request.getParameter("reqUserName");
		logger.info("进入导播台的bind信息===>connectId="+connectId+",reqUserName="+reqUserName);
		if (connectId == null || "".equals(connectId))
			return;
		if (mac == null || "".equals(mac))
			return;
		if (reqUserName == null || "".equals(reqUserName))
			return;
		OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
		if (vo != null) {
			if(!vo.getOnUserName().equals(reqUserName)){
				String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
				if(id != null){
					IDMacPool.removeHost(id);
					
				}
				OnUsreNamePerConsolePool.removeMessageInbound(mac);
				ReqUserNamePerConsolePool.removeMessageInbound(reqUserName);
				
				vo = new OnUserVo();
				vo.setOnUserName(reqUserName);
				vo.setMac(mac);
				vo.setId(connectId);
			}else{
				logger.info("表示该mac正在bind");
				vo.setId(connectId);
				
			}
		} else {
			logger.info("表示该mac正在重新缓存及bind");
			vo = new OnUserVo();
			vo.setOnUserName(reqUserName);
			vo.setMac(mac);
			vo.setId(connectId);
		}
		OnUsreNamePerConsolePool.addMessage(vo, mac);
		IDMacPool.addHost(connectId, mac);
		display("/bind");
	}

	/**
	 * http://localhost:8080/deviceService/confome?mac=11&para=0&reqUserName=aa
	 * http://localhost:8080/deviceService/confome?mac=11&para=1&reqUserName=aa
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/confome", method = RequestMethod.GET)
	public void confome(HttpServletRequest request, HttpServletResponse response) {

		String para = request.getParameter("para");
		String mac = request.getParameter("mac");
		String reqUserName = request.getParameter("reqUserName");
		if (reqUserName == null || "".equals(reqUserName))
			return;
		if (para == null || "".equals(para))
			return;
		if (mac == null || "".equals(mac))
			return;
		
		logger.info("confome === >被申请人的回应：para:"+para+",reqUserName:"+reqUserName);
		// 1231
		OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
		if (("1".equals(para))) {
			logger.info("vo.getOnUserName() = "+vo.getOnUserName() +"  "+reqUserName);
			if(vo != null && !vo.getOnUserName().equals(reqUserName)){
				String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
				IDMacPool.removeHost(id);
				OnUsreNamePerConsolePool.removeMessageInbound(mac); 
				
//				ReqUserNamePerConsolePool.removeMessageInbound(reqUserName);
			}else{
			//等于空，不行  相等，	
			}
		}
		// 1231
		if (ReqUserNamePerConsolePool.getMessage(reqUserName) == null) {
			ReqUserVo perConsoleVo = new ReqUserVo();
			perConsoleVo.setReqUserName(reqUserName);
			perConsoleVo.setStatus(para);//当状态为1时，表示同意也表示申请人可以进入，当前导播人员退出
			perConsoleVo.setMac(mac);//当状态为0时，表示不同意也表示申请人不可以进入，当前导播人员继续
			ReqUserNamePerConsolePool.addMessage(perConsoleVo, reqUserName);
		} else {
			ReqUserNamePerConsolePool.getMessage(reqUserName).setStatus(para);
		}
		logger.info(ReqUserNamePerConsolePool.all());
		display("/confome");
	}

	/**
	 * http://localhost:8080/deviceService/loop?mac=11&reqUserName=tom
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/loop", method = RequestMethod.GET)
	public void loop(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> map = new HashMap<String, String>();
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		String reqUserName = request.getParameter("reqUserName");
		if (reqUserName == null || "".equals(reqUserName))
			return;
		String ret = "";
		if (ReqUserNamePerConsolePool.getMessage(reqUserName) != null)
		{
			ret = ReqUserNamePerConsolePool.getMessage(reqUserName).getStatus();
			logger.info("loop === >reqUserName："+reqUserName+",ret:"+ret+"==>状态");
			if ((ret == null) || ("".equals(ret))) {
				map.put("status", "2");
			} else {
				map.put("status", ret);
				ReqUserNamePerConsolePool.removeMessageInbound(reqUserName);
			}
		}else{
			map.put("status", "2");
		}
		// 1231
		display("/loop");

		JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (json != null && !json.isEmpty()) {
				pw.print(json.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	/**
	 *      //超时 关闭弹出框
	 * @param request  
	 * @param response
	 * 我是申请方
	 * reqUserName 发给对方同意方。
	 */
	@RequestMapping(value = "/control", method = RequestMethod.GET)
	public void control(HttpServletRequest request, HttpServletResponse response) {

		String reqUserName = request.getParameter("reqUserName");
		if (reqUserName == null || "".equals(reqUserName))
			return;
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		String para = request.getParameter("para");
		if (para == null || "".equals(para))
			para="";
		logger.info("control === >关闭窗口：para:"+para+",reqUserName:"+reqUserName);
		OnUserVo onUserVo = OnUsreNamePerConsolePool.getMessage(mac);
		String connectId = "";
//		if (onUserVo != null && onUserVo.getId() != null){
//			connectId = onUserVo.getId();
//			if(StringUtils.isNotBlank(connectId)){
//				DeviceInboundPool.sendMessageToUser(connectId, "closeWindow:1");
//				
//				IDMacPool.removeHost(connectId);
//				OnUsreNamePerConsolePool.removeMessageInbound(mac);
//				ReqUserNamePerConsolePool.removeMessageInbound(reqUserName);
//			}
////			String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
//		}
			
//		if (!"".equals(string))
//			DeviceInboundPool.sendMessageToUser(string, "closeWindow:1");
//		if(OnUsreNamePerConsolePool.getMessage(mac) != null){
//			String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
//			IDMacPool.removeHost(id);
//			ReqUserNamePerConsolePool.removeMessageInbound(reqUserName);
//			OnUsreNamePerConsolePool.removeMessageInbound(mac);
//		}
//		OnUserVo vo = new OnUserVo();
//		vo.setOnUserName(reqUserName);
//		vo.setMac(mac);
//		OnUsreNamePerConsolePool.addMessage(vo, mac);

	}

	/**
	 * http://localhost:8080/deviceService/clearReqUserNameStatus?mac=11
	 * 清除独占
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/clearReqUserNameStatus", method = RequestMethod.GET)
	public void clearReqUserNameStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String reqUserName = request.getParameter("reqUserName");
		if (reqUserName == null || "".equals(reqUserName))
			return;
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		
//		if(OnUsreNamePerConsolePool
//				.getMessage(mac) != null){
////			DeviceInboundPool.sendMessageToUser(OnUsreNamePerConsolePool
////					.getMessage(mac).getId(), "result");
//			ReqUserNamePerConsolePool.removeMessageInbound(reqUserName);
//			OnUsreNamePerConsolePool.removeMessageInbound(mac);
//			
//		}
		if (StringUtils.isNotBlank(reqUserName)
				&& StringUtils.isNotBlank(mac)) {
			OnUserVo vo = OnUsreNamePerConsolePool.getMessage(mac);
			if(vo != null && vo.getOnUserName().equals(reqUserName)){
				String id = OnUsreNamePerConsolePool.getMessage(mac).getId();
				if(id != null){
					IDMacPool.removeHost(id);
					
				}
				OnUsreNamePerConsolePool.removeMessageInbound(mac);
				logger.info("clearReqUserNameStatus == >清除：mac:"+mac+",reqUserName:"+reqUserName);
			}
		}
		
		display("/clearReqUserNameStatus");
	}

	public void display(String str) {
		Monitoring.display();
	}
}
