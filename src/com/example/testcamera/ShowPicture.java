package com.example.testcamera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

public class ShowPicture extends Activity {

	private static final String TAG = "ShowPicture";
	private ImageView mPicture;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_picture);

		mPicture = (ImageView) findViewById(R.id.picture);
		String fileName = getIntent().getStringExtra(
				TestCameraActivity.KEY_FILENAME);
		// ͼƬ·��
		Log.i(TAG, fileName);
		String path = getFileStreamPath(fileName).getAbsolutePath();

		Display display = getWindowManager().getDefaultDisplay(); // ��ʾ���ߴ�
		float destWidth = display.getWidth();
		float destHeight = display.getHeight();

		// ��ȡ����ͼƬ�ߴ�
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);// ����Ϊtrue��options��Ȼ��Ӧ��ͼƬ��������������Ϊ��ͼƬ�����ڴ�

		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;

		int inSampleSize = 1;
		if (srcHeight > destHeight || srcWidth > destWidth) { // ��ͼƬ���������Ļ����ʱ
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / destHeight);
			} else {
				inSampleSize = Math.round(srcWidth / destWidth);
			}
		}
		options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		BitmapDrawable bDrawable = new BitmapDrawable(getResources(), bitmap);
		mPicture.setImageDrawable(bDrawable);
	}

	@Override
	public void onDestroy() {
		if (!(mPicture.getDrawable() instanceof BitmapDrawable))
			return;
		// �ͷ�bitmapռ�õĿռ�
		BitmapDrawable b = (BitmapDrawable) mPicture.getDrawable();
		b.getBitmap().recycle();
		mPicture.setImageDrawable(null);
	}

}
