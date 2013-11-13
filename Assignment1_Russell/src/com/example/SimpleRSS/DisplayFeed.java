package com.example.SimpleRSS;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class DisplayFeed extends Activity {	
	
	String[] dataTitles;
	String[] dataContent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_feed);
		
		
		Bundle receiveData = getIntent().getExtras();
		dataTitles = receiveData.getStringArray("titles");
		dataContent = receiveData.getStringArray("content");
		Log.d("recv titles", dataTitles[0]);
		
		
		ListView listView = (ListView)this.findViewById(R.id.listView1);
		ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(DisplayFeed.this, R.layout.feeditem, dataTitles);
		
		listView.setAdapter(itemAdapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int i, long arg3) {
				Toast.makeText(DisplayFeed.this, dataContent[i], Toast.LENGTH_LONG).show();
				
				return true;
			}
			
		});
		//Log.d("debug","here");
		
	}
	
	public void onBackPressed(){
		Intent intent = new Intent(DisplayFeed.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
}