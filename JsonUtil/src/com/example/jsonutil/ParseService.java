package com.example.jsonutil;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.jsonutil.httputil.Request;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @description 进行数据解析
 * @version 1.0
 * @author Jinwenqi
 * @update 2013-8-27 上午10:59:33
 */
public class ParseService {
	private Activity activity;
	// 测试地址 本地tomcat
	public static final String TESTURL = "http://10.8.8.85:8080/examples/test.txt";
	public static final Integer SUCCESSCODE =  0 ;
	private ProgressDialog dialog;

	public ParseService(Activity activity) {
		this.activity = activity;
	}

	/**
	 * @param listenter
	 *            数据回调
	 * @update 2013-8-27 上午11:00:49
	 */
	public void JsonParse(RequestListenter listenter) {
		dialog = ProgressDialog.show(activity, "", "loading...");
		Map<String, String> mRequestData = new HashMap<String, String>();
		MyRequest myRequest = new MyRequest(TESTURL, mRequestData, activity,
				listenter);
		myRequest.execute();
	}

	class MyRequest extends Request<HomeResultBean> {
		private RequestListenter RequestListenter;

		public MyRequest(String url, Map<String, String> mRequestData,
				Activity activity, RequestListenter requestListenter) {
			super(url, mRequestData, activity);
			this.RequestListenter = requestListenter;
		}

		@Override
		protected HomeResultBean jsonParse(String result) {
			HomeResultBean bean;
			try {
				Gson gson = new Gson();
				bean = gson.fromJson(result, HomeResultBean.class);
			} catch (JsonSyntaxException e) {

				e.printStackTrace();
				return null;
			}
			return bean;
		}

		@Override
		protected void onPostExecute(HomeResultBean result) {
			// System.out.println(result.getDemo());
			if (null != dialog) {
				dialog.dismiss();
			}
			if (null != result) {
				if (SUCCESSCODE==result.getStatusCode()) {
					RequestListenter.result(result);
				} else {
					Toast.makeText(activity, result.getStatusMsg(),
							Toast.LENGTH_LONG).show();
				}
			}

		}

		@Override
		protected void onErrorExecute(int errorCode, String result, Exception e) {
			if (null != dialog) {
			//	dialog.dismiss();
			}
			Toast.makeText(
					activity,
					"errorCode=" + errorCode + "==result=" + result
							+ "==Exception==" + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

	}
}
