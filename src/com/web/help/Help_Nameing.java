package com.web.help;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Help_Nameing {

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/nameHelp", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {

		String str = "<h1>name help</h1>";

		str += "<ul>";
		str += "<li><h4>处理设备的服务注册</h4></li>";
		str += "mac,type,id不能为空<br>http://ip:port/deviceService/registering?serviceinfo=ip_mac_type_id_url";

//		str += "<li><h4>处理设备的服务注销</h4></li>";
//		str += "http://<ip>:<端口>/deviceService/unRegistering?mac=mac";

		str += "<li><h4>得到所有服务</h4></li>";
		str += "http://<ip>:<端口>/deviceService/list";

//		str += "<li><h4>得到mac的所有服务</h4></li>";
//		str += "http://ip:port/deviceService/listByMac?mac=<mac>&id=id<br>";
//		str += "mac,type,id不能为空<br>http://ip:port/deviceService/listByMac?serviceinfo=ip_mac_type_id_url";

		str += "<li><h4>处理设备端发送的心跳数据</h4></li>";
		str += "mac,type,id不能为空<br>http://ip:port/deviceService/heartbeat?serviceinfo=ip_mac_type_id_url";

//		str += "<li><h4>手动执行  检测没有心跳脉冲注销服务</h4></li>";
//		str += "http://ip:port/deviceService/flushAll";

		// str += "<li>鼠标跟踪</li>";
		// str += "x,y不能为空<br>http://ip:port/deviceService/mouseTrace&x=x&y=y";
		// str += "<li>保存预置位, ID为预置位序号</li>";
		// str += "id不能为空<br>http://ip:port/deviceService/presetSave?id=id";
		// str += "<li>调用预置位, ID为预置位序号</li>";
		// str += "id不能为空<br>http://ip:port/deviceService/presetCall?id=id";
		// str += "<li>删除预置位, ID为预置位序号</li>";
		// str += "id不能为空<br>http://ip:port/deviceService/presetClear?id=id";

		str += "</ul>";
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(str);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}

}
