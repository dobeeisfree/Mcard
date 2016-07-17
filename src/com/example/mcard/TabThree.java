package com.example.mcard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.example.mcard.TabOne.onListViewLongClick;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
class mymy{
	String mine;}
//--------------------------------------------------------------------------------------------//
//MyAdapter 클래스
//- 리스트뷰 아이템을 내가 원하는 모양으로 관리하기 위한 클래스  
//--------------------------------------------------------------------------------------------//
class SMSAdapter extends ArrayAdapter<mymy> {
	LayoutInflater inFlater1;
	
	public SMSAdapter(Context context, int resource) {
		super(context, resource);
		inFlater1 = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	// 리스트뷰에 어떻게 보여질지 정해준다
	public View getView(int position, View v, ViewGroup parent) {
		View view2 = inFlater1.inflate(R.layout.myitem2, null);
		TextView tvi = (TextView)view2.findViewById(R.id.mmmm); //메세지
		int nanda = position;
		mymy mtom = this.getItem(nanda);		
		tvi.setText(mtom.mine); 
		
		return view2;
	}

}
public class TabThree extends Activity {
	EditText savePhone, sendM;
	ImageView savingNumber, sendD;
	TextView tv; String number, message;
	
	ListView lv;
	SMSAdapter adapter; // 리스트뷰에 데이터(Person객체) 간의 연결고리
	

	int WOW = 100;
	mymy[] myList = new mymy[WOW];	
	int numOfmine; 
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab3);
	    // TODO Auto-generated method stub
	    savePhone=(EditText)findViewById(R.id.editText1);
	    sendM=(EditText)findViewById(R.id.editText2);
	    
	    savingNumber=(ImageView)findViewById(R.id.imageView2);
	    savingNumber.setOnClickListener(new onSaveYourNumber());
	    sendD=(ImageView)findViewById(R.id.imageView3);
	    sendD.setOnClickListener(new onSend());
	    
	    tv=(TextView)findViewById(R.id.textView1);
	    
	    String line = "";
		try {
			InputStream is = openFileInput("phone.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// 파일에 저장된 내용을 한 줄씩 읽음. 이를 반복하다가 더 이상 읽을 내용이 없으면 while()문 종료
			while((line = br.readLine()) != null) {     			
    			String[] str = line.split(",");
        		number = str[0];
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		if(number.isEmpty()==false){ //번호 저장이 되어있으면_에딧텍스트 없애기
			tv.setText("Direct me");
			savePhone.setVisibility(View.INVISIBLE);
			savingNumber.setImageResource(R.drawable.momo);
		}
		//---------------------------//
        // 리스트뷰 관련 설정 부분
        //---------------------------//
		lv = (ListView)findViewById(R.id.listView1);
		adapter = new SMSAdapter(this, R.layout.myitem2);
		lv.setAdapter(adapter);
		lv.setOnItemLongClickListener(new onListViewLongClick());
				
		//deleteFile("list.txt"); // list.txt 파일을 삭제
		
        //---------------------------//
        // 파일 읽기 관련 부분
        //---------------------------//
		String line1 = "";
		try {
			InputStream is = openFileInput("listB.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// 파일에 저장된 내용을 한 줄씩 읽음. 이를 반복하다가 더 이상 읽을 내용이 없으면 while()문 종료
			while((line1 = br.readLine()) != null) {     			
    			String[] str = line1.split(",");
    			myList[numOfmine] = new mymy();
    			myList[numOfmine].mine = str[0];
        		numOfmine++;
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		
		// 리스트뷰에도 추가해줌
		for(int i =  0; i < numOfmine; i++) {
			adapter.add(myList[i]);  
		}
	}
	//--------------------------------------------------------------------------------------------//
    // 리스트뷰 길게 눌렀을 때 일어나는 이벤트
    // import android.widget.AdapterView.OnItemClickListener; 추가할 것
    //--------------------------------------------------------------------------------------------//
    public class onListViewLongClick implements OnItemLongClickListener  {
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        	//---------------------------//
            // 선택된 친구 삭제하는 부분
            //---------------------------//
        	int index = position;        	
			for(int i = (index+1); i < numOfmine; i++) {
				myList[i-1] = myList[i];
			}
			numOfmine--;
			myList[numOfmine] = null;				
			
        	//---------------------------//
            // 리스트뷰를 갱신하는 부분
            //---------------------------//
			adapter.clear(); // 모조리 다 지우고 
			for(int i =  0; i < numOfmine; i++) {
				adapter.add(myList[i]); // 다시 추가함
			}
			
			Toast.makeText(getApplicationContext(), "삭제완료", Toast.LENGTH_SHORT).show();
			
			//---------------------------//
            // 파일 쓰기 관련 부분
            //---------------------------//
    	    try {
    	    	// MODE_PRIVATE(덮어쓰기) - 기존 내용은 지우고, 새로 다시 쓰겠다
    			FileOutputStream fos = openFileOutput("listB.txt", MODE_PRIVATE);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			for(int i = 0; i < numOfmine; i++) {
    				bw.write(myList[i].mine+"\n");
    			}
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }
    	    
        	return true;
        } 
	};	
	//번호저장하기 - 맨 처음에만 유호하게 만듦
	public class onSaveYourNumber implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			number = savePhone.getText().toString();
			tv.setText("Direct me");
			savePhone.setVisibility(View.INVISIBLE);
			savingNumber.setImageResource(R.drawable.momo);
			//---------------------------//
    	    try {
    	    	// MODE_APPEND(이어쓰기) - 기존 내용에 이어서 쓰겠다
    			FileOutputStream fos = openFileOutput("phone.txt", MODE_APPEND);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			bw.write(number+",");			
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }    
		}
	}
	//메세지를 보낼때
	public class onSend implements OnClickListener{
		@Override
		public void onClick(View v) {
			message= sendM.getText().toString();
			SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
			sendM.setText("");
    		//message="";
    		//추가
    		myList[numOfmine]= new mymy(); 
    		myList[numOfmine].mine = message;
    		adapter.add(myList[numOfmine]); // 리스트뷰에도 추가    
     		//---------------------------//
            // 파일 쓰기 부분
            //---------------------------//
    	    try {
    	    	// MODE_APPEND(이어쓰기) - 기존 내용에 이어서 쓰겠다
    			FileOutputStream fos = openFileOutput("listB.txt", MODE_APPEND);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			bw.write(myList[numOfmine].mine+"\n");  			
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }    	    
    	    
    	    numOfmine++;
		}
	}
	// 상대방으로부터 문자를 받으면 실행되는 이벤트 (SMSReceiver 클래스에서 받은 정보를 처리함) 
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String message = intent.getStringExtra("MESSAGE");
		String phoneNum = intent.getStringExtra("PHONENUM");
		Toast.makeText(getApplicationContext(), phoneNum+"로부터 문자가 도착", Toast.LENGTH_SHORT).show();

	}
}
