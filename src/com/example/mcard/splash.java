package com.example.mcard;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splash extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash);
        
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
 
            @Override
            public void run() {
            	Toast.makeText(getApplicationContext(), "Hi, Director!", Toast.LENGTH_SHORT).show();
                finish();       // 3 초후 이미지를 닫아버림
            }
        }, 3000);
	    // TODO Auto-generated method stub
	}

}
