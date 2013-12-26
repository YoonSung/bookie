package com.bookie.www;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splash extends Activity {

	WaitingHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		handler = new WaitingHandler(Splash.this);
		handler.sendEmptyMessageDelayed(0, 500);
		
	}
	
	
	static class WaitingHandler extends Handler {

		private final Splash activity;

		WaitingHandler(Splash splash) {
			activity = splash;
		}

		@Override
		public void handleMessage(Message msg) {
			activity.startActivity(new Intent(activity, Main.class));
			activity.finish();
		}
	}

	
}
