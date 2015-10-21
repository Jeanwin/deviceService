package com.web.help;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Help_ShutdownAndRestart {

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/shutdownAndRestartHelp", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {

		String str = "<h1>shutdownAndRestart help</h1>";

		str += "<ul>";
		str += "<li><h4>处理设备的重启和关机</h4>DisrecGetInfo_Disrec  shutdownAndRestart</li>";
		str += "<para>不能为空  且为[shutdown,restart]<br>http://ip:port/deviceService/shutdownAndRestart?para=<para>";

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
