package com.logan.weibo.ui;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logan.R;
import com.logan.util.AsyncImageLoader;
import com.logan.util.QAsyncImageLoader;
import com.logan.util.QAsyncImageLoader.ImageCallback;
import com.logan.util.TimeUtil;
import com.logan.util.WebImageBuilder;
import com.logan.weibo.bean.Status;
import com.logan.weibo.bean.User;

public class StatusDetail extends Activity {
	private final String TAG = "StatusDetail";
	private QAsyncImageLoader imageLoader = new QAsyncImageLoader();
	private AsyncImageLoader mImageLoader = new AsyncImageLoader();
	ImageView back = null;

	ImageView profile_image = null;
	TextView name = null;
	TextView text = null;
	ImageView microBlogImage = null;
	TextView source = null;
	TextView created_at = null;
	TextView comments = null;
	TextView rt = null;
	ImageView verified = null;

	LinearLayout ll_btn = null;
	LinearLayout ll_lyt = null;
	TextView retweeted_status_text = null;
	ImageView retweeted_status_microBlogImage = null;

	Status mStatus = null;
			
	String httpMethod = "GET";
	String position = null;

	String num = null;

	private void initViews() {
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		profile_image = (ImageView) findViewById(R.id.status_profile_image);
		name = (TextView) findViewById(R.id.status_screen_name);
		text = (TextView) findViewById(R.id.status_text);
		microBlogImage = (ImageView) findViewById(R.id.status_microBlogImage);
		source = (TextView) findViewById(R.id.status_from);
		created_at = (TextView) findViewById(R.id.status_created_at);
		verified = (ImageView) findViewById(R.id.status_vipImage);
		comments = (TextView) findViewById(R.id.status_commentsCount);
		rt = (TextView) findViewById(R.id.status_repostsCount);
		// ll_btn = (LinearLayout) findViewById(R.id.status_countLL);
		// LayoutInflater inflater = (LayoutInflater)
		// getSystemService(LAYOUT_INFLATER_SERVICE);
		// View inflater_view = inflater.inflate(R.layout.weibo_singleweibo,
		// null);
		ll_lyt = (LinearLayout) findViewById(R.id.status_retweeted_status_ll);
		retweeted_status_microBlogImage = (ImageView) findViewById(R.id.status_retweeted_status_microBlogImage);
		retweeted_status_text = (TextView) findViewById(R.id.status_retweeted_status_text);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_statusdetail);
		Intent intent = getIntent();
		mStatus = (Status) intent.getSerializableExtra("detail");
		initViews();
		setData2Views(mStatus);

	}

	private void setData2Views(Status status) {
		Log.v(TAG, status.toString());
		User user = status.getUser();
		Status retweetedStatus = null;
		if (status.getRetweetedStatus() != null) retweetedStatus = status.getRetweetedStatus();
		String profile_image_url2 = user.getProfileImageUrl();
		String name2 = user.getName();
		String text2 = status.getText();
		String microBlogImage2 = status.getBmiddlePic();
		String source2 = status.getSource().getName();
		Boolean verified2 = user.isVerified();
		int statuses_count2 = status.getCommentsCount();
		int followers_count2 = status.getRepostsCount();
		String created_at2 = TimeUtil.converTime(new Date(status.getCreatedAt()).getTime() / 1000);
		String retweeted_status_text2 = "";
		if (retweetedStatus != null) {
			retweeted_status_text2 = retweetedStatus.getText();
			Log.v(TAG, "retweeted_status_text:  " + retweeted_status_text2);
		} else {
			// do nothing
		}
		String retweeted_status_microBlogImage2 = "";
		if (retweetedStatus != null) {
			retweeted_status_microBlogImage2 = retweetedStatus.getBmiddlePic();
			Log.v(TAG, "retweeted_status_microBlogImage:  " + retweeted_status_microBlogImage2);
		} else {
			// do nothing
		}
		if (!profile_image_url2.equals("")) setViewImage(profile_image, profile_image_url2);
		name.setText(name2);
		text.setText(text2);
		if (!microBlogImage2.equals("")) setQViewImage(microBlogImage, microBlogImage2);
		else microBlogImage.setVisibility(View.GONE);
		source.setText(source2);
		created_at.setText(created_at2);
		comments.setText(followers_count2 + "");
		rt.setText(statuses_count2 + "");
		if (verified2) verified.setVisibility(View.VISIBLE); else verified.setVisibility(View.GONE);

		if (!retweeted_status_microBlogImage2.equals("")) {
			retweeted_status_microBlogImage.setVisibility(View.VISIBLE);
			setQViewImage(retweeted_status_microBlogImage,
					retweeted_status_microBlogImage2);
		} else retweeted_status_microBlogImage.setVisibility(View.GONE);

		if (!retweeted_status_text2.equals("")) {
			retweeted_status_text.setVisibility(View.VISIBLE);
			retweeted_status_text.setText(retweeted_status_text2);
		} else retweeted_status_text.setVisibility(View.GONE);
		if (!retweeted_status_microBlogImage2.equals("") || !retweeted_status_text2.equals("")) ll_lyt.setVisibility(View.VISIBLE);
		else ll_lyt.setVisibility(View.GONE);

		Log.v(TAG, "verified:  " + verified2);
		Log.v(TAG, "statuses_count:  " + statuses_count2);
		Log.v(TAG, "followers_count:  " + followers_count2);
		Log.v(TAG, "created_at2:  " + created_at2);
		Log.v(TAG, "profile_image_url:  " + profile_image_url2);
		Log.v(TAG, "name:  " + name2);
		Log.v(TAG, "text:  " + text2);
		Log.v(TAG, "microBlogImage:  " + microBlogImage2);
		Log.v(TAG, "source:  " + source2);

	}

	public void setQViewImage(final ImageView v, String url) {
		// Bitmap bitmap = WebImageBuilder.returnBitMap(url);
		// v.setImageBitmap(bitmap);
		imageLoader.loadDrawable(url, v, new ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, ImageView imageView, String imageUrl) {
				v.setImageDrawable(imageDrawable);
			}
		});
	}

	public void setViewImage(final ImageView v, String url) {
		Bitmap bitmap = WebImageBuilder.returnBitMap(url);
		v.setImageBitmap(bitmap);
		mImageLoader.loadDrawable(url, new AsyncImageLoader.ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (imageDrawable != null
						&& imageDrawable.getIntrinsicWidth() > 0) {
					v.setImageDrawable(imageDrawable);
				}
			}
		});
	}
}
