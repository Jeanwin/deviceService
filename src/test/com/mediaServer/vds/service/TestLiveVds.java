package test.com.mediaServer.vds.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.mediaServer.transcode.service.CodeLiveService;
import com.mediaServer.transcode.vo.CodeLiveVo;
import com.mediaServer.transcode.vo.RetCodeLiveVo;
import com.mediaServer.vds.service.VdsLiveService;
import com.mediaServer.vds.vo.RetVdsLiveVo;

public class TestLiveVds {

	public static void main(String[] args) throws InterruptedException,
			Exception {
		// exeTestLive();
		GetLiving();
		//exeTestLiveAll();
		System.exit(0);
	}

	public void sendtest() {
		String param;
		param="IP=192.168.12.217&";
		param=param+"PORT=50601&";
		//[{"endtime":"2015-12-15 13:12:30","uid":"xx-xxxx-xxxx-xxx","rate":"3000k","rtmp_repeater":"rtsp://192.168.12.238:8555/00096F24A0AF_vga","audiorate":"96","group_id":"xx-xxx-xxxx","grade":"1","resolution":"1920*1080","channel":"2","samprate":"24000","framerate":"25"}]
		CodeLiveService.sendPost("http://192.168.12.46:8080/getRtmpUrls", param);
	}
	
    public static void GetLiving() throws InterruptedException  {
		String codeUrl = "http://192.168.12.217:50601/transcode/transinfo";
		CodeLiveVo vo = new CodeLiveVo();	
		vo.setRate("3000k");
		vo.setFramerate("25");
		vo.setResolution("1920*1080");
		vo.setSamprate("24000");
		//vo.setTranscodingurl(list);
		vo.setRtmp_repeater("rtsp://192.168.13.200:8554/00096F24A0D1_students");
		vo.setUid("xx-xxxx-xxxx-xxx");
		vo.setChannel("2");
		vo.setAudiorate("96");
		vo.setGrade("1");
		vo.setEndtime("2015-12-15 13:12:30");
		vo.setGroup_id("xx-xxx-xxxx");
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		l.add(vo.toMap());
		
		
		System.out.println(CodeLiveVo.toJson(l).toString());

		//转码
		String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l).toString());
		System.out.println("-------------------转码返回的结果-------------------");
		System.out.println(code);
	}	
	
	public static RetVdsLiveVo exeTestLiveAll() throws InterruptedException {
		String codeUrl = "http://192.168.12.217:50501/transcode/transinfo";
		CodeLiveVo vo = new CodeLiveVo();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("rtmp_repeater", "rtmp://192.168.13.117:51935/zonekey/00096f24a0aa_teacher");
//		map.put("uid", "xx-xxxx-xxxx-xxx");
//		list.add(map);
		vo.setRate("3000k");
		vo.setFramerate("25");
		vo.setResolution("1920*1080");
		vo.setSamprate("24000");
		//vo.setTranscodingurl(list);
		vo.setRtmp_repeater("rtsp://192.168.13.200:8554/00096F24A0D1_students");
		vo.setUid("xx-xxxx-xxxx-xxx");
		vo.setChannel("2");
		vo.setAudiorate("96");
		vo.setGrade("1");
		vo.setEndtime("2015-12-15 13:12:30");
		vo.setGroup_id("xx-xxx-xxxx");
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		l.add(vo.toMap());

//		//转码
//		String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l)
//				.toString());
//		String content = CosJSONUtil.json2Map(CosJSONUtil.string2json(code))
//				.get("content");
//		//分发
//		String url = "http://192.168.12.117:50201/vds/web";
//		String vds = HttpExec.doPostWithJSON(url, content);
//		vds = VdsLiveService.start(url, content);
//		
//		System.out.println("--------------------------------------");
//		System.out.println(RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent().get(0)
//				.getRtmps().get(0).getRtmp_distributer());
		
		
		
		System.out.println(CodeLiveVo.toJson(l).toString());

		//转码
		String code = CodeLiveService.start(codeUrl, CodeLiveVo.toJson(l).toString());
		System.out.println("-------------------转码返回的结果-------------------");
		System.out.println(code);
		RetCodeLiveVo retCodeLiveVo=RetCodeLiveVo.ToRetCodeLiveVo(code);
		if(!"0".equals(retCodeLiveVo.getResponse_code()))return null;
		//分发
//		String url = "http://192.168.12.117:50201/vds/web";
//		System.out.println("-------------------分发发送的数据-------------------");
//		System.out.println(JSONArray.fromObject(retCodeLiveVo.getContent()).toString());
//		String vds = VdsLiveService.start(url, JSONArray.fromObject(retCodeLiveVo.getContent()).toString());
//		System.out.println("-------------------分发返回的结果-------------------");
//		System.out.println(vds);
		//////////System.out.println(RetVdsLiveVo.ToRetVdsLiveVo(vds).getContent().get(0).getRtmps().get(0).getRtmp_distributer());
		return null;
	}

}
