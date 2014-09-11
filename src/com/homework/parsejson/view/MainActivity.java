package com.homework.parsejson.view;

import com.homework.parsejson.R;
import com.homework.parsejson.constant.GlobalConstant;
import com.homework.parsejson.database.Country;
import com.homework.parsejson.imagerloader.ImagerLoader;
import com.homework.parsejson.josnparser.CountryJsonParser;
import com.homework.parsejson.utils.Utils;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private TextView mTextView;
	private ListView mListView;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			handleMessages(msg);
			super.handleMessage(msg);
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mTextView = (TextView)findViewById(R.id.textview);
		mListView = (ListView)findViewById(R.id.listview);
		
		Button refreshButton = (Button)findViewById(R.id.button);
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadJsonThread();
			}
		});
		loadJsonThread();
	}
	
	private void loadJsonThread(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loadJson();
			}
		}).start();
	}
	
	private synchronized void loadJson() {
		Utils.LOGD("startLoadJson run");
		ImagerLoader.getInstance().clearCache();
		CountryJsonParser.getInstance().parse(GlobalConstant.jsonUrl, mHandler);
		
	}
	

	protected void handleMessages(Message msg) {
		switch (msg.what) {
		case GlobalConstant.MSG_JSONDOWNLOADFINISHED:
			Country country = (Country)msg.obj;
			
			mTextView.setText(country.getTitle());
			ListViewAdapter adapter = new ListViewAdapter(this, country, mListView);
			mListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			break;
		
		default:
			break;
		}

	}
	
}
