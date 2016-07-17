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
//MyAdapter Ŭ����
//- ����Ʈ�� �������� ���� ���ϴ� ������� �����ϱ� ���� Ŭ����  
//--------------------------------------------------------------------------------------------//
class SMSAdapter extends ArrayAdapter<mymy> {
	LayoutInflater inFlater1;
	
	public SMSAdapter(Context context, int resource) {
		super(context, resource);
		inFlater1 = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	// ����Ʈ�信 ��� �������� �����ش�
	public View getView(int position, View v, ViewGroup parent) {
		View view2 = inFlater1.inflate(R.layout.myitem2, null);
		TextView tvi = (TextView)view2.findViewById(R.id.mmmm); //�޼���
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
	SMSAdapter adapter; // ����Ʈ�信 ������(Person��ü) ���� �����
	

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
    		// ���Ͽ� ����� ������ �� �پ� ����. �̸� �ݺ��ϴٰ� �� �̻� ���� ������ ������ while()�� ����
			while((line = br.readLine()) != null) {     			
    			String[] str = line.split(",");
        		number = str[0];
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		if(number.isEmpty()==false){ //��ȣ ������ �Ǿ�������_�����ؽ�Ʈ ���ֱ�
			tv.setText("Direct me");
			savePhone.setVisibility(View.INVISIBLE);
			savingNumber.setImageResource(R.drawable.momo);
		}
		//---------------------------//
        // ����Ʈ�� ���� ���� �κ�
        //---------------------------//
		lv = (ListView)findViewById(R.id.listView1);
		adapter = new SMSAdapter(this, R.layout.myitem2);
		lv.setAdapter(adapter);
		lv.setOnItemLongClickListener(new onListViewLongClick());
				
		//deleteFile("list.txt"); // list.txt ������ ����
		
        //---------------------------//
        // ���� �б� ���� �κ�
        //---------------------------//
		String line1 = "";
		try {
			InputStream is = openFileInput("listB.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// ���Ͽ� ����� ������ �� �پ� ����. �̸� �ݺ��ϴٰ� �� �̻� ���� ������ ������ while()�� ����
			while((line1 = br.readLine()) != null) {     			
    			String[] str = line1.split(",");
    			myList[numOfmine] = new mymy();
    			myList[numOfmine].mine = str[0];
        		numOfmine++;
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		
		// ����Ʈ�信�� �߰�����
		for(int i =  0; i < numOfmine; i++) {
			adapter.add(myList[i]);  
		}
	}
	//--------------------------------------------------------------------------------------------//
    // ����Ʈ�� ��� ������ �� �Ͼ�� �̺�Ʈ
    // import android.widget.AdapterView.OnItemClickListener; �߰��� ��
    //--------------------------------------------------------------------------------------------//
    public class onListViewLongClick implements OnItemLongClickListener  {
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        	//---------------------------//
            // ���õ� ģ�� �����ϴ� �κ�
            //---------------------------//
        	int index = position;        	
			for(int i = (index+1); i < numOfmine; i++) {
				myList[i-1] = myList[i];
			}
			numOfmine--;
			myList[numOfmine] = null;				
			
        	//---------------------------//
            // ����Ʈ�並 �����ϴ� �κ�
            //---------------------------//
			adapter.clear(); // ������ �� ����� 
			for(int i =  0; i < numOfmine; i++) {
				adapter.add(myList[i]); // �ٽ� �߰���
			}
			
			Toast.makeText(getApplicationContext(), "�����Ϸ�", Toast.LENGTH_SHORT).show();
			
			//---------------------------//
            // ���� ���� ���� �κ�
            //---------------------------//
    	    try {
    	    	// MODE_PRIVATE(�����) - ���� ������ �����, ���� �ٽ� ���ڴ�
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
	//��ȣ�����ϱ� - �� ó������ ��ȣ�ϰ� ����
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
    	    	// MODE_APPEND(�̾��) - ���� ���뿡 �̾ ���ڴ�
    			FileOutputStream fos = openFileOutput("phone.txt", MODE_APPEND);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			bw.write(number+",");			
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }    
		}
	}
	//�޼����� ������
	public class onSend implements OnClickListener{
		@Override
		public void onClick(View v) {
			message= sendM.getText().toString();
			SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
			sendM.setText("");
    		//message="";
    		//�߰�
    		myList[numOfmine]= new mymy(); 
    		myList[numOfmine].mine = message;
    		adapter.add(myList[numOfmine]); // ����Ʈ�信�� �߰�    
     		//---------------------------//
            // ���� ���� �κ�
            //---------------------------//
    	    try {
    	    	// MODE_APPEND(�̾��) - ���� ���뿡 �̾ ���ڴ�
    			FileOutputStream fos = openFileOutput("listB.txt", MODE_APPEND);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			bw.write(myList[numOfmine].mine+"\n");  			
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }    	    
    	    
    	    numOfmine++;
		}
	}
	// �������κ��� ���ڸ� ������ ����Ǵ� �̺�Ʈ (SMSReceiver Ŭ�������� ���� ������ ó����) 
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String message = intent.getStringExtra("MESSAGE");
		String phoneNum = intent.getStringExtra("PHONENUM");
		Toast.makeText(getApplicationContext(), phoneNum+"�κ��� ���ڰ� ����", Toast.LENGTH_SHORT).show();

	}
}
