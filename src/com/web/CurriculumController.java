package com.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.disrec.service.Disrec2DeviceService;
import com.service.SetingService;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class CurriculumController {
	private static Logger LOG = Logger.getLogger(NameingController.class);  
	/**
	 * 要录播机立刻同步课表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateClassSchedule", method = RequestMethod.GET)
	public void updateClassSchedule(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return ;
		String result = SetingService.updateClassSchedule(mac);
		response.setContentType("text/html;charset=UTF-8");
		LOG.info("updateClassSchedule == >"+result);
		try {
			PrintWriter pw = response.getWriter();
			pw.print("ok");
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	/**
	 * 同步课表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/curriculum", method = RequestMethod.GET)
	public void perset(HttpServletRequest request,
			HttpServletResponse response) {
		String para = request.getParameter("mac");
		if (para == null || "".equals(para))
		return ;
		
		String json =Disrec2DeviceService.exec(para);
		if(json!=null){
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (json != null && !json.isEmpty()) {
				pw.print(json.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
		}

	}

}
