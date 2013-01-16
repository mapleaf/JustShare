package com.logan.weibo.ui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.logan.R;
import com.logan.util.QAsyncImageLoader;
import com.logan.util.QAsyncImageLoader.ImageCallback;
import com.logan.util.WebImageBuilder;
import com.logan.weibo.bean.QStatus;

public class QStatusDetail extends Activity {
	private final String TAG = "QStatusDetail";

	private QAsyncImageLoader imageLoader = new QAsyncImageLoader();
	ImageView back = null;

	ImageView head;// 头像
	TextView nick;// 用户名
	TextView origText;// 微博文本
	ImageView image;// 微博图片
	TextView from;// 平台来源
	ImageView isVip;
	TextView mcount;
	TextView count;
	TextView timeStamp;
	// --------转发微博-----------------
	TextView source_text;// 转发微博文字
	ImageView source_image;// 转发微博图片
	View source_ll;// 转发微博父容器
	Boolean isVisible = true;
	String httpMethod = "GET";
	QStatus mStatus = null;

	private void initViews() {
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		head = (ImageView) findViewById(R.id.status_profile_image);
		nick = (TextView) findViewById(R.id.status_screen_name);
		origText = (TextView) findViewById(R.id.status_text);
		image = (ImageView) findViewById(R.id.status_microBlogImage);
		from = (TextView) findViewById(R.id.status_from);
		timeStamp = (TextView) findViewById(R.id.status_created_at);
		isVip = (ImageView) findViewById(R.id.status_vipImage);
		mcount = (TextView) findViewById(R.id.status_commentsCount);
		count = (TextView) findViewById(R.id.status_repostsCount);
		// ------------转发微博------------------------
		source_image = (ImageView) findViewById(R.id.status_retweeted_status_microBlogImage);
		source_text = (TextView) findViewById(R.id.status_retweeted_status_text);
		source_ll = findViewById(R.id.status_retweeted_status_ll);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_statusdetail);
		initViews();
		Intent intent = getIntent();
		mStatus = (QStatus) intent.getSerializableExtra("detail");
		try {
			setData2Views(mStatus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setData2Views(QStatus status) throws JSONException {
		nick.setText(status.getNick());
		// if (!origText.equals("") && origText != null)
		origText.setText(status.getOrigText());
		// else
		// origText.setText(text);
		if (!status.getImage().equals("")) {
			image.setVisibility(View.VISIBLE);
			setViewImage(image, status.getImage());
		} else {
			image.setVisibility(View.GONE);
		}
		from.setText(status.getFrom());

		Drawable cachedImage = null;
		if (!status.getHead().equals("") && status.getHead() != null) {
			cachedImage = imageLoader.loadDrawable(status.getHead(), head,
					new ImageCallback() {
						@Override
						public void imageLoaded(Drawable imageDrawable,
								ImageView imageView, String imageUrl) {
							imageView.setImageDrawable(imageDrawable);
						}
					});
		}
		if (cachedImage == null) {
			head.setImageResource(R.drawable.icon);
		} else {
			head.setImageDrawable(cachedImage);
		}

		mcount.setText(status.getMcount());
		count.setText(status.getCount());
		timeStamp.setVisibility(View.VISIBLE);
		if (!status.getCreated_at().equals(""))
			timeStamp.setText(status.getCreated_at());
		else
			timeStamp.setVisibility(View.INVISIBLE);
		if (status.getIsVip() == 1)
			isVip.setVisibility(View.VISIBLE);
		else
			isVip.setVisibility(View.INVISIBLE);

		// ------------------------转发微博----------------------------------

		if (!status.getSource_image().equals("") && status.getSource_image() != null) {
			source_image.setVisibility(View.VISIBLE);
			setViewImage(source_image, status.getSource_image());
		} else
			source_image.setVisibility(View.GONE);
		if (!status.getSource_text().equals("")) {
			source_text.setVisibility(View.VISIBLE);
			source_text.setText(status.getSource_text());
		} else
			source_text.setVisibility(View.GONE);

		if (!isVisible)
			source_ll.setVisibility(View.GONE);
		else
			source_ll.setVisibility(View.VISIBLE);

	}

	public void setViewImage(final ImageView v, String url) {
		Bitmap bitmap = WebImageBuilder.returnBitMap(url);
		v.setImageBitmap(bitmap);
		imageLoader.loadDrawable(url, v, new ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable,
					ImageView imageView, String imageUrl) {
				imageView.setImageDrawable(imageDrawable);
			}
		});
	}

}
