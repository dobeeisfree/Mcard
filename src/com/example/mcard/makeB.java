package com.example.mcard;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class makeB extends Activity {
	ImageView getting, saving;
	Bitmap input; EditText memo;
	int count=0; //��ġ ���� ��� ����
	TextView tt;
	String overide;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.two2);
	    // TODO Auto-generated method stub
	    getting = (ImageView)findViewById(R.id.imageView2);
	    getting.setOnClickListener(new onImgGet());
	    saving = (ImageView)findViewById(R.id.imageView3);
	    saving.setOnClickListener(new onImgSave());
	    tt=(TextView)findViewById(R.id.textView1);
	    memo=(EditText)findViewById(R.id.editText1);
	    
	    Toast.makeText(getApplicationContext(), 
				"�޸�����",
				Toast.LENGTH_SHORT).show();

	}
	public class onImgGet implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// ������ ȭ������ �Ѿ�� �κ�
			Intent intent = new Intent(Intent.ACTION_PICK, 
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 1);
		}
	}
	// ������ ȭ�� ������ ����Ǵ� �κ�
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);

		// ���������� ������ �̹��� ��θ� �˾Ƴ���
		String[] str = {MediaStore.Images.Media.DATA};
		Cursor c = getContentResolver().query(data.getData(), str, null, null, null);
		c.moveToFirst();
		String path = c.getString(c.getColumnIndex(str[0]));
		c.close();

		// �̹����� �ʹ� ũ�� �ٿ����� (�޸� ����)
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);

		int MAX_SIZE = 500;
		int sampleSize = 1;		
		if((opts.outWidth > MAX_SIZE) || (opts.outHeight > MAX_SIZE)) {
			int widthRatio = (int)((float)opts.outWidth / MAX_SIZE);
			int heightRatio = (int)((float)opts.outHeight / MAX_SIZE);			
			if(widthRatio > heightRatio) sampleSize = heightRatio;
			else sampleSize = widthRatio;
		}
		opts.inSampleSize = sampleSize;
		opts.inJustDecodeBounds = false;		
		
		// ���������� ������ �̹����� input�� �����Ѵ�
		if(requestCode==1){ 
			input = BitmapFactory.decodeFile(path, opts);
			getting.setImageBitmap(input);
			Toast.makeText(getApplicationContext(), 
					"W:"+input.getWidth()+", H:"+input.getHeight(),
					Toast.LENGTH_SHORT).show();
		}
	}
    // �̹��� ĸ���ϱ�
    public class onImgSave implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(count==0){
				count++;
				memo.setVisibility(View.INVISIBLE);
				overide= memo.getText().toString();
				tt.setText(overide);
				Toast.makeText(getApplicationContext(), "�غ�Ϸ�", Toast.LENGTH_SHORT).show();
			}
			else if(count==1){ // ĸ���ߴٰ�  �˷��ֱ�
				count++;
				Bitmap bitmap = takeScreenshot();
				Toast.makeText(getApplicationContext(), "ĸ�ĿϷ�", Toast.LENGTH_SHORT).show();
				saveBitmap(bitmap);			
				finish();
			}
		}
    	
    }
    public Bitmap takeScreenshot() {
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		saving.setImageResource(R.drawable.momo);//��ư �̹��� ������ �ɷ� ��� �Ⱥ��̰Բ�
		return rootView.getDrawingCache();
	}
	public void saveBitmap(Bitmap bitmap) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";
		String filtering = overide.substring(0,2); //���� 2���ڸ� ���´�
		String fileName = "Mcard_"+filtering+"_Back.jpg";
		path = path+fileName;			
		try {
			FileOutputStream fos = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);				
			fos.close();
			Toast.makeText(getApplicationContext(), "�޸鳡", Toast.LENGTH_SHORT).show();				
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
		} catch (Exception e) {}
	}
}
