package com.example.jsonutil.httputil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author jwq
 * @version 1.0
 * @created 2013-6-10 下午1:27:24
 */
public class HttpUtil {
	private static final String CHARSET_UTF8 = HTTP.UTF_8;
	private static HttpClient customerHttpClient;

	/**
	 * 描述 post 请求
	 * 
	 * @param url
	 *            地址
	 * @param data
	 *            参数集合
	 * @return json串
	 */
	public static String get(String url, Map<String, String> data) throws IOException{
	 	try {
			String sb = "";
			if (null == data)
				data = new HashMap<String, String>();
			if (data.size() > 0) {
				for (Map.Entry<String, String> entry : data.entrySet()) {
					sb += "&" + entry.getKey() + "=" + entry.getValue();
				}
			}
			if (url.indexOf("?") == -1) {
				url += sb.replaceFirst("&", "?");
			} else {
				url += sb;
			}
			// HttpGet连接对象
			HttpGet httpRequest = new HttpGet(url);
			// 取得HttpClient对象
			HttpClient httpclient = getHttpClient();
			// 请求HttpClient，取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new HttpResponseException(1, "请求失败");
			}
			return EntityUtils.toString(httpResponse.getEntity());
		//} catch (ParseException e) {
		//	// TODO Auto-generated catch block
		//	throw new RuntimeException("连接失败", e);
 } catch (IOException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 	throw new HttpResponseException(1, "请求失败");
		 }
	}

	/**
	 * 描述 post 请求
	 * 
	 * @param url
	 *            地址
	 * @param data
	 *            参数集合
	 * @return json串
	 */
	public static String post(String url, Map<String, String> data) throws IOException{
	try {
			if (null == data)
				data = new HashMap<String, String>();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			StringBuffer sb = new StringBuffer();
			if (data.size() > 0) {
				for (Map.Entry<String, String> entry : data.entrySet()) {
					if (entry.getValue() == null)
						continue;
					sb.append(entry.getKey() + "=" + entry.getValue() + "&");
					params.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
			}
			UrlEncodedFormEntity urlEncoded = new UrlEncodedFormEntity(params,
					CHARSET_UTF8);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(urlEncoded);
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				//throw new RuntimeException("请求失败");
			throw new HttpResponseException(1, "请求失败");
			}
			HttpEntity resEntity = response.getEntity();
			return (resEntity == null) ? null : EntityUtils.toString(resEntity,
					CHARSET_UTF8);
		//} catch (UnsupportedEncodingException e) {
		//	return null;
		//} catch (ClientProtocolException e) {
		//	return null;
		} catch (IOException e) {
			throw new HttpResponseException(1, "请求失败");
 	}

	}

	/**
	 * 创建httpClient实例
	 * 
	 * @return customerHttpClient
	 * @throws Exception
	 */
	private static synchronized HttpClient getHttpClient() {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET_UTF8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			/*HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");*/
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 1000);
			/* 连接超时 */
			int ConnectionTimeOut = 10000;
			/*
			 * if (!HttpUtils.isWifiDataEnable(context)) { ConnectionTimeOut =
			 * 10000; }
			 */
			HttpConnectionParams
					.setConnectionTimeout(params, ConnectionTimeOut);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 4000);
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			customerHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customerHttpClient;
	}
}
