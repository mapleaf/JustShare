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

/*
 * 对应于“提到我的”
 * 
 * 现在改成了“查看用户自己的微博”
 */
public class UserTimeLine extends BaseTimeLine {

	private final String TAG = "UserTimeLine";

	ListViewAdapter sa = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setSelectedFooterTab(1);

		titleTV.setText("我的微博");

		refreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显示刷新按钮动画
				Animation hyperspaceJump = AnimationUtils.loadAnimation(
						UserTimeLine.this, R.anim.refresh_bt);
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
					sa = getWeiBoData(getUserTimeline());
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
				sa.notifyDataSetChanged();
				lv.setAdapter(sa);
				final ProgressDialog myDialog = ProgressDialog.show(
						UserTimeLine.this, "稍等", "加载中。。。", true, true);
				new Thread() {
					@Override
					public void run() {
						try {
							// 休眠10秒钟
							sleep(500);
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
		// 为了避免程序获取数据时，由于网络原因或者数据量很大而导致程序无响应（ANR），因而使用了异步处理
		Handler handler = new Handler();
		Runnable mTasks = new Runnable() {
			@Override
			public void run() {

				// 去掉两个listview元素之间的分割线
				lv.setDivider(null);
				try {
					lv.setAdapter(getWeiBoData(getUserTimeline()));
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

		handler.post(mTasks);
		/*
		 * 弹出提示框，它主要为了是界面更加友好，实际上它和数据加载没有关系，只是同用户进行交互
		 */
		final ProgressDialog myDialog = ProgressDialog.show(UserTimeLine.this,
				"稍等", "亲，正在努力加载数据。。。", true, true);

		new Thread() {
			@Override
			public void run() {
				try {
					// 休眠10秒钟
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myDialog.dismiss();
			}
		}.start();
	}

}
