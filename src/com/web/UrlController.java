package com.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;










import com.util.CenterControlStatePool;
import com.util.DeviceRtspUrlsPool;
import com.util.Url;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class UrlController {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getServerUrl", method = RequestMethod.GET)
	public void getServerUrl(HttpServletRequest request,
			HttpServletResponse response) {
		String str = "";
		String type = request.getParameter("type");
		if (type != null && !"".equals(type)) {
			str = Url.getServerUrl(type);
		}
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(str);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	@RequestMapping(value = "/clearPool", method = RequestMethod.GET)
	public void clearPool(HttpServletRequest request,
			HttpServletResponse response) {
		DeviceRtspUrlsPool.clear();
		CenterControlStatePool.clear();
		
		
	}
}
