package com.homework.parsejson.view;

import com.homework.parsejson.R;
import com.homework.parsejson.constant.GlobalConstant;
import com.homework.parsejson.josnparser.ParseJsonTask;
import com.homework.parsejson.utils.Utils;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private TextView mTextView;
	private ListView mListView;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		mTextView = (TextView)findViewById(R.id.textview);
		mListView = (ListView)findViewById(R.id.listview);
		
		Button refreshButton = (Button)findViewById(R.id.button);
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadJson();
			}
		});
		
		loadJson();
	}

	
	private  void loadJson() {
		Utils.LOGD("LoadJson run");
		ParseJsonTask parseJsonTask = new ParseJsonTask(this, mTextView, mListView);
		parseJsonTask.execute(GlobalConstant.JSONURL);
	}
	
}
