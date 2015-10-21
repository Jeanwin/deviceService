package com.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.disrec.service.DisrecService;
import com.disrec.vo.ClassRoomDeviceVo;

@Controller
public class Disrec_DeviceService {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ClassRoomDeviceVo xxx(HttpServletRequest request, HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return null;
		ClassRoomDeviceVo vo = DisrecService.genClassRoomDeviceVo(mac);
		return vo;
	}
	
	@RequestMapping(value = "/sendCmdToDevice", method = RequestMethod.GET)
	public void sendCmdToDevice(HttpServletRequest request,HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String para = request.getParameter("cmd");   
		if (para == null || "".equals(para))
			return;
		String str=DisrecService.SendCmdToDevice(mac,para);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str);
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	
}
