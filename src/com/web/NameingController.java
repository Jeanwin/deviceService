package com.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.DeviceRegisteringService;
import com.service.LivingService;
import com.util.CosJSONUtil;
import com.util.DeviceCache;
import com.util.DeviceStatusPool;
import com.util.RegServicePool;
import com.vo.ServiceInfo;

@Controller
public class NameingController {
	private static Logger LOG = Logger.getLogger(NameingController.class);  
	@Resource
	DeviceCache deviceCache;

	/**
	 * @author zfc
	 * 
	 *         得到所有服务
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {
        
		JSONObject json = CosJSONUtil.toJsonObjectFromMap(getAll());
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
		//LOG.info("得到所有的服务信息： "+json);
		// JSONObject json = CosJSONUtil.toJsonObjectFromMap(getAll());
		// response.setContentType("text/html;charset=UTF-8");
		// try {
		// PrintWriter pw = response.getWriter();
		// if (json != null && !json.isEmpty()) {
		// pw.print(json.toString());
		// }
		// pw.flush();
		// pw.close();
		// } catch (IOException e) {
		// }
	}

	/**
	 * @author zfc
	 * 
	 *         得到mac的所有服务
	 * 
	 * @param request
	 * @param response
	 */
//	@RequestMapping(value = "/listByMac", method = RequestMethod.GET)
//	public void listByMac(HttpServletRequest request,
//			HttpServletResponse response) {
//		String mac = request.getParameter("mac");
//		if (mac == null || "".equals(mac))
//			return;
//
//		// Map<String, Map<String, ServiceInfo>> map = null;
//		// map = (Map<String, Map<String, ServiceInfo>>)
//		// deviceCache.verifyRead(request.getParameter("mac"));
//		Map<String, ServiceInfo> map = deviceCache.verifyRead(mac);
//
//		JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
//		response.setContentType("text/html;charset=UTF-8");
//		try {
//			PrintWriter pw = response.getWriter();
//			if (json != null && !json.isEmpty()) {
//				pw.print(json.toString());
//			}
//			pw.flush();
//			pw.close();
//		} catch (IOException e) {
//		}

//		// Map<String, Map<String, ServiceInfo>> map = null;
//		// if (request.getParameter("mac") != null) {
//		// map = (Map<String, Map<String, ServiceInfo>>) deviceCache
//		// .verifyRead(request.getParameter("mac"));
//		//
//		// JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
//		// response.setContentType("text/html;charset=UTF-8");
//		// try {
//		// PrintWriter pw = response.getWriter();
//		// if (json != null && !json.isEmpty()) {
//		// pw.print(json.toString());
//		// }
//		// pw.flush();
//		// pw.close();
//		// } catch (IOException e) {
//		// }
//		// }
//	}
	
	

	/**
	 * @author zfc
	 * 
	 *         处理设备的服务注册
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/registering", method = RequestMethod.GET)
	public void registering(HttpServletRequest request,
			HttpServletResponse response) {
		
		String serviceinfo = request.getParameter("serviceinfo");
		//LOG.info("registering..."+serviceinfo);
		if (serviceinfo == null || "".equals(serviceinfo))
			return;
		ServiceInfo serviceInfo = formatUtil(request
				.getParameter("serviceinfo"));
		//LOG.info("formatUtil..."+serviceInfo.toString());
		if (serviceInfo == null || "".equals(serviceInfo.getMac())
				|| "".equals(serviceInfo.getId()))
			return;
		// System.out.println(serviceInfo.getMac());
		//minitor();
		// Map<String,
		// Map<String,ServiceInfo>>macs=deviceCache.verifyRead(serviceInfo.getMac());
		//对注册上的机制进行拦截,处理特殊的需求
		//LOG.info("serviceInfo ..."+serviceInfo.toString());
		DeviceRegisteringService.DeviceRegisterFilter(serviceInfo);
		Map<String, ServiceInfo> macs = deviceCache.verifyRead(serviceInfo
				.getMac());
		//LOG.info("macs ..."+macs);
		if (macs != null) {
			if (macs.get(serviceInfo.getId()) == null) {
				macs.put(serviceInfo.getId(), serviceInfo);
			} else {
				macs.get(serviceInfo.getId()).setDate(serviceInfo.getDate());
			}
		} else {

//			System.out.print("服务注册前:" + serviceInfo.toString());
//			minitor();
			macs = new HashMap<String, ServiceInfo>();
			macs.put(serviceInfo.getId(), serviceInfo);
			deviceCache.verifyWrite(serviceInfo.getMac(), macs);
			//LOG.info("verifyWrite ..."+serviceInfo.getMac());
			// macs.get(serviceInfo.getId()).setDate(serviceInfo.getDate());
		}
		deviceCache.verifyWrite(serviceInfo.getMac(), macs);

//		System.out.print("服务注册后:" + serviceInfo.toString());
//		minitor();
		Map<String, String> map = new HashMap<String, String>();
		map.put("info", request.getParameter("serviceinfo") + " 该服务已经注册。。。");
		JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
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
		// System.out.print("registering...");
		// ServiceInfo serviceInfo = formatUtil(request
		// .getParameter("serviceinfo"));
		// if (serviceInfo == null || "".equals(serviceInfo.getMac())
		// || "".equals(serviceInfo.getId()))
		// return;
		// System.out.println(serviceInfo.getMac());
		// minitor();
		// Map<String, ServiceInfo> m;
		// Map<String, Map<String, ServiceInfo>> macs = (HashMap<String,
		// Map<String, ServiceInfo>>) deviceCache
		// .verifyRead(serviceInfo.getMac());
		// if (macs != null) {
		// macs.get(serviceInfo.getMac())
		// .put(serviceInfo.getId(), serviceInfo);
		// } else {
		// macs = new HashMap<String, Map<String, ServiceInfo>>();
		// m = new HashMap<String, ServiceInfo>();
		// m.put(serviceInfo.getId(), serviceInfo);
		// macs.put(serviceInfo.getMac(), m);
		// m = null;
		// }
		// List<ServiceInfo> list= new ArrayList<ServiceInfo>();
		// list.add(serviceInfo);
		// deviceCache.verifyWrite(serviceInfo.getMac(), list);
		//
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("info", request.getParameter("serviceinfo") + " 该服务已经注册。。。");
		// JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
		// response.setContentType("text/html;charset=UTF-8");
		// try {
		// PrintWriter pw = response.getWriter();
		// if (json != null && !json.isEmpty()) {
		// pw.print(json.toString());
		// }
		// pw.flush();
		// pw.close();
		// } catch (IOException e) {
		// }
	}

	/**
	 * @author zfc
	 * 
	 *         处理设备的服务注销
	 * @param request
	 * @param response
	 */
	// @SuppressWarnings("unchecked")
	// @RequestMapping(value = "/unRegistering", method = RequestMethod.GET)
	// public void unRegistering(HttpServletRequest request,
	// HttpServletResponse response) {
	// // ServiceInfo serviceInfo = formatUtil(request
	// // .getParameter("serviceinfo"));
	// // Map<String, Map<String, ServiceInfo>> map2 = (Map<String, Map<String,
	// // ServiceInfo>>) deviceCache
	// // .verifyRead(serviceInfo.getMac());
	// // map2.get(serviceInfo.getMac()).remove(serviceInfo.getId());
	//
	// String mac = request.getParameter("mac");
	// String id = request.getParameter("id");
	// Map<String, Map<String, ServiceInfo>> map22 = (Map<String, Map<String,
	// ServiceInfo>>) deviceCache
	// .verifyRead(mac);
	// map22.get(mac).remove(id);
	//
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("info", request.getParameter("serviceinfo") + " ： 该服务已注销。。。");
	// JSONObject json = CosJSONUtil.toJsonObjectFromMap(map);
	// response.setContentType("text/html;charset=UTF-8");
	// try {
	// PrintWriter pw = response.getWriter();
	// if (json != null && !json.isEmpty()) {
	// pw.print(json.toString());
	// }
	// pw.flush();
	// pw.close();
	// } catch (IOException e) {
	// }
	// }

	
	/**
	 * @author zfc
	 * 
	 *         处理设备端发送的心跳数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
	public void heartbeat(HttpServletRequest request,
			HttpServletResponse response) {
		String serviceinfo = request.getParameter("serviceinfo");
		
		//LOG.info("serviceInfo==>"+request.getRemoteHost()+"...."+serviceinfo);
		if (serviceinfo == null || "".equals(serviceinfo))
			return;
		//LOG.info("没有retrun");
		Map<String, String> retmap = new HashMap<String, String>();
		try {
			ServiceInfo serviceInfo = formatUtil(serviceinfo);
			//LOG.info("formatUtil success");
			Map<String, ServiceInfo> map = deviceCache.verifyRead(serviceInfo
					.getMac());
			//LOG.info("verifyRead success,,"+serviceInfo.getMac());
			//LOG.info("heartbeat=serviceInfo.getMac==>"+serviceInfo.getMac());
			//空方法
			//minitorheartbeat(map, serviceInfo);
			if (map == null)
				return;
			//LOG.info("map is not null"+serviceInfo.getMac());

			map.get(serviceInfo.getId()).setDate(new Date().getTime());
			retmap.put("info", request.getParameter("serviceinfo")
						+ " 发送的心跳数据。。。");
		} catch (Exception e) {
			//LOG.info("心跳失败"+e.getMessage());
			retmap.put("info", " 失败! 发送的心跳数据。。。");
			// 心跳失败
		}

		JSONObject json = CosJSONUtil.toJsonObjectFromMap(retmap);
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
		// //System.out.println(request.getParameter("serviceinfo"));
		// Map<String, String> retmap = new HashMap<String, String>();
		// try {
		// ServiceInfo serviceInfo = formatUtil(request
		// .getParameter("serviceinfo"));
		// Map<String, Map<String, ServiceInfo>> map = (Map<String, Map<String,
		// ServiceInfo>>) deviceCache
		// .verifyRead(serviceInfo.getMac());
		// Date d=new Date();
		// map.get(serviceInfo.getMac()).get(serviceInfo.getId()).setDate(d.getTime());
		// retmap.put("info", request.getParameter("serviceinfo") +
		// " 发送的心跳数据。。。");
		// } catch (Exception e) {
		// retmap.put("info"," 失败! 发送的心跳数据。。。");
		// // 心跳失败
		// }
		//
		// JSONObject json = CosJSONUtil.toJsonObjectFromMap(retmap);
		// response.setContentType("text/html;charset=UTF-8");
		// try {
		// PrintWriter pw = response.getWriter();
		// if (json != null && !json.isEmpty()) {
		// pw.print(json.toString());
		// }
		// pw.flush();
		// pw.close();
		// } catch (IOException e) {
		// }
	}

	public ServiceInfo formatUtil(String name) {
		String[] str = name.trim().split("_");
		ServiceInfo serviceInfo = new ServiceInfo();
		if ((str[1] == null || "".equals(str[1].trim()))
				|| (str[2] == null || "".equals(str[2].trim()))
				|| (str[3] == null || "".equals(str[3].trim())))
			return null;
		serviceInfo.setIp(str[0].trim());
		serviceInfo.setMac(str[1].trim());
		serviceInfo.setType(str[2].trim());
		serviceInfo.setId(str[3].trim());
		if (str.length == 5)
			serviceInfo.setUrl(str[4].trim());
		serviceInfo.setDate(new Date().getTime());
		return serviceInfo;
	}

	public Map<String, Map<String, ServiceInfo>> getAll() {
		// Map<String, Map<String, Map<String, ServiceInfo>>> all = new
		// HashMap<String, Map<String, Map<String, ServiceInfo>>>();
		// List<String> list = (List<String>) deviceCache.verifyReadAllKeys();
		// Map<String, Map<String, ServiceInfo>> map = null;
		// for (String string : list) {
		// if (string != null && !"".equals(string)) {
		// map = (Map<String, Map<String, ServiceInfo>>) deviceCache
		// .verifyRead(string);
		// all.put(string, map);
		// }
		// }
		return deviceCache.verifyReadAll();
	}

	/**
	 * @author zfc
	 * 
	 *         处理设备的心跳数据 手动执行 检测没有心跳脉冲注销服务
	 * @param request
	 * @param response
	 */
	// @RequestMapping(value = "/flushAll", method = RequestMethod.GET)
	// public void flushAll() {
	// execute();
	// }

	// @SuppressWarnings("unchecked")
	// public void execute() {
	// Iterator<String> idIterator;
	// Iterator<String> macIterator;
	// Map<String, ServiceInfo> idMap = null;
	// Map<String, Map<String, ServiceInfo>> macMap = null;
	// ServiceInfo serviceInfo;
	// List<String> macs = (List<String>) deviceCache.verifyReadAllKeys();
	//
	// for (String string : macs) {
	// if (string != null && !"".equals(string)) {
	//
	// macMap = (Map<String, Map<String, ServiceInfo>>) deviceCache
	// .verifyRead(string);
	// macIterator = macMap.keySet().iterator();
	// while (macIterator.hasNext()) {
	// idMap = macMap.get(macIterator.next());
	// if (idMap.keySet().isEmpty())
	// deviceCache.verifyDel(string);
	// else {
	// idIterator = idMap.keySet().iterator();
	// while (idIterator.hasNext()) {
	// String str = idIterator.next();
	// serviceInfo = (ServiceInfo) idMap.get(str);
	//
	// // System.out.println(serviceInfo.getMac()
	// // + serviceInfo.getId());
	//
	// try {
	// // if (DateUtils.timeDifference(new Date(),
	// // serviceInfo.getDate()) > 1000) {
	// if (new Date().getTime()-serviceInfo.getDate() > 1000) {
	// idMap.remove(str);
	// // if (idMap.keySet().isEmpty()) {
	// // deviceCache.verifyDel(string);
	// // } else {
	// // deviceCache.verifyWrite(
	// // serviceInfo.getMac(), macMap);
	// // }
	// }
	//
	// idIterator = idMap.keySet().iterator();
	//
	// } catch (Exception e) {
	// continue;
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// }
	//
	//
	// /**
	// * @author zfc
	// *
	// *
	// * @param request
	// * @param response
	// */
	// @RequestMapping(value = "/presetSave", method = RequestMethod.GET)
	// public void presetSave(HttpServletRequest request,
	// HttpServletResponse response) {
	// String id = request.getParameter("id");
	// if (id != null && !"".equals(id)) {
	// Preset.save(id);
	// }
	// // System.out.println(id);
	// }
	//
	// /**
	// * @author zfc
	// *
	// *
	// * @param request
	// * @param response
	// */
	// @RequestMapping(value = "/presetCall", method = RequestMethod.GET)
	// public void presetCall(HttpServletRequest request,
	// HttpServletResponse response) {
	// String id = request.getParameter("id");
	// if (id != null && !"".equals(id)) {
	// Preset.call(id);
	// }
	// // System.out.println(id);
	// }
	//
	// /**
	// * @author zfc
	// *
	// *
	// * @param request
	// * @param response
	// */
	// @RequestMapping(value = "/presetClear", method = RequestMethod.GET)
	// public void presetClear(HttpServletRequest request,
	// HttpServletResponse response) {
	// String id = request.getParameter("id");
	// if (id != null && !"".equals(id)) {
	// Preset.clear(id);
	// }
	// // System.out.println(id);
	// }

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
//	@RequestMapping(value = "/mouseTrace", method = RequestMethod.GET)
//	public void MouseTrace(HttpServletRequest request,
//			HttpServletResponse response) {
//		String mac = request.getParameter("mac");
//		if (mac == null || "".equals(mac))
//			return;
//		String x = request.getParameter("x");
//		String y = request.getParameter("y");
//		if (x != null && !"".equals(x) && y != null && !"".equals(y)) {
//			com.daobotai.MouseTrace.mouse_trace(mac, x, y);
//		}
//		// System.out.println(x+y);
//	}

	/**
	 * @author zfc
	 * 
	 *         http://ip:port/deviceService/regHost?mac=<真实mac>&ip=<真实ip>&
	 *         hosttype=<真实hosttype>
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/regHost", method = RequestMethod.GET)
	public void regHost(HttpServletRequest request, HttpServletResponse response) {
		String ret = "ok";
		String mac = request.getParameter("mac");
		String ip = request.getParameter("ip");
		String hosttype = request.getParameter("hosttype");

		try {
			LivingService.controlDirection(mac, "RecordCmd=StopRecord");
		} catch (Exception e) {
		}
		try {
			RegServicePool.removeService(mac);
		} catch (Exception e) {
		}
		// if (mac != null && !"".equals(mac) && ip != null && !"".equals(ip)
		// && hosttype != null && !"".equals(hosttype)) {
		// RegHostPool.addHost(mac, ip + "&&" + hosttype);
		// if ((ip + "&&" + hosttype).equals(RegHostPool.getHost(mac)))
		// ret = "ok";
		// }
		//
		// RegHostPool.forch();

		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (ret != null) {
				pw.print(ret.toString());
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}

	}

	public void minitor() {
//		System.out.println("///////////////////////////////////////////////");
//		System.out.println(CosJSONUtil.toJsonObjectFromMap(getAll()));
//		System.out.println("///////////////////////////////////////////////");
	}

	public static void minitorheartbeat(Map<String, ServiceInfo> map,
			ServiceInfo serviceinfo) {
		// System.out.println("------------------------------------------");
		// System.out.println("before heartbeat :"+CosJSONUtil.toJsonObjectFromMap(map));
		// System.out.println("arter heartbeat :"+serviceinfo.toString());
		// System.out.println("------------------------------------------");
	}

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deviceOnLine", method = RequestMethod.GET)
	public void deviceOnLine(HttpServletRequest request,
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
			/** 取缓存状态开始 */
			string = "";
			try {
				string = DeviceStatusPool.getDeviceStatus(mac);
			} catch (Exception e) {
			}
			if (string != null && !"".equals(string)) {
				map = new HashMap<String, String>();
				map.put(mac, string);
				list.add(map);
			}
			/** 取缓存状态结束 */
//			try {
//				string = GetInfoService.getDiscStatus(mac);
//			} catch (Exception e) {
//				string = "";
//			}
//			if (string != null && !"".equals(string)) {
//				map = new HashMap<String, String>();
//				map.put(mac, "1");
//				list.add(map);
//			}
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
	@RequestMapping(value = "/deviceOnLinePost", method = RequestMethod.GET)
	public void deviceOnLinePost(HttpServletRequest request,
			HttpServletResponse response) {
		String macs = request.getParameter("macs");
		if (macs == null || "".equals(macs))
			return;
		JSONArray data = JSONArray.fromObject(macs);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> ret;
		Map<String, ServiceInfo> map;
		String str;
		for (int i = 0; i < data.size(); i++) {
			ret = new HashMap<String, String>();
			str = data.get(i).toString();
			try {
				map = deviceCache.verifyRead(str);
				if (map != null && !map.isEmpty()) {
					ret.put(str, "1");
				} else {
					ret.put(str, "0");
				}
				list.add(ret);
			} catch (Exception e) {
				ret.put(str, "0");
				list.add(ret);
				continue;
			}
		}
		str = JSONArray.fromObject(list).toString();
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			if (str != null && !"".equals(str)) {
				pw.print(str);
			}
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
}
