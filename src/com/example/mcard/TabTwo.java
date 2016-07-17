package com.example.mcard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TabTwo extends Activity {
	ImageView createA, createB, sth;
	EditText qMemo;
	String qqq; TextView tv;
	
	int count=0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab2);
	    // TODO Auto-generated method stub
	    createA=(ImageView)findViewById(R.id.imageView1);
	    createA.setOnClickListener(new onA());
	    createB=(ImageView)findViewById(R.id.imageView2);
	    createB.setOnClickListener(new onB());
	    sth=(ImageView)findViewById(R.id.imageView4);
	    sth.setOnClickListener(new onSth());
	    qMemo=(EditText)findViewById(R.id.editText1);
	    tv=(TextView)findViewById(R.id.textView1);
	    
	    //---------------------------//
        // 파일 읽기 관련 부분
        //---------------------------//
	    String line = "";
		try {
			InputStream is = openFileInput("qmemo.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// 파일에 저장된 내용을 한 줄씩 읽음. 이를 반복하다가 더 이상 읽을 내용이 없으면 while()문 종료
			while((line = br.readLine()) != null) {     			
    			String[] str = line.split(",");
    			qqq = str[0];
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
	}
	public class onSth implements OnClickListener{
		@Override
		public void onClick(View v) {
			count++;
			if(count==1){//추가
				//에딧텍스트가 있을 때, qq에 저장하구
				//그 담에 텍스트뷰에 저장하면서
				//그와 동시에 에딕텍스트가 보여져야함
				qqq = qMemo.getText().toString();
				tv.setText(qqq);
				qMemo.setVisibility(View.INVISIBLE);

				//---------------------------//
				try {
					// MODE_APPEND(이어쓰기) - 기존 내용에 이어서 쓰겠다
					FileOutputStream fos = openFileOutput("qmemo.txt", MODE_APPEND);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					bw.write(qqq+",");			
					bw.close();
					fos.close();
				} catch (Exception e) { }
				count=-2;
			}
			else if(count==-1){//수정
				//에딧텍스트가 생겨나며 텍스트뷰가 사라진다
				qMemo.setVisibility(View.VISIBLE);
				tv.setText("");
				//---------------------------//
				try {
					// MODE_APPEND(이어쓰기) - 기존 내용에 이어서 쓰겠다
					FileOutputStream fos = openFileOutput("qmemo.txt", MODE_APPEND);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					bw.write(qqq+",");			
					bw.close();
					fos.close();
				} catch (Exception e) { }
				count=0;
			}
		}
	}
	public class onA implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent1 = new Intent(TabTwo.this, makeA.class);
    		startActivity(intent1); 
		}
	}
	public class onB implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent2 = new Intent(TabTwo.this, makeB.class);
    		startActivity(intent2); 
		}
	}
}
