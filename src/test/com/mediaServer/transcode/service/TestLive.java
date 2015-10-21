//package test.com.mediaServer.transcode.service;
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
//import com.service.LivingService;
//import com.util.HttpExec;
//
//public class TestLive {
//
//	public static void main(String[] args) throws InterruptedException,
//			Exception {
//		// HttpExec.sendGet("http://localhost:8080/deviceService/help", "");
////		HttpExec.sendPost("http://localhost:8080/deviceService/ptzHelpPost",
////				"port=xxx");
////		sendPost();
////		exeTestLive();
//		LivingService.startAndSet("zfc123");
//		System.exit(0);
//	}
//
//	public static void sendPost() {
//
//		String sr =HttpExec.sendPost("http://localhost:8080/deviceService/ptzHelpPost",
//						"port=456");
//		System.out.println(sr);
//	}
//
//	public static void exeTestLive() throws InterruptedException {
////		String url = "http://192.168.12.103:8899/transcode/transinfo";
////		String url=Url.getMediaCodeIpUrl() +"/transcode/transinfo";
////		String url="http://192.168.12.211:50501/transcode/transinfo";
//		String url = "http://192.168.12.211:50501/transcode/transinfo";
//		CodeLiveVo vo = new CodeLiveVo();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("rtmp_repeater", "rtmp://192.168.12.103/zonekey/test");
//		map.put("uid", "xx-xxxx-xxxx-xxx");
//		list.add(map);
////		vo.setRate("3000k");
////		vo.setFramerate("25");
////		vo.setResolution("1920*1080");
////		vo.setSamprate("24000");
//		vo.setTranscodingurl(list);
////		vo.setRtmp_repeater("rtmp://192.168.12.103/zonekey/test");
////		vo.setUid("xx-xxxx-xxxx-xxx");
////		vo.setChannel("2");
////		vo.setAudiorate("96");
//		vo.setGrade("1");
//		vo.setEndtime("2014-12-15 13:12:30");;
//
//		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
//		l.add(vo.toMap());
//
//		//转码
//		String code = CodeLiveService.start(url, CodeLiveVo.toJson(l).toString());
//		RetCodeLiveVo zz=RetCodeLiveVo.ToRetCodeLiveVo(code);
//		System.out.println(JSONArray.fromObject(zz.getContent()).toString());
//	}
//}
