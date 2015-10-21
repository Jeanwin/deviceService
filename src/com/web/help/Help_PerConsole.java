package com.web.help;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Help_PerConsole {

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/perConsoleHelp", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {

		String str = "<h1>进入导播台  help</h1>";

		str += "<ul>";
		str += "<li><h4>进入导播台</h4></li>";
		str += "consoleOperationInfo不能为空{2 向下,8向上,6向右,4向左,1推进,3拉远}<br>http://<ip>:<端口>/deviceService/consoleOperationInfo?consoleOperationInfo=2";

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
