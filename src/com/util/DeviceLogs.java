package com.util;

import org.apache.log4j.Logger;

public class DeviceLogs{

	private static Logger logger = Logger.getLogger(DeviceLogs.class);

	public void log(String str) {
		if (str != null && !"".equals(str)) {
			logger.info(str);
		}
	}
}
