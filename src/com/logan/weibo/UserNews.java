package com.logan.weibo;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.logan.R;
import com.logan.weibo.adapter.ListViewAdapter;
import com.weibo.net.WeiboException;

public class UserNews extends BaseTimeLine {
	private final String TAG = "UserNews";
	ListViewAdapter sa = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setSelectedFooterTab(2);

		titleTV.setText("微博动态");
		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Animation hyperspaceJump = AnimationUtils.loadAnimation(
						UserNews.this, R.anim.refresh_bt);
				LinearInterpolator lir = new LinearInterpolator();
				hyperspaceJump.setInterpolator(lir);
				refreshBtn.startAnimation(hyperspaceJump);
				Runnable r = new Runnable() {
					@Override
					public void run() {
						while (!Thread.currentThread().isInterrupted()) {
							try {
								Thread.sleep(1000);
								refreshBtn.clearAnimation();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							}
						}
					}
				};
				Thread td = new Thread(r);
				td.start();

				try {
					sa = getWeiBoData(getPublicTimeLine());
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WeiboException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sa.notifyDataSetInvalidated();
				sa.notifyDataSetChanged();
				lv.setAdapter(sa);
				final ProgressDialog myDialog = ProgressDialog.show(
						UserNews.this, "稍等", "数据加载中", true, true);
				new Thread() {
					@Override
					public void run() {
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						myDialog.dismiss();
					}
				}.start();

			}
		});

		statushow();
	}

	private void statushow() {
		Handler handler = new Handler();
		Runnable mTasks = new Runnable() {
			@Override
			public void run() {

				lv.setDivider(null);
				try {
					lv.setAdapter(getWeiBoData(getPublicTimeLine()));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Thread th = new Thread();
		th.start();
		final ProgressDialog myDialog = ProgressDialog.show(UserNews.this,
				"稍等", "亲，正在努力加载数据。。。", true, true);
		handler.post(mTasks);

		new Thread() {
			@Override
			public void run() {
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myDialog.dismiss();
			}
		}.start();
	}
}