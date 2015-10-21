package com.common;

import java.io.File;

/**
 * 常量类
 * @author gly
 *
 */
public class AppConstants {
	/**
	 * 截图存放相对路径
	 */
	public static final String IMAGE_PATH = File.separatorChar+"home"+File.separatorChar+"data"+File.separatorChar+"images";
	/**
	 *切图url
	 */
	public static final String CUT_IMAGE_URL = ":50501/rounder/turning";
	
	/**
	 * status =0 只获取直播流
	 */
	public static final String LIVING_GET_FLOW = "0";
}
