package com.example.jsonutil.httputil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpResponseException;

import android.util.Log;

/**
 * @author jwq
 * @version 1.0
 * @created 2013-6-10 下午1:27:10
 */
public class HttpManager {
	public static final int GET = 0;
	public static final int POST = 1;
	private ITransaction iTransaction;
	private String url;// 请求地址
	private Map<String, String> mRequestData;// 参数集合
	private int method;// 请求方式
	public void setiTransaction(ITransaction iTransaction) {
		this.iTransaction = iTransaction;
	}

	public Map<String, String> getmRequestData() {
		return mRequestData;
	}

	public void setmRequestData(Map<String, String> mRequestData) {
		this.mRequestData = mRequestData;
	}

	public HttpManager(String url, Map<String, String> mRequestData) {
		this(url, mRequestData, null);
	}

	public HttpManager(String url, Map<String, String> mRequestData,
			ITransaction iTransaction) {
		this(url, mRequestData, GET, iTransaction);
	}

	public HttpManager(String url, Map<String, String> mRequestData,
			int method, ITransaction iTransaction) {
		this.iTransaction = iTransaction;
		this.url = url;
		this.mRequestData = mRequestData;
		this.method = method;
	}
	public synchronized void start() {
		RequsetThread thread=new RequsetThread();
		new Thread(thread).start();
	}
	class RequsetThread implements Runnable {
		private void request() throws IllegalStateException,
				HttpResponseException, IOException ,RuntimeException,NullPointerException{
			long begin = System.currentTimeMillis();
			String respone = null;
			switch (method) {
			case GET:
				respone = HttpUtil.get(url, mRequestData);
				break;
			case POST:
				respone = HttpUtil.post(url, mRequestData);
			default:
				break;
			}
			long end = System.currentTimeMillis();
			Log.d("request_time", "request_time==" + (end - begin));
			if (null != iTransaction) {
				iTransaction.transactionOver(respone);
			}
		}

		private void dealWithExcaption(int errorCode, String result, Exception e) {
			if (null != iTransaction) {
				iTransaction.transactionException(errorCode, result, e);
			}
		}

		@Override
		public void run() {
			if (null == mRequestData) {
				mRequestData = new HashMap<String, String>();
			}
			try {
				request();
			} 
			
			catch (HttpResponseException e) {
				dealWithExcaption(e.getStatusCode(), e.getMessage(), e);
				e.printStackTrace();
			} catch (IllegalStateException e) {
				dealWithExcaption(1, e.getMessage(), e);
				e.printStackTrace();
			} 
			catch (NullPointerException e) {
				dealWithExcaption(1, e.getMessage(), e);
				e.printStackTrace();
			}
			catch (IOException e) {
				dealWithExcaption(1, e.getMessage(), e);
				e.printStackTrace();
			}
		}

	}
}
