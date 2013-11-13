package com.example.SimpleRSS;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private RadioButton giz_;
	private RadioButton kot_;
	private Button get_;
	private String url_ = "http://www.gizmodo.com/rss"; //start with gizmodo 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		giz_ = (RadioButton) findViewById(R.id.gizmodo);
		kot_ = (RadioButton) findViewById(R.id.kotaku);
		get_ = (Button) findViewById(R.id.getFeed);
			
		giz_.setChecked(true);
		kot_.setChecked(false);
		
		giz_.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				url_ = "http://www.gizmodo.com/rss";
				giz_.setChecked(true);
				kot_.setChecked(false);
				
			}
			
		});
		
		kot_.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				url_ = "http://www.kotaku.com/rss";
				kot_.setChecked(true);
				giz_.setChecked(false);
				
			}
			
		});
		
		doNetworkOperation();
		
	}
	

	public String getUrl(){
		return url_;
	}
	
	public void doNetworkOperation(){
		get_.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				NetworkTask nt = new NetworkTask(MainActivity.this);
				nt.execute();
				
				
				
								
			}
			
		});
	}
	
	public void doDisplay(String[][] data){
		
		Bundle sendStrings = new Bundle();
		
		sendStrings.putStringArray("titles", data[0]);
		sendStrings.putStringArray("content", data[1]);
		
		Intent intent = new Intent(MainActivity.this, DisplayFeed.class);
		
		intent.putExtras(sendStrings);
		startActivity(intent);
		finish();
	}
	
	
	
}
