package com.logan.weibo.more;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logan.R;

public class CacheSettingDialog extends Activity{

	private Button sureButton=null;
	private Button cancelButton=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_setting_cache);
		sureButton=(Button)findViewById(R.id.surebutton);
		sureButton.setOnClickListener(new SureButtonListener());
		
		cancelButton=(Button)findViewById(R.id.cancelbutton);
		cancelButton.setOnClickListener(new CancelButtonListener());
	}
	
	class SureButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("sure cache");
			finish();
		}
		
	}
	
	class CancelButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("cancel cache");
			finish();
		}
		
	}
}
