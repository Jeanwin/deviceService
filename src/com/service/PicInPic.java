package com.service;

import com.util.Url;


/**
 * @author zfc
 * 
 */
public class PicInPic {

//	private static String url = "http://192.168.12.47:10006/recording/cmd";
//	private static String url = Url.getRecordingUrl();
//	private static String url = Url.getConseleIpUrl() + ":10006/recording/cmd";;
//	private static String url = Url.readValue();

	/**
	 *  RecordCmd=SetPnp&CardPort={0}&isMain=true（port为主画面的端口号值）
	 * @param RecordCmd
	 *            SetPicInPicMode
	 * @param PicInPicMode
	 *            Insert/LeftRight/Tile/Pnp
	 * @param port
	 * @param Pos
	 * @param Size
	 * @param CurrPos
	 */
	public static void execute(String mac,String RecordCmd,String PicInPicMode, String port, String Pos,
			String Size, String CurrPos) {
		String url=Url.getServiceUrl(mac, "recording");
		if (port == null || "".equals(port))
			return;
		
		if (RecordCmd != null && !"".equals(RecordCmd)&&"SetPnp".equals(RecordCmd))
			controlDirection(url+"/cmd","RecordCmd="+RecordCmd+"&CardPort="+port + "&isMain=true");
//			return;
		
		controlDirection(url+"/cmd","RecordCmd=SetPicInPicCardPort&CardPort=" + port + "&isMain=true");
		if (PicInPicMode != null && !"".equals(PicInPicMode)) {
			if ("LeftRight".equals(PicInPicMode) || "Tile".equals(PicInPicMode))
				controlDirection(url+"/cmd","RecordCmd=SetPicInPicMode&PicInPicMode="
						+ PicInPicMode + "&isMain=true");

			if ("Insert".equals(PicInPicMode) || "Insert".equals(PicInPicMode))
				controlDirection(url+"/cmd","RecordCmd=SetPicInPicMode&PicInPicMode="
						+ PicInPicMode + "&Pos=" + Pos + "&Size=" + Size
						+ "&CurrPos=" + CurrPos + "&isMain=true");

		}

	}

	/**
	 *  关闭小画面：RecordCmd=ClosePicInPic&isMain=false
	 *  
	 *  10007/recording/cmd?RecordCmd=ClosePicInPic&isMain=true
	 */
	public static void del(String mac) {
		String url=Url.getServiceUrl(mac, "recording");
		controlDirection(url+"/cmd","RecordCmd=ClosePicInPic&isMain=true");
	}
	/**
	 *  更换主副画面：RecordCmd=ChangePicInPic&isMain=true
	 *  
	 *  10007/recording/cmd?RecordCmd=ChangePicInPic&isMain=true
	 */
	public static void ChangePicInPic(String mac) {
		String url=Url.getServiceUrl(mac, "recording");
		controlDirection(url+"/cmd","RecordCmd=ChangePicInPic&isMain=true");
	}

	public static void controlDirection(String url,String param) {
		
		Url.sendGet(url, param);
	}

	public static void main(String[] args) throws InterruptedException {
		// Insert模式
		//execute(null,"Insert", "3000", "0,0", "0.5,0.5", "LU");
		//Thread.sleep(1000);
		//execute(null,"Insert", "3000", "0.5,0", "0.5,0.5", "RU");
		//Thread.sleep(1000);
		//execute(null,"Insert", "3000", "0,0.5", "0.5,0.5", "LD");
		//Thread.sleep(1000);
		//execute(null,"Insert", "3000", "0.5,0.5", "0.5,0.5", "RD");
		//Thread.sleep(1000);
		
////		 LeftRight模式
//		execute(null,"LeftRight","3000",null,null,null);
//		Thread.sleep(1000);
//		// Tile模式
//		execute(null,"Tile","3000",null,null,null);
//		Thread.sleep(1000);
//		// Pnp模式
//		execute("SetPnp",null,"3000",null,null,null);
//		Thread.sleep(1000);

		//更换主副画面
		//ChangePicInPic();
		// 关闭小画面
		//Thread.sleep(1000);
		//del();
	}
}
