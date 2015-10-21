package com.pip.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RMIServer {
	public static void main(String[] args) {
    	new ClassPathXmlApplicationContext("com/pip/server/rmi-server.xml");
        
        System.out.println("deviceService pip Server start .....");
    }
} 
