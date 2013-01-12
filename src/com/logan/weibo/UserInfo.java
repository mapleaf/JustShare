package com.logan.weibo;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.logan.R;
import com.logan.util.AsyncImageLoader;
import com.logan.util.WebImageBuilder;
import com.weibo.net.WeiboException;

public class UserInfo extends BottomBaseActivity {
	private AsyncImageLoader imageLoader = new AsyncImageLoader();
	private final String TAG = "UserInfo";
	private ImageView userHead = null;
	private TextView userName = null;
	private TextView genderOfUser = null;
	private TextView locationOfUser = null;
	private TextView descriptionOfUser = null;

	private Button mblogNumBtn = null;
	private Button fansNumBtn = null;
	private Button guanzhuNumBtn = null;

	private String jsonData;
	private JSONObject jsonObj = null;

	private String name = null;
	private String headerImageUrl = null;
	private String gender = null;
	private String location = null;
	private String description = null;
	// followers_count:
	private String followers_count = null;
	// statuses_count:
	private String statuses_count = null;
	// friends_count:
	private String friends_count = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setSelectedFooterTab(3);

		initComp();
		postForData();
	}


	private void postForData() {
		try {
			jsonData = getUserInfo();
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
		if (jsonData != null) {

			try {
				jsonObj = new JSONObject(jsonData);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		bindData();
	}

	private void bindData() {
		try {
			name = jsonObj.getString("name");
			location = jsonObj.getString("location");
			gender = jsonObj.getString("gender");
			headerImageUrl = jsonObj.getString("profile_image_url");
			description = jsonObj.getString("description");
			statuses_count = jsonObj.getString("statuses_count");
			followers_count = jsonObj.getString("followers_count");
			friends_count = jsonObj.getString("friends_count");

		} catch (Exception e) {
			Log.v(TAG, "getDataFromJSON:exception");
		}
		userName.setText(name);
		userName.setTextColor(Color.BLACK);
		userName.setTextSize(20);
		locationOfUser.setText(location);
		locationOfUser.setTextColor(Color.BLACK);
		Log.v(TAG, description);
		descriptionOfUser.setText(description);

		if (gender.equals("m"))
			genderOfUser.setText("男");
		else if (gender.equals("f"))
			genderOfUser.setText("女");
		else
			genderOfUser.setText("未设置");
		genderOfUser.setTextColor(Color.BLACK);
		setViewImage(userHead, headerImageUrl);

		String statuses_count_temp = statuses_count
				+ "</font><br><font size='10px' color='#A7A7A7'>微博";
		Spanned localSpanned1 = Html.fromHtml(statuses_count_temp);
		mblogNumBtn.setText(localSpanned1);
		String followers_count_temp = followers_count
				+ "</font><br><font size='10px' color='#A7A7A7'>粉丝";
		Spanned localSpanned2 = Html.fromHtml(followers_count_temp);
		fansNumBtn.setText(localSpanned2);
		String friends_count_temp = friends_count
				+ "</font><br><font size='10px' color='#A7A7A7'>关注";
		Spanned localSpanned3 = Html.fromHtml(friends_count_temp);
		guanzhuNumBtn.setText(localSpanned3);
	}

	public void setViewImage(final ImageView v, String url) {
		Bitmap bitmap = WebImageBuilder.returnBitMap(url);
		v.setImageBitmap(bitmap);
		imageLoader.loadDrawable(url, new AsyncImageLoader.ImageCallback() {
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (imageDrawable != null
						&& imageDrawable.getIntrinsicWidth() > 0) {
					v.setImageDrawable(imageDrawable);
				}
			}
		});
	}

	private void initComp() {
		writeBtn.setVisibility(View.INVISIBLE);
		titleTV.setText("个人资料");
//		refreshBtn.setOnClickListener(new ButtonListener());
		userHead = (ImageView) findViewById(R.id.userHead);
		userName = (TextView) findViewById(R.id.userName);
		genderOfUser = (TextView) findViewById(R.id.sexOfUser);
		locationOfUser = (TextView) findViewById(R.id.locationOfUser);
		descriptionOfUser = (TextView) findViewById(R.id.introductionTV);
		mblogNumBtn = (Button) findViewById(R.id.mblogNumBtn);
//		mblogNumBtn.setOnClickListener(new ButtonListener());
		fansNumBtn = (Button) findViewById(R.id.fansNumBtn);
//		fansNumBtn.setOnClickListener(new ButtonListener());
		guanzhuNumBtn = (Button) findViewById(R.id.guanzhuNumBtn);
//		guanzhuNumBtn.setOnClickListener(new ButtonListener());

	}

//	class ButtonListener implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.mblogNumBtn:
//
//				break;
//			case R.id.fansNumBtn:
//
//				break;
//			case R.id.guanzhuNumBtn:
//
//				break;
//			//
//			// case R.id.refreshBtn:
//			// postForData();
//			// break;
//			}
//		}
//
//	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.weibo_userinfo;
	}


}
