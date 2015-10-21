package com.service;

import java.util.HashMap;
import java.util.Map;

import com.util.CosJSONUtil;
import com.util.JsonUtil;
import com.vo.PreViewInfo;
import com.vo.QueryRAllInfo;

/**
 * @author zfc
 * 
 */
public class GetInfoService {

	/**
	 * 
	 * 10008/lving/cmd?BroadCastCmd=GetDeviceIP
	 * 
	 * 10007/recording/cmd?MetaInfoCmd=GetMac
	 * 
	 * 10007/recording/cmd?RecordCmd=QueryDiskStatus
	 * 
	 * 10007/recording/cmd?RecordCmd=QueryRecordTime
	 * 
	 * 10007/recording/cmd?RecordCmd=QuerySystemTime
	 * 
	 * @param RecordCmd
	 *            SetPicInPicMode
	 * @param PicInPicMode
	 *            Insert/LeftRight/Tile/Pnp
	 * @param port
	 * @param Pos
	 * @param Size
	 * @param CurrPos
	 */

	public static String getRecordStatus(String mac) {
		QueryRAllInfo status = getRecordStatusFromCreateQueryRAllInfo(
				RecordingService.execute(mac, "RecordCmd=QueryRAllInfo"));
		if(status == null){
			return null;
		}
		return status.getRecordStatus();
	}

	/**
	 * 
	 * @author zfc get device live status
	 * @param mac
	 * @return
	 */
	public static String getLiveStatus(String mac) {
		QueryRAllInfo status = getRecordStatusFromCreateQueryRAllInfo(RecordingService.execute(mac, "RecordCmd=QueryRAllInfo"));
		if(status == null){
			return null;
		}
		return status.getLivingStatus();
	}

	/**
	 * 
	 * @author zfc get vds live status vds live status is real live status
	 * @param mac
	 * @return
	 */
	public static String getVdsLiveStatus(String mac) {
		return LivingService.getVdsLiveStatus(mac);
	}

	public static String getDiscStatus(String mac) {
		String str = CosJSONUtil
				.parseJSON2Map(
						RecordingService.execute(mac,
								"RecordCmd=QueryDiskStatus")).get("info")
				.toString();
		if (!"".equals(str)) {
			str = str.split("&")[0];
		}
		return str;
	}

	// 导播台推送的信息 
	public static String getConsoleOperationInfo(String mac) {
		String ret;
		try {
			QueryRAllInfo queryRAllInfo = getRecordStatusFromCreateQueryRAllInfo(RecordingService
					.QueryRAllInfo(mac, "RecordCmd=QueryRAllInfo"));
			String str = CosJSONUtil
					.parseJSON2Map(
							RecordingService.execute(mac,
									"RecordCmd=QueryDiskStatus")).get("info")
					.toString();
			if (!"".equals(str))
				str = str.split("&")[0];
			ret = queryRAllInfo.getLivingStatus()
					+ ",,"
					+queryRAllInfo.getDeviceType()
					+ ",,"
					+ CosJSONUtil.parseJSON2Map(
							RecordingService.execute(mac,
									"RecordCmd=QueryRecordTime")).get("info")
					+ ",," + queryRAllInfo.getTrackStatus() + ",," + str;

		} catch (Exception e) {
			return "";
		}
		return ret;
	}

	public static void main(String[] args) {
//		getVdsLiveStatus("00e04c680002");
		// System.out.println(getRecordStatus());
		// System.out.println(getConsoleOperationInfo());
		// 导播台
		// System.out.println("record : "+getRecordStatus());
		// System.out.println("live : "+getLiveStatus());
		// System.out.println("disc : "+RecordingService.execute("RecordCmd=QueryDiskStatus"));
		// System.out.println(getConsoleOperationInfo());
		// System.out.println("QueryRecordTime : "+RecordingService.execute("RecordCmd=QueryRecordTime"));

		// System.out.print("ip : ");
		// getIp();
		// System.out.print("mac : ");
		// execute("MetaInfoCmd=GetMac");
		// System.out.print("disc : ");
		// System.out.print(execute("RecordCmd=QueryDiskStatus"));
		// execute("RecordCmd=QueryDiskStatus");
		// System.out.print("recordTime : ");
		// execute("RecordCmd=QueryRecordTime");
		// System.out.print("systemTime : ");
		// execute("RecordCmd=QuerySystemTime");
		// System.out.print("allInfo  : ");
		// execute("RecordCmd=QueryRAllInfo");
		//
		// System.out.println(getRecordStatus());
		// System.out.print(executeWithReturn("MetaInfoCmd=GetMac"));

		// System.out.println(getRecordStatus());
		// System.out.println(getLiveStatus());
		// System.out.println(execute("RecordCmd=QueryDiskStatus"));

		// //------------------Preview
		// createPreViewInfo(controlView("RecordCmd=RtspPreview"));
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getMovie());
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getResource1());
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getResource2());
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getResource3());
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getResource4());
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getResource5());
		// System.out.println(createPreViewInfo(controlView("RecordCmd=RtspPreview")).getResource6());
		// ------------------consoleTitile
		// QueryRAllInfo
		// queryRAllInfo=getRecordStatusFromCreateQueryRAllInfo(RecordingService.execute("RecordCmd=QueryRAllInfo"));
		// System.out.println(queryRAllInfo.getCardInfo0());
		// System.out.println(queryRAllInfo.getCardInfo1());
		// System.out.println(queryRAllInfo.getCardInfo2());
		// System.out.println(queryRAllInfo.getCardInfo3());
		// System.out.println(queryRAllInfo.getCardInfo4());
		// System.out.println(queryRAllInfo.getCardInfo5());
		// ------------------AllInfo
		// getRecordStatusFromCreateQueryRAllInfo(controlDirection("RecordCmd=QueryRAllInfo"));
		// System.out.println(getRecordStatusFromCreateQueryRAllInfo(controlDirection("RecordCmd=QueryRAllInfo")));
		// System.out.println(getRecordStatus());

		// RecordingService.execute("RecordCmd=QueryDiskStatus");
		// CosJSONUtil.parseJSON2Map(RecordingService.execute("RecordCmd=QueryDiskStatus"));
		
		String str="{'info': 'CardInfo0=aa,,false&CardInfo1=VGA,,false&CardInfo2=aa,,false&CardInfo3=aa,,false&MPort=&SPort=&pipMode=&pipSize=&pipPos=&logoStatus=closed&captionStatus=&mTrackStatus=&recordStatus=RecordStopped&livingStatus=LivingStopped&effectStatus=&recordMode=ResourcemMouseStatus=', 'result': 'ok'}";
		getRecordStatusFromCreateQueryRAllInfos(str);
	}

	private static void getRecordStatusFromCreateQueryRAllInfos(String str) {
		QueryRAllInfo queryRAllInfo = new QueryRAllInfo();
		Map<String, String> map = new HashMap<String, String>();
		map = CosJSONUtil.json2Map(CosJSONUtil.string2json(str));
		if(map !=null){
		queryRAllInfo.setInfo(map.get("info"));
		queryRAllInfo.setResult(map.get("result"));
		if (queryRAllInfo.getInfo() == null
				|| "".equals(queryRAllInfo.getInfo())) {
		} else {
			getQueryRAllInfos(queryRAllInfo, queryRAllInfo.getInfo());
		}
		
		}
		
	}

	/**
	 * Preview 请求
	 * 
	 * @param str
	 * @return
	 */
	public static PreViewInfo createPreViewInfo(String str) {
		PreViewInfo pv = new PreViewInfo();
		Map<String, String> map = new HashMap<String, String>();
		map = CosJSONUtil.json2Map(CosJSONUtil.string2json(str));
		pv.setInfo(map.get("info"));
		pv.setResult(map.get("result"));
		map = new HashMap<String, String>();
		map = CosJSONUtil.json2Map(CosJSONUtil.string2json(pv.getInfo()));
		pv.setMovie(map.get("movie"));
		pv.setResource1(map.get("resource1"));
		pv.setResource2(map.get("resource2"));
		pv.setResource3(map.get("resource3"));
		pv.setResource4(map.get("resource4"));
		pv.setResource5(map.get("resource5"));
		pv.setResource6(map.get("resource6"));
		return pv;
	}

	/**
	 * ok
	 * 
	 * RecordStatus
	 * 
	 * @param str
	 * @return
	 */
	public static QueryRAllInfo getRecordStatusFromCreateQueryRAllInfo(
			String str) {
		return createQueryRAllInfo(str);
	}

	public static QueryRAllInfo createQueryRAllInfo(String str) {
		QueryRAllInfo queryRAllInfo = new QueryRAllInfo();
		Map<String, String> map = new HashMap<String, String>();
		map = CosJSONUtil.json2Map(CosJSONUtil.string2json(str));
		if(map !=null){
		String temString=map.get("info");
		//if ("\r\n".equals(temString.substring(temString.length()-2)))
			//temString=temString.substring(0, temString.length()-2);	
		queryRAllInfo.setInfo(temString);
		queryRAllInfo.setResult(map.get("result"));
		if (queryRAllInfo.getInfo() == null
				|| "".equals(queryRAllInfo.getInfo())) {
			return null;
		} else {
			getQueryRAllInfo(queryRAllInfo, queryRAllInfo.getInfo());
		}
		
		}
		return queryRAllInfo;
	}
	private static void getQueryRAllInfos(QueryRAllInfo queryRAllInfo,
			String info) {
		Map<String, String> map = new HashMap<String, String>();
		String[] str = info.split(",,");
//		 map = JsonUtil.jsonToObject(str, Map.class);
		for(int i=0;i<str.length;i++){
			map.put(i + "", str[i]);
		}
		for(int i=0;i<str.length;i++){
			String value = map.get(i + "");
			if(value.split("=").length > 1){
				String key0 = value.split("=")[0];
				String key1 = value.split("=")[1];
				if(key0.indexOf("CardInfo0") > -1){
					queryRAllInfo.setCardInfo0(key1);
				}
				if(key0.indexOf("CardInfo1") > -1){
					queryRAllInfo.setCardInfo1(key1);
				}
				if(key0.indexOf("CardInfo2") > -1){
					queryRAllInfo.setCardInfo2(key1);
				}
				if(key0.indexOf("CardInfo3") > -1){
					queryRAllInfo.setCardInfo3(key1);
				}
				if(key0.indexOf("CardInfo4") > -1){
					queryRAllInfo.setCardInfo4(key1);
				}
			}
		}
		String infoValue = map.get(""+(str.length-1));
		String[] str1 = infoValue.split("&");
		Map<String, String> map2 = new HashMap<String, String>();
		for(int i=0;i<str1.length;i++){
			if(str1[i].split("=").length > 1){
			map2.put(str1[i].split("=")[0], str1[i].split("=")[1]);
		}
		}
//		System.out.println(map2);
			queryRAllInfo.setMPort(map2.get("MPort"));
			queryRAllInfo.setSPort(map2.get("SPort"));
			queryRAllInfo.setPipMode(map2.get("pipMode"));
			queryRAllInfo.setPipSize(map2.get("pipSize"));
			queryRAllInfo.setPipPos(map2.get("pipPos"));
			queryRAllInfo.setLogoStatus(map2.get("logoStatus"));
//			queryRAllInfo.setrecordMode(map2.get("recordMode"));
				queryRAllInfo.setCaptionStatus(map2.get("captionStatus"));
//				queryRAllInfo.setTrackStatus(map2.get("mTrackStatus"));
				queryRAllInfo.setmTrackStatus(map2.get("mTrackStatus"));
				queryRAllInfo.setRecordStatus(map2.get("recordStatus"));
				queryRAllInfo.setLivingStatus(map2.get("livingStatus"));
				queryRAllInfo.setEffectStatus(map2.get("effectStatus"));
//				
		
//		String[] str = info.split(",,");
//	if(str.length == 5){
//		if (str[0].split("=").length > 1)
//			queryRAllInfo.setCardInfo0(str[0].split("=")[1]);
//		if (str[1].split("=").length > 1)
//			queryRAllInfo.setCardInfo1(str[1].split("=")[1]);
//		if (str[2].split("=").length > 1)
//			queryRAllInfo.setCardInfo2(str[2].split("=")[1]);
//		if (str[3].split("=").length > 1)
//			queryRAllInfo.setCardInfo3(str[3].split("=")[1]);
////		if (str[4].split("=").length > 1)
////			queryRAllInfo.setCardInfo4(str[4].split("=")[1]);
//		str = str[4].split("&");
//		if (str[1].split("=").length > 1)
//			queryRAllInfo.setMPort(str[1].split("=")[1]);
//		if (str[2].split("=").length > 1)
//			queryRAllInfo.setSPort(str[2].split("=")[1]);
//		if (str[3].split("=").length > 1)
//			queryRAllInfo.setPipMode(str[3].split("=")[1]);
//		if (str[4].split("=").length > 1)
//			queryRAllInfo.setPipSize(str[4].split("=")[1]);
//		if (str[5].split("=").length > 1)
//			queryRAllInfo.setPipPos(str[5].split("=")[1]);
//		if (str[6].split("=").length > 1)
//			queryRAllInfo.setLogoStatus(str[6].split("=")[1]);
////		if (str[7].split("=").length > 1)
////			queryRAllInfo.setCaptionStatus(str[7].split("=")[1]);
////		if (str[8].split("=").length > 1)
////			queryRAllInfo.setTrackStatus(str[8].split("=")[1]);
////		if (str[9].split("=").length > 1)
////			queryRAllInfo.setmTrackStatus(str[9].split("=")[1]);
////		if (str[10].split("=").length > 1)
////			queryRAllInfo.setRecordStatus(str[10].split("=")[1]);
////		if (str[11].split("=").length > 1)
////			queryRAllInfo.setLivingStatus(str[11].split("=")[1]);
////		if (str[12].split("=").length > 1)
////			queryRAllInfo.setEffectStatus(str[12].split("=")[1]);
////		&recordMode=all
////				&mMouseStatus=off'
//	}else{
//		// QueryRAllInfo queryRAllInfo=new QueryRAllInfo();
//		if (str[0].split("=").length > 1)
//			queryRAllInfo.setCardInfo0(str[0].split("=")[1]);
//		if (str[1].split("=").length > 1)
//			queryRAllInfo.setCardInfo1(str[1].split("=")[1]);
//		if (str[2].split("=").length > 1)
//			queryRAllInfo.setCardInfo2(str[2].split("=")[1]);
//		if (str[3].split("=").length > 1)
//			queryRAllInfo.setCardInfo3(str[3].split("=")[1]);
//		if (str[4].split("=").length > 1)
//			queryRAllInfo.setCardInfo4(str[4].split("=")[1]);
//		if (str[5].split("=").length > 1)
//			queryRAllInfo.setCardInfo5(str[5].split("=")[1]);
//		str = str[6].split("&");
//		if (str[1].split("=").length > 1)
//			queryRAllInfo.setMPort(str[1].split("=")[1]);
//		if (str[2].split("=").length > 1)
//			queryRAllInfo.setSPort(str[2].split("=")[1]);
//		if (str[3].split("=").length > 1)
//			queryRAllInfo.setPipMode(str[3].split("=")[1]);
//		if (str[4].split("=").length > 1)
//			queryRAllInfo.setPipSize(str[4].split("=")[1]);
//		if (str[5].split("=").length > 1)
//			queryRAllInfo.setPipPos(str[5].split("=")[1]);
//		if (str[6].split("=").length > 1)
//			queryRAllInfo.setLogoStatus(str[6].split("=")[1]);
//		if (str[7].split("=").length > 1)
//			queryRAllInfo.setCaptionStatus(str[7].split("=")[1]);
//		if (str[8].split("=").length > 1)
//			queryRAllInfo.setTrackStatus(str[8].split("=")[1]);
//		if (str[9].split("=").length > 1)
//			queryRAllInfo.setmTrackStatus(str[9].split("=")[1]);
//		if (str[10].split("=").length > 1)
//			queryRAllInfo.setRecordStatus(str[10].split("=")[1]);
//		if (str[11].split("=").length > 1)
//			queryRAllInfo.setLivingStatus(str[11].split("=")[1]);
//		if (str[12].split("=").length > 1)
//			queryRAllInfo.setEffectStatus(str[12].split("=")[1]);
//		
//	}
//		System.out.println(map);
	}

	public static QueryRAllInfo getQueryRAllInfo(QueryRAllInfo queryRAllInfo,
			String info) {
		Map<String, String> map = new HashMap<String, String>();
		String[] str = info.split(",,");
		// map = JsonUtil.jsonToObject(str, Map.class);
		for (int i = 0; i < str.length; i++) {
			map.put(i + "", str[i]);
		}
		for (int i = 0; i < str.length; i++) {
			String value = map.get(i + "");
			if (value.split("=").length > 1) {
				String key0 = value.split("=")[0];
				String key1 = value.split("=")[1];
				if (key0.indexOf("CardInfo0") > -1) {
					queryRAllInfo.setCardInfo0(key1);
				}
				if (key0.indexOf("CardInfo1") > -1) {
					queryRAllInfo.setCardInfo1(key1);
				}
				if (key0.indexOf("CardInfo2") > -1) {
					queryRAllInfo.setCardInfo2(key1);
				}
				if (key0.indexOf("CardInfo3") > -1) {
					queryRAllInfo.setCardInfo3(key1);
				}
				if (key0.indexOf("CardInfo4") > -1) {
					queryRAllInfo.setCardInfo4(key1);
				}
			}
		}
		String infoValue = map.get("" + (str.length - 1));
		String[] str1 = infoValue.split("&");
		Map<String, String> map2 = new HashMap<String, String>();
		for (int i = 0; i < str1.length; i++) {
			if (str1[i].split("=").length > 1) {
				map2.put(str1[i].split("=")[0], str1[i].split("=")[1]);
			}
		}
//		System.out.println(map2);
		queryRAllInfo.setMPort(map2.get("MPort"));
		queryRAllInfo.setSPort(map2.get("SPort"));
		queryRAllInfo.setPipMode(map2.get("pipMode"));
		queryRAllInfo.setPipSize(map2.get("pipSize"));
		queryRAllInfo.setPipPos(map2.get("pipPos"));
		queryRAllInfo.setLogoStatus(map2.get("logoStatus"));
		// queryRAllInfo.setrecordMode(map2.get("recordMode"));
		queryRAllInfo.setCaptionStatus(map2.get("captionStatus"));
		// queryRAllInfo.setTrackStatus(map2.get("mTrackStatus"));
		queryRAllInfo.setmTrackStatus(map2.get("mTrackStatus"));
		queryRAllInfo.setRecordStatus(map2.get("recordStatus"));
		queryRAllInfo.setLivingStatus(map2.get("livingStatus"));
		queryRAllInfo.setEffectStatus(map2.get("effectStatus"));
		queryRAllInfo.setDeviceType(map2.get("DeviceType"));
		return queryRAllInfo;
	}
}
