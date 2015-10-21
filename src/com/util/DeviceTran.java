package com.util;

public class DeviceTran {
	public static String TranName(String name) {
		if ("教师".equals(name)) return "teacher";
		if ("学生".equals(name)) return "students";
		if ("VGA".equals(name)) return "vga";
		if ("教师全景".equals(name)) return "full";
		if ("电影".equals(name)) return "film";
		if ("板书".equals(name)) return "blackboard";
		if ("学生全景".equals(name)) return "studentsfull";
		return "";
	}
}
