package com.example.jsonutil.httputil;

import java.util.Map;

import android.app.Activity;

public abstract class Request<Result> implements ITransaction {
	private  HttpManager httpManager;
	private Result result;
	private int errorCode=0;
	private String resultmsg="";
	private Exception e;
	private Activity activity;
	public static final int SUCCESS=0;
	public static final int ERROR=1;
	public Request(String url, Map<String, String> mRequestData,Activity activity) {
		this(url, mRequestData, HttpManager.POST, activity);
	}

	public Request(String url, Map<String, String> mRequestData, int method,Activity activity) {
		httpManager = new HttpManager(url, mRequestData, method, this);
		this.activity=activity;
	}

	public void execute() {
		httpManager.start();
	}

	@Override
	public void transactionOver(String result_str) {
		result = jsonParse(result_str);
		InitMsg(SUCCESS);

	}

	private void InitMsg(final int i) {
		 activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				if (i == SUCCESS) {
					onPostExecute(result);
				}else{
					onErrorExecute(errorCode,resultmsg,e);
				}
				
			}
		});
	}
	/**
	 * @description jsonParse子线程运行
	 */
	protected abstract Result jsonParse(String result);
	/**
	 * @description onPostExecute onErrorExecute 主线程运行
	 */
	protected abstract void onPostExecute(Result result);
	
	protected abstract void onErrorExecute(int errorCode,String result,Exception e);

	@Override
	public void transactionException(int errorCode, String result, Exception e) {
		this.errorCode=errorCode;
		this.resultmsg=result;
		this.e=e;
	
		InitMsg(ERROR);
	}
}
