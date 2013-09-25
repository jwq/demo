package com.example.jsonutil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	private HomeResultBean homeResultBean;
	private	Holder holder;
	private LayoutInflater layoutInflater;
	public MyAdapter(HomeResultBean homeResultBean,Activity activity) {
			this.homeResultBean=homeResultBean;
			layoutInflater=activity.getLayoutInflater();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return homeResultBean.getList().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return homeResultBean.getList().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			holder =new Holder();
			convertView=layoutInflater.inflate(R.layout.item, null);
			holder.item_text=(TextView) convertView.findViewById(R.id.item_text);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		holder.item_text.setText(homeResultBean.getList().get(position).getTitle()+"");
		return convertView;
	}
	
	static class Holder{
		TextView item_text;
	}
}
