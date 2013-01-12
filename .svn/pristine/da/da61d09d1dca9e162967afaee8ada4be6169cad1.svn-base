package com.logan.weibo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.logan.R;
import com.logan.weibo.more.AboutAcitvity;
import com.logan.weibo.more.AccountAcitvity;
import com.logan.weibo.more.FeedbackActivity;
import com.logan.weibo.more.HelpAcitvity;
import com.logan.weibo.more.SettingActivity;
import com.logan.weibo.more.ThemeDialog;

public class More extends BottomBaseActivity {

	private RelativeLayout mycount = null;
	private RelativeLayout theme = null;
	private CheckBox picture = null;
	private RelativeLayout weibo = null;
	private RelativeLayout setting = null;
	private RelativeLayout feedback = null;
	private RelativeLayout help = null;
	private RelativeLayout about = null;

	// private RelativeLayout headerBar = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setSelectedFooterTab(4);

		// headerBar=(RelativeLayout) findViewById(R.id.relativeLayout1);
		// headerBar.setVisibility(View.INVISIBLE);
		writeBtn.setVisibility(View.INVISIBLE);
		titleTV.setText("更多");
		refreshBtn.setVisibility(View.INVISIBLE);

		mycount = (RelativeLayout) findViewById(R.id.mycount);
		mycount.setOnClickListener(new MycountListener());

		picture = (CheckBox) findViewById(R.id.pictureOrNot);
		picture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {

				} else {

				}
			}
		});

		theme = (RelativeLayout) findViewById(R.id.theme);
		theme.setOnClickListener(new ThemeListener());

		weibo = (RelativeLayout) findViewById(R.id.weibo);
		weibo.setOnClickListener(new WeiboListener());

		setting = (RelativeLayout) findViewById(R.id.setting);
		setting.setOnClickListener(new SettingListener());

		feedback = (RelativeLayout) findViewById(R.id.feedback);
		feedback.setOnClickListener(new FeedbackListener());

		help = (RelativeLayout) findViewById(R.id.help);
		help.setOnClickListener(new HelpListener());

		about = (RelativeLayout) findViewById(R.id.about);
		about.setOnClickListener(new AboutListener());

	}

	class MycountListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(More.this, AccountAcitvity.class);
			More.this.startActivity(intent);
		}

	}

	class ThemeListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("theme");
			Intent intent = new Intent();
			intent.setClass(More.this, ThemeDialog.class);
			More.this.startActivity(intent);
		}

	}

	class WeiboListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}

	}

	class SettingListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(More.this, SettingActivity.class);
			More.this.startActivity(intent);
		}

	}

	class FeedbackListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(More.this, FeedbackActivity.class);
			More.this.startActivity(intent);
		}

	}

	class HelpListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(More.this, HelpAcitvity.class);
			More.this.startActivity(intent);
		}

	}

	class AboutListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(More.this, AboutAcitvity.class);
			More.this.startActivity(intent);
		}

	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.weibo_more;
	}


}
