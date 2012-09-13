package com.sammy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lt.droidTest.MainActivity;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

public class songAsyncTask extends AsyncTask<Void, Void, Void> {
    String response;
	
	@Override
	protected Void doInBackground(Void... params) {
	String URL = "http://67.194.112.231";
	   int timeout = 10000; // 5 seconds
       HttpParams params1 = new BasicHttpParams();
       HttpConnectionParams.setConnectionTimeout(params1, timeout);
       int timoutSocket = 10000; // wait for data
       HttpConnectionParams.setSoTimeout(params1, timoutSocket);

       HttpClient httpClient = new DefaultHttpClient(params1);
       HttpPost httppost = new HttpPost("http://67.194.112.231/getData.php");
       List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
       namevaluepairs.add(new BasicNameValuePair("command", "hello"));
       
       try {
		httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		response = httpClient.execute(httppost, responseHandler);
       } 
       catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      //return response;
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			JSONArray arr = new JSONArray(response);
			ArrayList<String> songList = new ArrayList<String>();
			String[] arrSongList = new String[arr.length()]; 
			for(int i = 0; i < arr.length(); i++){
				JSONObject obj = arr.getJSONObject(i);
				arrSongList[i]= obj.getString("name");
			}
			//ArrayAdapter<String> songView = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,arrSongList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
