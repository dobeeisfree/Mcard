package com.example.mcard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
//--------------------------------------------------------------------------------------------//
//Person Ŭ���� 
//- �ּҷϿ� �߰��� ������� �����͸� �����ϱ� ���� Ŭ���� 
//--------------------------------------------------------------------------------------------//
class Person {
	String title;
	String tags;
	String contents;
	String message;
}
//--------------------------------------------------------------------------------------------//
//MyAdapter Ŭ����
//- ����Ʈ�� �������� ���� ���ϴ� ������� �����ϱ� ���� Ŭ����  
//--------------------------------------------------------------------------------------------//
class MyAdapter extends ArrayAdapter<Person> {
	LayoutInflater inFlater;
	
	public MyAdapter(Context context, int resource) {
		super(context, resource);
		inFlater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	// ����Ʈ�信 ��� �������� �����ش�
	public View getView(int position, View v, ViewGroup parent) {
		// ����Ʈ�信 �������� ���̾ƿ��� myitem (TextView 3��)
		View view = inFlater.inflate(R.layout.myitem, null);
		TextView tv1 = (TextView)view.findViewById(R.id.textView1); //Ÿ��Ʋ
		TextView tv2 = (TextView)view.findViewById(R.id.textView2); //�±�
		TextView tv3 = (TextView)view.findViewById(R.id.textView3); //����
		
		Person p = this.getItem(position);		
		tv1.setText(p.title); tv2.setText(p.tags); tv3.setText(p.contents);
		
		return view;
	}
}
public class TabOne extends Activity {
	EditText etTitle, etTag, etContent;
	ImageView save;
	ListView lv;
	MyAdapter adapter; // ����Ʈ�信 ������(Person��ü) ���� �����
	
	int MAX = 1000000;
	Person[] personList = new Person[MAX];	
	int numOfPerson; // �߰��� ����� ��
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab1);
	    // TODO Auto-generated method stub
	    save=(ImageView)findViewById(R.id.imageView1);
	    save.setOnClickListener(new onSave());
	    
	    etTitle=(EditText)findViewById(R.id.editText1);
	    etTag=(EditText)findViewById(R.id.editText2);
	    etContent=(EditText)findViewById(R.id.editText3);
	    
	    //---------------------------//
        // ����Ʈ�� ���� ���� �κ�
        //---------------------------//
		lv = (ListView)findViewById(R.id.listView1);
		adapter = new MyAdapter(this, R.layout.myitem);
		lv.setAdapter(adapter);
		// ����Ʈ�� �������� ª�� ������ �� �Ͼ�� �̺�Ʈ�� onListViewLongClick
		lv.setOnItemLongClickListener(new onListViewLongClick());
				
		//deleteFile("list.txt"); // list.txt ������ ����
		
        //---------------------------//
        // ���� �б� ���� �κ�
        //---------------------------//
		String line = "";
		try {
			InputStream is = openFileInput("listed.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// ���Ͽ� ����� ������ �� �پ� ����. �̸� �ݺ��ϴٰ� �� �̻� ���� ������ ������ while()�� ����
			while((line = br.readLine()) != null) {     			
    			String[] str = line.split(",");
        		personList[numOfPerson] = new Person();
        		personList[numOfPerson].title = str[0];
        		personList[numOfPerson].tags = str[1];
        		personList[numOfPerson].contents = str[2];
    			numOfPerson++;
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		
		// ����Ʈ�信�� �߰�����
		for(int i =  0; i < numOfPerson; i++) {
			adapter.add(personList[i]); 
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
			for(int i = (index+1); i < numOfPerson; i++) {
				personList[i-1] = personList[i];
			}
			numOfPerson--;
			personList[numOfPerson] = null;				
			
        	//---------------------------//
            // ����Ʈ�並 �����ϴ� �κ�
            //---------------------------//
			adapter.clear(); // ������ �� ����� 
			for(int i =  0; i < numOfPerson; i++) {
				adapter.add(personList[i]); // �ٽ� �߰���
			}
			
			Toast.makeText(getApplicationContext(), "�����Ϸ�", Toast.LENGTH_SHORT).show();
			
			//---------------------------//
            // ���� ���� ���� �κ�
            //---------------------------//
    	    try {
    	    	// MODE_PRIVATE(�����) - ���� ������ �����, ���� �ٽ� ���ڴ�
    			FileOutputStream fos = openFileOutput("listed.txt", MODE_PRIVATE);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			for(int i = 0; i < numOfPerson; i++) {
    				bw.write(personList[i].title+",");
    				bw.write(personList[i].tags+",");
    				bw.write(personList[i].contents+"\n");
    			}
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }
    	    
        	return true;
        } 
	};	
	
	//--------------------------------------------------------------------------------------------//
	// �߰� ��ư ������ �� �Ͼ�� �̺�Ʈ
	//--------------------------------------------------------------------------------------------//
    public class onSave implements OnClickListener {
    	public void onClick(View v) {
    		//---------------------------//
            // personList�� �߰��ϴ� �κ�
            //---------------------------//
    		personList[numOfPerson] = new Person(); 						// ��ü ����
    		personList[numOfPerson].title = etTitle.getText().toString(); 	// editText�� �Է��� ���ڿ��� name�� ����
    		personList[numOfPerson].tags = etTag.getText().toString();
    		personList[numOfPerson].contents = etContent.getText().toString();
    		
    		adapter.add(personList[numOfPerson]); // ����Ʈ�信�� �߰�    		
    		Toast.makeText(getApplicationContext(), "����Ϸ�", Toast.LENGTH_SHORT).show();
    		
    		etTitle.setText("");
    		etTag.setText("");
    		etContent.setText("");
    		//---------------------------//
            // ���� ���� �κ�
            //---------------------------//
    	    try {
    	    	// MODE_APPEND(�̾��) - ���� ���뿡 �̾ ���ڴ�
    			FileOutputStream fos = openFileOutput("listed.txt", MODE_APPEND);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			bw.write(personList[numOfPerson].title+",");
    			bw.write(personList[numOfPerson].tags+",");
    			bw.write(personList[numOfPerson].contents+"\n");    			
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }    	    
    	    
    	    numOfPerson++;
    	}
    }
}
