package com.logan.weibo.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.logan.R;

public class SettingActivity extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.more_setting);	
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		String key=preference.getKey();
		if(key!=null){
			if(key.equals("setting_cache")){
				Intent intent=new Intent();
				intent.setClass(SettingActivity.this, CacheSettingDialog.class);
				SettingActivity.this.startActivity(intent);
			}
			if(key.equals("setting_remind")){
				Intent intent=new Intent();
				intent.setClass(SettingActivity.this, RemindSettingDialog.class);
				SettingActivity.this.startActivity(intent);
			}
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	
}
