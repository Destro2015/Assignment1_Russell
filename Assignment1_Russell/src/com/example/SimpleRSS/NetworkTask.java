package com.example.SimpleRSS;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkTask extends AsyncTask<Void, Void, String[][]> {
	MainActivity main_;

	public NetworkTask(MainActivity main) {
		main_ = main;
	}

	
	@Override
	protected String[][] doInBackground(Void... params) {
		// TODO Auto-generated method stub
		int size = 20;
		String[][] data = new String[2][size];
		String[] titles = new String[size];
		String[] content = new String[size];
		try{
			HttpClient client = new DefaultHttpClient();
		
			HttpGet get = new HttpGet(main_.getUrl());
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			
			InputStream in = entity.getContent();
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			//XmlPullParser xpp2 = factory.newPullParser();
			xpp.setInput(in, null);
			boolean valid = false;
			char currentTag = 'i';
			int i = 0;
/*
			 
			 
			String line = null;
			BufferedReader reader = new BufferedReader( new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			
			while((line = reader.readLine()) != null){
				sb.append(line + "\n");
				Log.d("data",line);	
			}
*/
			
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT && i < size) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
					
					
			           
				} else if (eventType == XmlPullParser.START_TAG) {
					if(xpp.getName().equals("title") && valid){
						currentTag = 't';
					}
					else if(xpp.getName().equals("description") && valid){
						currentTag = 'd';
					}
					else if(xpp.getName().equals("item")){
						valid = true;
					}
					else{
						currentTag ='i';
					}
			 
				} else if (eventType == XmlPullParser.END_TAG) {
					if(xpp.getName().equals("item")){
						valid = false;
					}
								            
				} else if (eventType == XmlPullParser.TEXT) {
					switch(currentTag){
					case 't':
						titles[i] = xpp.getText();
						Log.d("title", titles[i]);
						break;
					case 'd':
						/*
						String temp = xpp2.getText();
						xpp2.setInput(new StringReader(temp));
						 int eventType2 = xpp2.getEventType();
				         while (eventType2 != XmlPullParser.END_DOCUMENT) {
				          if(eventType2 == XmlPullParser.START_DOCUMENT) {
				              System.out.println("Start document");
				          } else if(eventType2 == XmlPullParser.START_TAG) {
				              System.out.println("Start tag "+xpp.getName());
				          } else if(eventType2 == XmlPullParser.END_TAG) {
				              System.out.println("End tag "+xpp.getName());
				          } else if(eventType2 == XmlPullParser.TEXT) {
				             
				          }
				          eventType = xpp2.next();
				         }
				         */
						
						content[i] =xpp.getText();
						Log.d("text", content[i]);
						i++;
						break;
					default:
						break;
										
					}
				}

				eventType = xpp.next();
			}
			Log.d("end", "end of document reached");
			in.close();
			data[0] = titles;
			data[1] = content;
				
					
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return data;
	}
	@Override
	protected void onPostExecute(String[][] data){
		main_.doDisplay(data);
	}
	
	

}
