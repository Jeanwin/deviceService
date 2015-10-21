package com.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.disrec.service.DisrecService;
import com.disrec.vo.ClassRoomDeviceVo;
import com.service.GetInfoService;
import com.util.RegHostPool;
import com.util.RegServicePool;

@Controller
public class DisrecGetInfo_Disrec {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deviceInfo", method = RequestMethod.GET)
	public ClassRoomDeviceVo xxx(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return null;
		ClassRoomDeviceVo vo = DisrecService.genClassRoomDeviceVo(mac);
		return vo;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping(value = "/shutdownAndRestart", method = RequestMethod.GET)
	public void shutdownAndRestart(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
		String para = request.getParameter("para");
		if (para == null || "".equals(para))
			return;
		// 正式环境要打开
		String[] s = mac.split(",");
		for (String str : s) {
			DisrecService.exec(str, para);
			RegServicePool.removeService(str);
		}
		return;
	}

	// @RequestMapping(value = "/deviceInfo", method = RequestMethod.GET)
	// public ClassRoomDeviceVo xxx(HttpServletRequest request,
	// HttpServletResponse response) {
	// ClassRoomDeviceVo vo = DisrecService.genClassRoomDeviceVo();
	// return vo;
	// }

	/**
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping(value = "/getIpByMac", method = RequestMethod.GET)
	public void getIpByMac(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
			return;
//		System.out.println("/shutdownAndRestart---------------" + mac);
		// 未实现
		// if(){}
		// String str = "192.168.12.47";
		// String str = RegHostPool.getHost(mac.toLowerCase());
		String str = RegHostPool.getHost(mac);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !str.isEmpty()) {
				pw.print(str.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
		return;
	}

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getLiveStatus", method = RequestMethod.GET)
	public void getLiveStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String macs = request.getParameter("mac");
		if (macs == null || "".equals(macs))
			return;
		String[] s = macs.split(",");
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String string;
		for (String mac : s) {
			if (mac == null || "".equals(mac))
				continue;
			try {
				string = GetInfoService.getVdsLiveStatus(mac);
			} catch (Exception e) {
				string = "";
			}
			if (string != null && !"".equals(string) && !"0".equals(string)) {
				map = new HashMap<String, String>();
				map.put(mac, "1");
				list.add(map);
			}
		}
		string = JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (string != null && !"".equals(string)) {
				pw.print(string);
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRecordStatus", method = RequestMethod.GET)
	public void getRecordStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String macs = request.getParameter("mac");
		if (macs == null || "".equals(macs))
			return;
		String[] s = macs.split(",");
		Map<String, String> map;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String string;
		for (String mac : s) {
			string = "";
			if (mac == null || "".equals(mac))
				continue;
			try {
				string = GetInfoService.getRecordStatus(mac);
			} catch (Exception e) {
				string = "";
			}
			// if(string!=null&&!"".equals(string)&&"Recording".equals(string)){
			if ("Recording".equals(string)) {
				map = new HashMap<String, String>();
				map.put(mac, "1");
				list.add(map);
			}
		}
		string = JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (string != null && !"".equals(string)) {
				pw.print(string);
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
}
