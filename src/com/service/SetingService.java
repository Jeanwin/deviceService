package com.service;

import com.util.Url;
import com.vo.CourseInfo;


/**
* @author zfc
* 
 * 
 */
public class SetingService {

//	private static String url = "http://192.168.12.47:10007/recording/cmd";
	//private static String url = Url.getConseleIpUrl() + ":10006/recording/cmd";;
	

	/**
	 * @author zfc
	 * 
	 * 课程同步设置
	 * 127.0.0.1:10006/recording/0/recording/cmd?RecordCmd=UpdateClassSchedule
	 */
	public static String updateClassSchedule(String mac) {
		String url=Url.getServiceUrl(mac,"recording");
		return Url.sendGet(url+"/cmd", "RecordCmd=UpdateClassSchedule");
	}
	/**
	 * @author zfc
	 * 
	 * 课程信息设置    SetCourseInfo&Department={0}&Subject={1}&CourseName={2}&Teacher={3}&Address={4}&DateTime={5}&Description={6}
	 * 
	 */
	public static void SetFileProperty(CourseInfo vo) {
		if(vo==null)return;
		String string="RecordCmd=SetCourseInfo"
				+ "&Department="+vo.getDepartment()
				+ "&Subject="+vo.getSubject()
				+ "&CourseName="+vo.getCourseName()
				+ "&Teacher="+vo.getTeacher()
				+ "&Address="+vo.getAddress()
				+ "&DateTime="+vo.getDateTime()
				+ "&Description="+vo.getDescription()
				;
		controlDirection(string);
	}
	
	
	/**
	 * 
	 * @param url
	 * @param direction
	 * @param param
	 */
	public static void controlDirection(String string) {
		//System.out.println(url +"?"+string);
//		ConsoleOperation.sendGet(url ,"?"+string);

	}

	public static void main(String[] args) {
		CourseInfo c=new CourseInfo();
		c.setDepartment("1");
		c.setSubject("2");
		c.setCourseName("3");
		c.setTeacher("4");
		c.setAddress("5");
		c.setDateTime("6");
		c.setDescription("7");
		SetFileProperty(c);
	}
}
