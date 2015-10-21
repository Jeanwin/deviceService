package com.util;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author zfc
 * 
 */
public class DateFormat {

    public static  String formatDate(Date date)throws Exception{
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
	public static void main(String[] args) throws Exception {
		Date d=new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		SimpleDateFormat format2 = new SimpleDateFormat("yy-MM-dd H:m:s");
		SimpleDateFormat format3 = new SimpleDateFormat("y-M-d H:m:s");
//		SimpleDateFormat format4 = new SimpleDateFormat("yyMMddHms");
		System.out.println(format.format(d));
		System.out.println(format2.format(d));
		System.out.println(format3.format(d));
		System.out.println(new SimpleDateFormat("yyMMddHms").format(d));
	}
}
