package com.example.mcard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.example.mcard.TabThree.onListViewLongClick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
// 텍스트를 다 입력한후
// 버튼을 누르면 다  에딧텍스트들이 텍스트뷰로 전환하고
// 그것을 스크린 샷을 찍어서 다른 폴더에 저장한다
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class makeA extends Activity {
	EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, et10;
	TextView t1, t2,t3,t4,t5,t6,t7,t8,t9,t10;
	ImageView saveAll;

	String three;
	static Bitmap output;
	
	int count=0; //터치 수를 재기 위해
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.two1);
	    // TODO Auto-generated method stub
	    et1=(EditText)findViewById(R.id.editText1);
	    et2=(EditText)findViewById(R.id.editText2);
	    et3=(EditText)findViewById(R.id.editText3);
	    et4=(EditText)findViewById(R.id.editText4);
	    et5=(EditText)findViewById(R.id.editText5);
	    et6=(EditText)findViewById(R.id.editText6);
	    et7=(EditText)findViewById(R.id.editText7);
	    et8=(EditText)findViewById(R.id.editText8);
	    et9=(EditText)findViewById(R.id.editText9);
	    et10=(EditText)findViewById(R.id.editText10);
	    
	    t1=(TextView)findViewById(R.id.textView1);
	    t2=(TextView)findViewById(R.id.textView2);
	    t3=(TextView)findViewById(R.id.textView3);
	    t4=(TextView)findViewById(R.id.textView4);
	    t5=(TextView)findViewById(R.id.textView5);
	    t6=(TextView)findViewById(R.id.textView6);
	    t7=(TextView)findViewById(R.id.textView7);
	    t8=(TextView)findViewById(R.id.textView8);
	    t9=(TextView)findViewById(R.id.textView9);
	    t10=(TextView)findViewById(R.id.textView10);
	    
	    
	    saveAll=(ImageView)findViewById(R.id.imageView2);
	    saveAll.setOnClickListener(new onSaveAll());
	  
	}
	void away(){
		count++;
		et1.setVisibility(View.INVISIBLE);
		String one;
		one= et1.getText().toString();
		t1.setText(one);
		
		et2.setVisibility(View.INVISIBLE);
		String two;
		two= et2.getText().toString();
		t2.setText(two);
		
		et3.setVisibility(View.INVISIBLE);
		three= et3.getText().toString();
		t3.setText(three);
		
		et4.setVisibility(View.INVISIBLE);
		String four;
		four= et4.getText().toString();
		t4.setText(four);
		
		et5.setVisibility(View.INVISIBLE);
		String five;
		five= et5.getText().toString();
		t5.setText(five);
		
		et6.setVisibility(View.INVISIBLE);
		String six;
		six= et6.getText().toString();
		t6.setText(six);
		
		et7.setVisibility(View.INVISIBLE);
		String seven;
		seven= et7.getText().toString();
		t7.setText(seven);
		
		et8.setVisibility(View.INVISIBLE);
		String eight;
		eight= et8.getText().toString();
		t8.setText(eight);
		
		et9.setVisibility(View.INVISIBLE);
		String nine;
		nine= et9.getText().toString();
		t9.setText(nine);
		
		et10.setVisibility(View.INVISIBLE);
		String ten;
		ten= et10.getText().toString();
		t10.setText(ten);
	}
	/*public static Bitmap getScreenShot(Activity currentActivity)
	{
		View rootView = currentActivity.findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
		//rootView.setDrawingCacheEnabled(false);
		rootView.setDrawingCacheEnabled(true);

		Bitmap currentScreenBitmap = rootView.getDrawingCache();
		output = currentScreenBitmap;
		return currentScreenBitmap;
	}*/
	
	//저장버튼 그냥 눌렀을 때
	public class onSaveAll implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(count==0){
				away();
				Toast.makeText(getApplicationContext(), "준비완료", Toast.LENGTH_SHORT).show();
			}
			else if(count==1){ // 캡쳐했다고  알려주기
				count++;
				Bitmap bitmap = takeScreenshot();
				Toast.makeText(getApplicationContext(), "캡쳐완료", Toast.LENGTH_SHORT).show();
				saveBitmap(bitmap);
				
				Intent intent1 = new Intent(makeA.this, makeB.class);
				startActivity(intent1);
	    		finish();
			}
		}
	}
	public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		saveAll.setImageResource(R.drawable.momo);//버튼 이미지 투명한 걸로 덮어서 안보이게끔
		return rootView.getDrawingCache();
	}
	public void saveBitmap(Bitmap bitmap) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";
		String fileName = "Mcard_"+et3.getText().toString()+"_Front.jpg";
		path = path+fileName;			
		try {
			FileOutputStream fos = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);				
			fos.close();
			Toast.makeText(getApplicationContext(), "앞면 끝", Toast.LENGTH_SHORT).show();				
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
		} catch (Exception e) {}
	}
}
