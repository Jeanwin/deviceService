package com.pip.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.
              support.ClassPathXmlApplicationContext;

public class RMIClient {
    public static void main(String[] args) {
        System.out.println("client start .....");
        ApplicationContext context =
            new ClassPathXmlApplicationContext(
                    "com/pip/client/rmi-client.xml");

        ISomeService service = 
            (ISomeService) context.getBean("someServiceProxy");

      System.out.println(service.getinfo(null));
    }
} 
