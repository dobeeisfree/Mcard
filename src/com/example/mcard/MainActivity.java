package com.example.mcard;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost;


public class MainActivity extends TabActivity implements OnTabChangeListener{
	TabHost mTab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		startActivity(new Intent(this,splash.class));
		
		mTab=getTabHost();
		mTab.setOnTabChangedListener(this);
		TabHost.TabSpec spec;
		Intent intent;
		
		// 첫번째 탭
	
		Drawable img1 = getResources().getDrawable(R.drawable.tab_one2);
		intent = new Intent(this, TabOne.class);
		spec = mTab.newTabSpec("M").setIndicator("", img1).setContent(intent);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //탭 클릭시 갱신
		mTab.addTab(spec);
		
		// 두번째 탭
		Drawable img2 = getResources().getDrawable(R.drawable.tab_two2);
		intent = new Intent(this, TabTwo.class);
		spec = mTab.newTabSpec("Card").setIndicator("", img2).setContent(intent);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //탭 클릭시 갱신
		mTab.addTab(spec);
		
		// 세번째 탭
		Drawable img3 = getResources().getDrawable(R.drawable.tab_three2);
		intent = new Intent(this, TabThree.class);
		spec = mTab.newTabSpec("Driector").setIndicator("", img3).setContent(intent);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //탭 클릭시 갱신
		mTab.addTab(spec);
		
		// 색칠하기
		for(int i=0;i<mTab.getTabWidget().getChildCount();i++){
			mTab.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffb413"));
		}
		mTab.getTabWidget().setCurrentTab(0);
		mTab.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#fff500"));
	}
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		//Tab 색 변경
		for(int i=0;i<mTab.getTabWidget().getChildCount();i++){
			mTab.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffb413"));
		}
		mTab.getTabWidget().getChildAt(mTab.getCurrentTab()).setBackgroundColor(Color.parseColor("#fff500"));
	}
	@Override
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
