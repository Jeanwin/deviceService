package com.web.help;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.disrec.vo.OnUserVo;
import com.disrec.vo.ReqUserVo;
import com.util.IDMacPool;
import com.util.OnUsreNamePerConsolePool;
import com.util.ReqUserNamePerConsolePool;

@Controller
public class TreadCount {

	public static int i = 0;

	@RequestMapping(value = "/TreadCount", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {
		
		
		

		String str = ""+i;
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
