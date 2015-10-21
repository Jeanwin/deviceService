package com.service;

import com.util.Url;

/**
 * @author zfc
 * 
 */
public class ChangeWindows {

	// private static String url = "http://192.168.12.47:10007/recording/cmd";
//	private static String url = Url.getRecordingUrl();
	//private static String url = Url.getConseleIpUrl() + ":10006/recording/cmd";;

	// private static String url = Url.readValue("recordingUrl");

	/**
	 * 
	 * 添加特效:10007/recording/cmd?RecordCmd=SetAllEffect&EffectType=9
	 * 
	 * 切换窗口:10007/recording/cmd?RecordCmd=ChangeCard&CardPort=3001&isMain=true
	 * 
	 * @param RecordCmd
	 *            SetPicInPicMode
	 * @param PicInPicMode
	 *            Insert/LeftRight/Tile/Pnp
	 * @param port
	 * @param Pos
	 * @param Size
	 * @param CurrPos
	 * 
	 * 3、 没有特效：RecordCmd=SetCardEffect&CardPort={0}&IsOpen=false 4、
	 *            全部命令设置： RecordCmd=SetAllEffect&EffectType={0}
	 */
	public static void execute(String mac,String port, String EffectType) {
		String url=Url.getServiceUrl(mac, "recording");
		controlDirection(url+"/cmd","RecordCmd=SetAllEffect&EffectType=" + EffectType);
		controlDirection(url+"/cmd","RecordCmd=ChangeCard&CardPort=" + port
				+ "&isMain=true");
	}

	public static void execute(String mac,String PicInPicMode, String PicInPicMode2,
			String port, String EffectType) {
		String url=Url.getServiceUrl(mac, "recording");
		controlDirection(url+"/cmd","RecordCmd=SetAllEffect&EffectType=" + EffectType);
		controlDirection(url+"/cmd","RecordCmd=ChangeCard&CardPort=" + port
				+ "&isMain=true");
	}

	public static void controlDirection(String url,String param) {
		// ConsoleOperation.sendGet(url,
		// "RecordCmd=SetDisSolve&DissolveDuraTime=1000");
		Url.sendGet(url, param);
	}

	public static void main(String[] args) throws InterruptedException {
		test();
	}

	public static void test() throws InterruptedException {
		//execute("SetAllEffect", null, "3000", "0");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3001", "1");
		//Thread.sleep(1000);
		//execute("SetAllEffect", null, "3000", "2");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3001", "3");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3000", "4");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3001", "5");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3000", "6");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3001", "7");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3000", "8");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3001", "9");
		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3000", "10");

		Thread.sleep(1000);
		//execute("SetAllEffect", null, "3001", "11");

		//controlDirection("RecordCmd=ChangeCard&CardPort=3000&isMain=true");
		Thread.sleep(1000);
		//controlDirection("RecordCmd=ChangeCard&CardPort=3001&isMain=true");
	}
}
