package com.web.help;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Help_Perset {

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/persetHelp", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {

		String str = "<h1>预制位  help</h1>";

		str += "<ul>";

		str += "<li><h4>处理预制位 </h4></li>";

//		 str += "<li>保存预置位, ID为预置位序号</li>";
//		 str += "id不能为空<br>http://ip:port/deviceService/presetSave?para=id";
		 str += "<li>调用预置位, ID为预置位序号</li>";
		 str += "id不能为空<br>http://ip:port/deviceService/perset?para=id";
//		 str += "<li>删除预置位, ID为预置位序号</li>";
//		 str += "id不能为空<br>http://ip:port/deviceService/presetClear?para=id";

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
