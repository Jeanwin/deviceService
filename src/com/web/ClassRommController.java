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

import com.service.GetInfoService;
import com.util.DeviceStatusPool;
import com.util.RegServicePool;

/**
 * @author zfc
 * 
 *         云台操作
 * 
 */
@Controller
public class ClassRommController {

	/**
	 * @author zfc
	 * 
	 *         教室日常刷新
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public void listByMac(HttpServletRequest request,
			HttpServletResponse response) {

		String macs = request.getParameter("mac");
		if (macs == null || "".equals(macs))
			return;
		String[] s = macs.split(",");
		Map<String, String> map = new HashMap<String, String>();
//		Map<String, ServiceInfo> mm;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String result = null;
		for (String mac : s) {
			if (mac == null || "".equals(mac))
				continue;
			/** 取缓存状态开始 */
//			string = "";
			try {
				//System.out.println("RegServicePool==>"+mac+".."+RegServicePool.getService(mac).isEmpty());
				if (!RegServicePool.getService(mac).isEmpty()){
					result = DeviceStatusPool.getDeviceStatus(mac);
					if (result != null &&  !"".equals(mac)) {
						map = new HashMap<String, String>();
						map.put(mac, result);
						list.add(map);
					}
				}
			} catch (Exception e) {
			}
				
			
			/** 取缓存状态结束 */
			/** 取录播机在线状态开始 */
			// string = getDeviceInfos(mac);
			// mm=new HashMap<String, ServiceInfo>();
			// mm=RegServicePool.getService(mac);
			// if(mm!=null&&mm.isEmpty()){
			// if (string != null && !"".equals(string)) {
			// map = new HashMap<String, String>();
			// map.put(mac, string+",,1");
			// }else{
			// map = new HashMap<String, String>();
			// map.put(mac, "1");
			// }
			// list.add(map);
			// }
			/** 取录播机在线状态结束 */
		}
		String string = JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(string);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}

	public String getDeviceInfos(String mac) {
		// List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		// List<String> list = new ArrayList<String>(
		// RegServicePool.getAllService());
		// Map<String, String> map;
		// for (String string : list) {
		// map = new HashMap<String, String>();
		// String str = GetInfoService.getConsoleOperationInfo(string);
		// if (!"".equals(str)) {
		// map.put(string, str);
		// ret.add(map);
		// }
		// }
		// return JSONArray.fromObject(ret).toString();
		return GetInfoService.getConsoleOperationInfo(mac);
	}
}
