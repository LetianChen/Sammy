package com.lt.droidTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle bd) {
		super.onCreate(bd);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(1000);
				} 
				catch(Exception e){
					e.printStackTrace();
				}
				finally{
					Intent strtMain = new Intent("com.lt.droidTest.MAINACTIVITY");
					//intent: find the actionname in manifest
					startActivity(strtMain);
				}
				
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	

}
