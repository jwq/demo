package com.example.jsonutil.httputil;


/**
 * @author jwq    
 * @version 1.0  
 * @created 2013-6-10 下午1:14:02
 */
public interface ITransaction {
	/**
	 * 描述  网络请求会调函数
	 * @param result 请求结果返回字符串
	 */
	public void transactionOver(String result);
	/**
	 * 描述 出现异常回调函数
	 * @param errorCode 错误码
	 * @param result 请求结果
	 * @param e 异常对象
	 */
	public void transactionException(int errorCode,String result,Exception e);
}
