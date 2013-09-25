package com.example.jsonutil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private Button loding;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loding=(Button) findViewById(R.id.button1);
		listView=(ListView) findViewById(R.id.mylist);
		loding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseService parseService=new ParseService(MainActivity.this);
				parseService.JsonParse(new RequestListenter() {
					
					@Override
					public void result(HomeResultBean bean) {
						MyAdapter adapter=new MyAdapter(bean, MainActivity.this);
						listView.setAdapter(adapter);
					}
				});
				
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
