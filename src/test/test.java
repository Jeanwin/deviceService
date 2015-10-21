package test;

import java.util.Date;

import com.util.DateUtils;

public class test {
	public static void main(String[] args) throws InterruptedException, Exception {
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//		// "/WebRoot/WEB-INF/config/application/applicationContext.xml");
//				"classpath:applicationContext.xml");
//		DeviceCache aa = (DeviceCache) context.getBean("deviceCache");

		Date d1 = new Date();
		Thread.sleep(1000);
		Date d2 = new Date();
		System.out.println(DateUtils.timeDifference(d1,d2));

//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
//Date d1 = dateFormat.parse(dateFormat.format( new Date()));
//Thread.sleep(1000);
//Date d2 = dateFormat.parse(dateFormat.format( new Date()));
//long diff = d1.getTime() - d2.getTime();
//System.out.println(diff);
		
//   Date d1 = df.parse("2004-03-26 13:31:40");
//   Date d2 = df.parse("2004-03-26 13:31:41");
//   long diff = d1.getTime() - d2.getTime();
//      df.parse(new Date().toString());
		/*
//		aa.verifyWrite("pass", "xx");
//		aa.verifyWrite("name", "xx");
		aa.verifyReadAllKeys();
		// aa.verifyWrite("name", "zfc");
		// aa.verifyReadAll("name");
		aa.verifyDel("name");
		aa.verifyReadAllKeys();
*/

//		Map<String, Map<String, ServiceInfo>> service;
//		Map<String, Map<String, Map<String, ServiceInfo>>> all =getAll(aa);
//        for(Entry<String, Map<String, Map<String, ServiceInfo>>> entry : all.entrySet()){  
//            entry.getKey();  
//            service=entry.getValue();  
//            System.out.println("the map key is : " + entry.getKey() + " || the value is : " + "entry.getValue()");
//        }  
		System.exit(0);
	}

}
