//package test.com.service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//
//import com.mediaServer.transcode.service.CodeLiveService;
//import com.mediaServer.transcode.vo.CodeLiveVo;
//import com.mediaServer.transcode.vo.RetCodeLiveVo;
//import com.mediaServer.vds.service.VdsLiveService;
//import com.mediaServer.vds.vo.RetVdsLiveVo;
//import com.service.LivingService;
//import com.util.livingPool;
//import com.vo.RetLivingVo;
//
//public class TestLiveing {
//
//	public static void main(String[] args) {
//		living();
//	}
//
//	public static void living() {
//		String mac = "";
//		String vds = "";
//		try {
//			// 直播
//			String s = LivingService.controlDirection("RecordCmd=RTMPLiving");
//			RetLivingVo retLivingVo = RetLivingVo.ToRetLivingVo(s);
//			if (!"ok".equals(retLivingVo.getResult()))
//				return;
//			// 转码
//			String codeUrl = "http://192.168.12.211:50501/transcode/transinfo";
//			CodeLiveVo vo = new CodeLiveVo();
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("rtmp_repeater", retLivingVo.getInfo());
//			map.put("uid", "xx-xxxx-xxxx-xxx");
//			list.add(map);
//			vo.setTranscodingurl(list);
//			vo.setGrade("1");
//			List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
//			l.add(vo.toMap());
//			// 转码
//			String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l)
//					.toString());
//			RetCodeLiveVo retCodeLiveVo = RetCodeLiveVo.ToRetCodeLiveVo(code);
//			if (!"0".equals(retCodeLiveVo.getResponse_code()))
//				return;
//			// 分发
//			String url = "http://192.168.12.117:50201/vds/web";
//			vds = VdsLiveService
//					.start(url, JSONArray
//							.fromObject(retCodeLiveVo.getContent()).toString());
//
//			System.out.println("--------------------------------------");
//			System.out.println(RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent()
//					.get(0).getRtmps().get(0).getRtmp_distributer());
//
//		} catch (Exception e) {
//			//直播失败
//			return ;
//		}
//		livingPool.getInstance().addMessage(mac,RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent().get(0).getRtmps()
//						.get(0).getRtmp_distributer());
//		if(livingPool.getInstance().getMessage(mac)==null||"".equals(livingPool.getInstance().getMessage(mac)))
//			//直播失败
//			vds="直播失败";
//	}
//}
