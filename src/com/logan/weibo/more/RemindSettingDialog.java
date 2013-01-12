package com.logan.weibo.more;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.logan.R;

public class RemindSettingDialog extends Activity{

	private CheckBox remind1=null;
	private CheckBox remind2=null;
	private CheckBox remind3=null;
	private CheckBox remind4=null;
	private CheckBox remind5=null;
	private Button closeButton=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_setting_remind);
		remind1=(CheckBox)findViewById(R.id.checkBox1);
		remind1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("remind1 yes");
				}
				else{
					System.out.println("remind1 not");
				}
			}
		}); 
		
		remind2=(CheckBox)findViewById(R.id.checkBox2);
		remind2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("remind2 yes");
				}
				else{
					System.out.println("remind2 not");
				}
			}
		}); 
		
		remind3=(CheckBox)findViewById(R.id.checkBox3);
		remind3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("remind3 yes");
				}
				else{
					System.out.println("remind3 not");
				}
			}
		});
		
		remind4=(CheckBox)findViewById(R.id.checkBox4);
		remind4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("remind4 yes");
				}
				else{
					System.out.println("remind4 not");
				}
			}
		});
		
		remind5=(CheckBox)findViewById(R.id.checkBox5);
		remind5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("remind5 yes");
				}
				else{
					System.out.println("remind5 not");
				}
			}
		});
		
		closeButton=(Button)findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new ColseButtonListener());
	}
	
	class ColseButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("close");
			finish();
		}
		
	}
}
