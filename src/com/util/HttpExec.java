package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;

@SuppressWarnings("deprecation")
public class HttpExec {
	private static final Header CONTENT_TYPE_TEXT_JSON = null;
	private static final String APPLICATION_JSON = null;

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		// System.out.println(doGet("http://192.168.12.47:10003/ptz/help"));
		// System.out.println(controlDirection(Url.getConseleIpUrl()+":10003/ptz/help",
		// ""));
	}

	/**
	 * @author zfc
	 * 
	 *         doDelete
	 */
	public static String doDelete(String url) {
		String ret = "";
		HttpResponse response;
		if (url != null && url.length() != 0) {
			HttpDelete delete = new HttpDelete(url);
			delete.setHeader("Accept-Encoding", "utf-8");
			delete.setHeader("Accept-Language", "zh-CN");
			delete.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			try {
				response = new DefaultHttpClient().execute(delete);
				if (response != null) {
					// System.out.println("Response Code: "
					// + response.getStatusLine().getStatusCode());
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent(), "utf-8"));
						String line = "";
						while ((line = rd.readLine()) != null) {
							// System.out.println(line);
							ret += line;
						}
					}
				}

			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		return ret;
	}

	/**
	 * @author zfc
	 * 
	 *         doGet
	 */
	public static String doGet(String url) {
		String ret = "";
		HttpResponse response;
		if (url != null && url.length() != 0) {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Accept-Encoding", "utf-8");
			httpGet.setHeader("Accept-Language", "zh-CN");
			httpGet.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			try {
				response = new DefaultHttpClient().execute(httpGet);
				if (response != null) {
					// System.out.println("Response Code: "
					// + response.getStatusLine().getStatusCode());
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent(), "utf-8"));
						String line = "";
						while ((line = rd.readLine()) != null) {
							// System.out.println(line);
							ret += line;
						}
					}
				}

			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		return ret;

	}

	/**
	 * @author zfc
	 * 
	 *         doPost--doPostWithJSON
	 */
	@SuppressWarnings({ "resource" })
	public static String doPostWithJSON(String url, String values) {
		String ret = "";
		HttpResponse response;
		if (url != null && url.length() != 0) {
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept-Encoding", "utf-8");
			post.setHeader("Accept-Language", "zh-CN");
			post.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			try {
				if (values == null || "".equals(values))
					return ret;
				StringEntity se = new StringEntity(values, "UTF-8");
				se.setContentType(CONTENT_TYPE_TEXT_JSON);
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
						APPLICATION_JSON));
				post.setEntity(se);

				response = new DefaultHttpClient().execute(post);
				if (response != null) {
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent(), "utf-8"));
						String line = "";
						while ((line = rd.readLine()) != null) {
							// System.out.println(line);
							ret += line;
						}
					}
				}

			} catch (Exception e) {
			}
		}
		return ret;
	}

	/**
	 * @author zfc
	 * 
	 *         doPost--doPost
	 */
	public static String doPost(String url, List<NameValuePair> values) {
		String ret = "";
		HttpResponse response;
		if (url != null && url.length() != 0) {
			HttpPost post = new HttpPost(url);
			post.setHeader("Accept-Encoding", "utf-8");
			post.setHeader("Accept-Language", "zh-CN");
			post.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			try {
				if (values != null && values.size() > 0) {
					// System.out.println(values.toString());
					try {
						post.setEntity(new UrlEncodedFormEntity(values,
								HTTP.UTF_8));
					} catch (UnsupportedEncodingException e) {
						// e.printStackTrace();
					}
				}
				response = new DefaultHttpClient().execute(post);
				if (response != null) {
					int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent(), "utf-8"));
						String line = "";
						while ((line = rd.readLine()) != null) {
							// System.out.println(line);
							ret += line;
						}
					}
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * @author zfc
	 * 
	 *         doPut--doPutWithJSON
	 */
	public static String doPutWithJSON(String url, String values) {
		String ret = "";
		if (url != null && url.length() > 0) {
			HttpPut request = new HttpPut(url);
			request.setHeader("Accept-Encoding", "utf-8");
			request.setHeader("Accept-Language", "zh-CN");
			request.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");
			if (values == null || "".equals(values))
				return ret;
			// System.out.println(values.toString());
			StringEntity se = new StringEntity(values, "UTF-8");
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					APPLICATION_JSON));
			request.setEntity(se);
			try {
				@SuppressWarnings({ "resource" })
				HttpResponse response = new DefaultHttpClient()
						.execute(request);
				if (response != null) {
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent(), "utf-8"));
						String line = "";
						while ((line = rd.readLine()) != null) {
							// System.out.println(line);
							ret += line;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * @author zfc
	 * 
	 *         doPut--doPut
	 */
	public static String doPut(String url, List<NameValuePair> values) {
		String ret = "";
		if (url != null && url.length() > 0) {
			HttpPut request = new HttpPut(url);
			request.setHeader("Accept-Encoding", "utf-8");
			request.setHeader("Accept-Language", "zh-CN");
			request.setHeader("Accept",
					"application/json, application/xml, text/html, text/*, image/*, */*");

			if (values != null && values.size() > 0) {
				// System.out.println(values.toString());
				try {
					request.setEntity(new UrlEncodedFormEntity(values));
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			try {
				@SuppressWarnings({ "resource" })
				HttpResponse response = new DefaultHttpClient()
						.execute(request);
				if (response != null) {
					StatusLine statusLine = response.getStatusLine();
					int statusCode = statusLine.getStatusCode();
					if (statusCode == 200 || statusCode == 403) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent(), "utf-8"));
						String line = "";
						while ((line = rd.readLine()) != null) {
							// System.out.println(line);
							ret += line;
						}
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return ret;
	}

	public static String controlDirection(String url, String str) {
		return HttpExec.sendGet(url, str);
	}

	/**
	 * @author zfc
	 * 
	 *         sendGet
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
			// e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				// e2.printStackTrace();
			}
		}
		return result;

	}

	public static String simplePost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			// e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
		}
		return result;
	}
}