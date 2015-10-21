package com.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.service.ChangeWindows;

/**
 * @author zfc
 * 
 * 
 */
@Controller
public class ChangeWindowsController {

	// @Autowired
	// private ChangeWindows changeWindows;

	/**
	 * 特效
	 * 画中画
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/changeWindows", method = RequestMethod.GET)
	public void changeWindows(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		String port = request.getParameter("port");
		String type = request.getParameter("type");
		if ((port == null || "".equals(port) )|| (type == null || "".equals(type)))
			return;

		ChangeWindows.execute(mac,port, type);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/picInPic", method = RequestMethod.GET)
	public void PicInPic(HttpServletRequest request,
			HttpServletResponse response) {
		String mac = request.getParameter("mac");
		if (mac == null || "".equals(mac))
		return;
		//关闭小画面
		String para = request.getParameter("para");
		if ("close".equals(para)) {
			com.service.PicInPic.del(mac);
			return;
		}
		//更换主副画面
		if ("change".equals(para)) {
			com.service.PicInPic.ChangePicInPic(mac);
			return;
		}

		String PicInPicMode = request.getParameter("picInPicMode");
		String port = request.getParameter("port");
		
		String currPos = request.getParameter("currPos");
		String pos = request.getParameter("pos");
		String size = request.getParameter("size");
		if (port == null || "".equals(port))return;
		if ("000".equals(PicInPicMode.trim())) {
			//000 （全景）模式
			com.service.PicInPic.execute(mac,"SetPnp", null, port, null, null, null);
		} else {
			if (PicInPicMode == null || "".equals(PicInPicMode))return;
			if( "Insert".equals(PicInPicMode)){
				com.service.PicInPic.execute(mac,null, "Insert", port, pos, size,currPos);
			}else{
				com.service.PicInPic.execute(mac,null, PicInPicMode, port, null, null,null);
			}
		}
		// com.service.PicInPic.execute(RecordCmd, PicInPicMode, port, Pos,
		// Size, CurrPos);

		// String port = request.getParameter("port");
		// String type = request.getParameter("type");
		// if (port == null && "".equals(port) && type == null
		// && "".equals(type))
		// return;
		//
		//
		// ChangeWindows.execute(port,type);

	}

}
