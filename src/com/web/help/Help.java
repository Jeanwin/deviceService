package com.web.help;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.util.DeviceCache;
import com.util.Url;

@Controller
public class Help {

	String url = Url.getMainUrl()+":8080/";

	@Resource
	DeviceCache deviceCache;

	/**
	 * @author zfc
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {
//		 getUrls(request);
		String str = "<h1>deviceService help</h1>";

		str += "<a href='"+url+"deviceService/nameHelp'>nameHelp</a><br><br>";
		str += "<a href='"+url+"deviceService/ptzHelp'>ptzHelp</a><br><br>";
		str += "<a href='"+url+"deviceService/persetHelp'>persetHelp</a><br><br>";
		str += "<a href='"+url+"deviceService/shutdownAndRestartHelp'>shutdownAndRestartHelp</a><br><br>";
		
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(str);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}

	public static String getUrls(HttpServletRequest request) {
		String str=request.getRealPath("WEB-INF/classes/deployment.properties");
		return str;
	}
	
	 public static String readValue(String filePath,String key) {
		  Properties props = new Properties();
		        try {
		         InputStream in = new BufferedInputStream (new FileInputStream(filePath));
		         props.load(in);
		         String value = props.getProperty (key);
		            return value;
		        } catch (Exception e) {
		         //e.printStackTrace();
		         return null;
		        }
		 }
}
