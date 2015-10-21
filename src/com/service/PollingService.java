package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.common.AppConstants;
import com.util.HttpExec;
import com.util.JsonUtil;
import com.vo.PollingInfo;
import com.vo.RtmpInfo;

@Service
public class PollingService {
	
	public String getCutPic(HttpServletRequest req){
		List<PollingInfo> pollingList = new ArrayList<PollingInfo>();
		try {
			pollingList = getFlow(req);
			if(pollingList.size()==0)
				return null;
			String data = JsonUtil.toJson(pollingList);
			//执行截图的url
			String url = "http://"+req.getLocalAddr()+AppConstants.CUT_IMAGE_URL;
			String result = HttpExec.doPostWithJSON(url, data);
//			System.out.println("-----------------url:"+url+",data:"+data+",result:"+result);
			return result;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getVideo(HttpServletRequest req){
		Map<String,Object> data = JsonUtil.jsonToObject(req, Map.class);
		List<String> seats = (List<String>) data.get("seats");
		List<Map<String,Object>> seatsList = new ArrayList<Map<String,Object>>();
	    Map<String,Object> macInfo =  (Map<String, Object>) data.get("macInfo");
	    String rtmp_repeater = null;
	    String mac = (String) macInfo.get("mac");
		//String json = LivingService.startLiving(mac);
	    String json = LivingService.StartPullLiving(mac,seats);
	    List<Map<String,Object>> livingFlows=null;
	    if ("rtstperror".equals(json) ||"tranerror".equals(json) ||"exception".equals(json) || "FFMPEG_ERROR".equals(json)){
	    	rtmp_repeater=json;
	    } else {
	    	livingFlows = JsonUtil.jsonToObject(json, List.class);
	    }
		for (String seat : seats) {
			if(livingFlows != null){
				rtmp_repeater = null;
				for (Map<String, Object> map : livingFlows) {
					rtmp_repeater = (String) map.get(seat);
					if(rtmp_repeater != null){
						break;
					}
				}
			}
			Map<String,Object> seatMap = new HashMap<String, Object>();
			seatMap.put("seat", seat);
			seatMap.put("rtmp", rtmp_repeater);
			seatMap.put("mac", mac);
			seatsList.add(seatMap);
		}
		return JsonUtil.toJson(seatsList);
	}
	private List<RtmpInfo> getRtmps(String json,List<String> seats,String mac){
		List<Map<String,Object>> livingFlows = JsonUtil.jsonToObject(json, List.class);
		List<RtmpInfo> list = new ArrayList<RtmpInfo>();
		//返回结果
		for (Map<String,Object> map : livingFlows) {
			for (String seat : seats) {
				String rtmp_repeater = (String) map.get(seat);
				if(rtmp_repeater!=null){
					RtmpInfo rtmp = new RtmpInfo();
					rtmp.setRtmp_repeater(rtmp_repeater);
					rtmp.setRtmp_filename(seat);
					String uid = mac+seat;
					rtmp.setUid(uid);
					list.add(rtmp);
				}
			}
		}
		return list;
	}
	//获取流数据
	private List<PollingInfo> getFlow(HttpServletRequest req){
		List<PollingInfo> pollingList = new ArrayList<PollingInfo>();
			Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
			//机位
			List<String> seats = (List<String>) map.get("seats");
			//mac列表
			List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("macList");
			for (Map<String,Object> info: list) {
				PollingInfo pollingInfo = new PollingInfo();
				String mac = (String) info.get("mac");
				String class_id = (String) info.get("innerid");
				String json = LivingService.startLiving(mac);
				if(json== null)
					continue;
				List<RtmpInfo> rtmps = getRtmps(json,seats,mac);
				
				pollingInfo.setClass_id(class_id);
				pollingInfo.setEndtime("2050-02-01 13:12:30");
				pollingInfo.setClass_filename(mac);
				pollingInfo.setImage_path(AppConstants.IMAGE_PATH);
				pollingInfo.setServerip(req.getLocalAddr());
				pollingInfo.setRound_url(rtmps);
				
				pollingList.add(pollingInfo);
			}
			return pollingList;
	}
}
