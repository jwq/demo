package com.example.jsonutil;


import java.io.Serializable;
import java.util.ArrayList;


public class HomeResultBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1065006694726545871L;
	
	
	private ArrayList<DetialBean> list;
	private int Tag;
	private int statusCode;
	private String statusMsg;
	private String rspTime;
	public ArrayList<DetialBean> getList() {
		return list;
	}
	public void setList(ArrayList<DetialBean> list) {
		this.list = list;
	}
	public int getTag() {
		return Tag;
	}
	public void setTag(int tag) {
		Tag = tag;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getRspTime() {
		return rspTime;
	}
	public void setRspTime(String rspTime) {
		this.rspTime = rspTime;
	}
	
	
	
}
