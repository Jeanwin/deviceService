package com.web.help;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.util.DeviceStatusPool;
import com.util.LivingPool;

@Controller
public class MonitoringHelp {
	
	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public void listAll(HttpServletRequest request, HttpServletResponse response) {
		display();
		String str = "<h2>LivingPool</h2><br>";

		Set<String> macs = LivingPool.getOnlineLiving();
		if (macs.size() > 0) {
			for (String mac : macs) {
				str += mac + "  " + LivingPool.getLiving(mac) + "<br>";
			}
		}
		str += "<br><h2>DeviceStatusPool</h2><br>";
		macs = DeviceStatusPool.getAllDeviceStatus();
		if (macs.size() > 0) {
			for (String mac : macs) {
				str +=mac+"  "+DeviceStatusPool.getDeviceStatus(mac) +"<br>";
			}
		}
//		str += "<a href='"+url+"deviceService/ptzHelp'>ptzHelp</a><br><br>";
//		str += "<a href='"+url+"deviceService/persetHelp'>persetHelp</a><br><br>";
//		str += "<a href='"+url+"deviceService/shutdownAndRestartHelp'>shutdownAndRestartHelp</a><br><br>";
		
//		System.out.println(str);
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print(str);
			pw.flush();
			pw.close();
		} catch (Exception e) {
		}
	}
	public static void display() {
//		System.out.println("***********************************************");
//		System.out.println("*------------ IDMac : ");
//		IDMacPool.forch();
//		System.out.println("*------------ RegHost : ");
//		RegHostPool.forch();
//		System.out.println("*--------- ReqUserNamePer : ");
//		ReqUserNamePerConsolePool.forch();
//		System.out.println("*----------- OnUsreNamePerConsole : ");
//		OnUsreNamePerConsolePool.forch();
//		System.out.println("*--------------- DeviceInbound : ");
//		DeviceInboundPool.forch();
//		System.out.println("*--------------- LivingPool : ");
//		LivingPool.forch();
//		System.out.println("*--------------- DeviceStatusPool : ");
//		DeviceStatusPool.forch();
//		System.out.println("***********************************************");
	}
}
