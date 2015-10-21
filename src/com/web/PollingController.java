package com.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.PollingService;

/**
 * 轮询
 * @author gly
 *
 */
@Controller
@RequestMapping(value = "/polling")
public class PollingController {
	@Resource
	PollingService pollingService;
	/**
	 *生成rtmp流并发送给第三方用于截图
	 * @param req
	 * @return 
	 */
	@RequestMapping(value = "pictureType",method = RequestMethod.POST)
	@ResponseBody
	public String cutPic(HttpServletRequest req){
		return pollingService.getCutPic(req);
	}
	/**
	 *生成rtmp流并
	 * @param req
	 * @return 
	 */
	@RequestMapping(value = "flowType",method = RequestMethod.POST)
	@ResponseBody
	public String video(HttpServletRequest req){
		return pollingService.getVideo(req);
	}
}
