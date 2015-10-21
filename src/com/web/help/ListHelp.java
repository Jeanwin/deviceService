package com.web.help;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.disrec.vo.OnUserVo;
import com.disrec.vo.ReqUserVo;
import com.util.DeviceCache;
import com.util.IDMacPool;
import com.util.OnUsreNamePerConsolePool;
import com.util.ReqUserNamePerConsolePool;
import com.util.Url;

@Controller
public class ListHelp {

	String url = Url.getMainUrl()+":8080/";

	@Resource
	DeviceCache deviceCache;

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/listHelp", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {
		
		
		

		String str = "";
		 Map<String, ReqUserVo> re=ReqUserNamePerConsolePool.all();
		 Map<String, OnUserVo> on=OnUsreNamePerConsolePool.all();
		 Map<String, String> id=IDMacPool.all();

			str += "ReqUserName : ";
			str += JSONArray.fromObject(re).toString();
			str += "  OnUsreName : ";
			str += JSONArray.fromObject(on).toString();
			str += "  id : ";
			str += JSONArray.fromObject(id).toString();
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
