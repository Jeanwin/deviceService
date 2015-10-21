package com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.ptz.Preset;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class PersetController {

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/perset", method = RequestMethod.GET)
	public void perset(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String cardInfo = request.getParameter("cardInfo");
		if (cardInfo == null || "".equals(cardInfo))
		return;
		String para = request.getParameter("para");
		if (para == null || "".equals(para))
		return;
 		if ("-1".equals(para)){
			//all
			return;
		}
		String mem = request.getParameter("mem");
		String result= null;
		if(mem == null || "".equals(mem)){
			//Preset.call(mac.toLowerCase(),cardInfo,para);
			result= Preset.call(mac,cardInfo,para);
		}else if("0".equals(mem)){
			result= Preset.save(mac,cardInfo,para);
			//Preset.save(mac.toLowerCase(),cardInfo,para);
		}
		System.out.println("perset == >"+result);

	}

}
