package com.example.dlogdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.dlog.android.constants.Constants.LogMode;
import com.dlog.android.database.DLogDatabase;
import com.dlog.android.util.DLog;
import com.dlog.android.util.DLogUtil;

public class DLogDemoActivity extends Activity {

	DLogUtil dLogUtil=DLogUtil.INSTANCE;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dlog_demo);
		DLogDatabase database=new DLogDatabase(getApplication());
		dLogUtil.initializeLog(getBaseContext(), LogMode.DEBUG);
		
		DLog.e("------------------->","-------->This is  just a demo;");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dlog_demo, menu);
		return true;
	}

	public void writeToLog(View view)
	{
		Log.e("#################>","------------------>"+dLogUtil.getAllLogs());
	}
}
