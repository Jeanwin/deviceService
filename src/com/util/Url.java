package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import bsh.This;

import com.vo.ServiceInfo;

public class Url {
	private static String disrecUrl;
	private static String ftpNamePwd;

	public static String getMainUrl() {
		// return "http://localhost:8080/disrec";
		if (disrecUrl == null || "".equals(disrecUrl)) {
			ResourceBundle bundle = ResourceBundle.getBundle("deployment");
			disrecUrl = bundle.getString("disrecUrl");
		}
		return disrecUrl;
	}
	
	public static String getFtpNamePwd() {
		// return "http://localhost:8080/disrec";
		if (ftpNamePwd == null || "".equals(ftpNamePwd)) {
			ResourceBundle bundle = ResourceBundle.getBundle("deployment");
			ftpNamePwd = bundle.getString("ftpNamePwd");
		}
		return ftpNamePwd;
	}
	
	public static String getServerUrl(String type) {
		String ret = "";
		switch (type) {
		case "vds":
			ret = "vds";
			break;
		case "code":
			ret = "code";
			break;
		case "middle":
			ret = "middle";
			break;
		case "store":
			ret = "store";
			break;
		case "ftp":
		    ret="ftp";
		    break;
		case "ftpnp":
		    return getFtpNamePwd();
		case "web":
			// ret = "web";
			// break;
			return getMainUrl();
		}
		String str = sendGet(getMainUrl() + "/serverConfig/getServerUrl",
				"type=" + ret);
		if (str == null || "".equals(str)) {
			return "";
		} else {
			return str;
		}
	}

	// public static String getVdsUrl() {
	// return sendGet(getMainUrl() + "/serverConfig/getSendServer", "");
	// }

	// public static String getCodeUrl() {
	// return sendGet(getMainUrl() + "/serverConfig/getCodeServer", "");
	// }

	// public static String getMiddleUrl() {
	// return sendGet(getMainUrl() + "/serverConfig/getMiddleServer", "");
	// }

	public static Boolean useTransCode() {
		return true;
	}

	public static Boolean usevds() {
		return true;
	}

	public static String getServiceUrl(String mac, String deviceType) {
		String ret = "";
		ServiceInfo serviceInfo = null;
		try {
			serviceInfo = (ServiceInfo) RegServicePool.getService(mac).get(
					deviceType);
		} catch (Exception e) {
			return ret;
		}
		if (serviceInfo != null)
			ret = serviceInfo.getUrl();
		return ret;
	}

	// public static String readValue(String key) {
	// return key;
	// // File filePath;
	// // Properties props = new Properties();
	// //
	// // File directory = new File(".");
	// // // try {
	// // // filePath = new File(directory.getCanonicalPath() + File.separator
	// // //// + "info.properties");
	// // // + "WEB-INF"+ File.separator +"classes" + File.separator +
	// // "info.properties");
	// // //// + "src" + File.separator + "info.properties");
	// // //
	// // // } catch (Exception e) {
	// // // e.printStackTrace();
	// // // return null;
	// // // }
	// //
	// //
	// // String str = System.getProperty("user.dir");
	// // // System.out.println(System.getProperty("user.dir"));
	// // try {
	// // filePath = new File(str + File.separator
	// // // + "info.properties");
	// // + "WEB-INF"+ File.separator +"classes" + File.separator +
	// // "info.properties");
	// // // + "src" + File.separator + "info.properties");
	// //
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // return null;
	// // }
	// //
	// // try {
	// // InputStream in = new BufferedInputStream(new FileInputStream(
	// // filePath));
	// // props.load(in);
	// // String value = props.getProperty(key);
	// // System.out.println("");
	// // System.out.println(key + value);
	// // return value;
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // return null;
	// // }
	// }

	public static String readValue(File filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			String value = props.getProperty(key);
			// System.out.println(key + value);
			return value;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getContextClassLoader()
				.getResource(""));

		System.out.println(Url.class.getClassLoader().getResource(""));

		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(Url.class.getResource(""));
		System.out.println(Url.class.getResource("/"));
		// Class文件所在路径
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));

		// readValue("conseleIpUrl");
		System.out.println(System.getProperty("user.dir"));

		// System.out.println(CONSELEIPURL);
		// System.out.println(MAINURL);
		// System.out.println(VDSURL);
		// System.out.println(CODEURL);
	}

	public static String getUrl(String url) {
		// System.out.println(File.separator);
		String str = "";
		File directory = new File(".");
		try {
			File newPath = new File(directory.getCanonicalPath()
					+ File.separator + "src" + File.separator
					+ "info.properties");
			str = readValue(newPath, url);
		} catch (Exception exp) {
			// exp.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	@SuppressWarnings("unused")
	public static String sendGet(String url, String param) {

		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			if (param != null && !"".equals(param))
				urlNameString += "?" + param;
			URL realUrl = new URL(urlNameString);

			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();

			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
			}

			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				// System.out.println(line);
				result += line;
			}
		} catch (Exception e) {
			// System.out.println("发送GET请求出现异常！" + e);
			//e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				//e2.printStackTrace();
			}
		}

		return result;

	}

	/**
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setConnectTimeout(2000);;
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
//			System.out.println("发送 POST 请求出现异常！" + e);
//			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				//ex.printStackTrace();
			}
		}
		return result;
	}

	// public static String getUrls() {
	// str=request.getRealPath("info.properties");
	// System.out.println(str);
	// return CONSELEIPURL;
	// }
}
