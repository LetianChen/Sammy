package com.lt.droidTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
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

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button connect;
	Button endsong;
	TextView display;
	MediaPlayer bgSong;
	EditText enterIp;
	String URL;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.lt.droidTest.R.layout.activity_main);
		final songAsyncTaskTest getSongs = new songAsyncTaskTest();
		connect = (Button) findViewById(com.lt.droidTest.R.id.connect);
		endsong = (Button) findViewById(com.lt.droidTest.R.id.endSong);
		enterIp = (EditText) findViewById(com.lt.droidTest.R.id.enterIp);
		connect.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				URL = enterIp.getText().toString();
				getSongs.execute();
			}

		});

		endsong.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				HttpParams params1 = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(params1, 10000);
				int timoutSocket = 10000; // wait for data
				HttpConnectionParams.setSoTimeout(params1, timoutSocket);

				HttpClient httpClient = new DefaultHttpClient(params1);
				HttpPost httppost = new HttpPost("http://" + URL + "/kill.php");
				List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
				namevaluepairs.add(new BasicNameValuePair("command", "hello"));

				try {
					httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					httpClient.execute(httppost, responseHandler);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public class songAsyncTaskTest extends AsyncTask<Void, Void, Void> {
		String response;

		@Override
		protected Void doInBackground(Void... params) {
			// URL = "http://67.194.198.197";
			int timeout = 10000; // 5 seconds
			HttpParams params1 = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params1, timeout);
			int timoutSocket = 10000; // wait for data
			HttpConnectionParams.setSoTimeout(params1, timoutSocket);

			HttpClient httpClient = new DefaultHttpClient(params1);
			HttpPost httppost = new HttpPost("http://" + URL + "/getData.php");
			List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
			namevaluepairs.add(new BasicNameValuePair("command", "hello"));

			try {
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				response = httpClient.execute(httppost, responseHandler);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// return response;
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				JSONArray arr = new JSONArray(response);
				// ArrayList<String> songList = new ArrayList<String>();
				String[] arrSongList = new String[arr.length()];
				for (int i = 0; i < arr.length(); i++) {
					JSONObject obj = arr.getJSONObject(i);
					arrSongList[i] = obj.getString("name");
				}
				final ArrayAdapter<String> songView = new ArrayAdapter<String>(
						MainActivity.this, android.R.layout.simple_list_item_1,
						arrSongList);
				ListView songListView = (ListView) findViewById(R.id.songListView);
				songListView.setAdapter(songView);
				songListView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> adapter, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						String result = songView.getItem(position);
						try {
							String songName = new JSONObject().put("song",
									result).toString();
							int timeout = 10000; // 5 seconds
							HttpParams params1 = new BasicHttpParams();
							HttpConnectionParams.setConnectionTimeout(params1,
									timeout);
							int timoutSocket = 10000; // wait for data
							HttpConnectionParams.setSoTimeout(params1,
									timoutSocket);

							HttpClient httpClient = new DefaultHttpClient(
									params1);
							HttpPost httppost = new HttpPost(
									"http://67.194.112.231/playSong.php");
							List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
							namevaluepairs.add(new BasicNameValuePair("json",
									songName));
							httppost.setEntity(new UrlEncodedFormEntity(
									namevaluepairs));
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							response = httpClient.execute(httppost,
									responseHandler);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
