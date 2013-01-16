package com.logan.weibo.ui;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.logan.R;
import com.logan.weibo.adapter.QListViewAdapter;
import com.logan.weibo.bean.BaseTimeLine;
import com.logan.weibo.bean.QStatus;

public class QUserNews extends BaseTimeLine {

	private final String TAG = "QUserNews";

	private List<QStatus> statusList = null;
	private String mData = null;
	
	private final int pageSize = 10;
	private final int pageFlag = 0;		//分页标识（0：第一页，1：向下翻页，2向上翻页）
	private final String pageTime = "0";//本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	private final String lastId = "0";
	private int pageSum = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setSelectedFooterTab(2);

		titleTV.setText("微博动态");
		mData =getQMentionsTimeLine(String.valueOf(pageFlag), pageTime, lastId);
		statusList = getQStatusList(mData);
		QListViewAdapter mAdapter = new QListViewAdapter(QUserNews.this, statusList);
		pullToRefreshListView.setAdapter(mAdapter);

	}
}
