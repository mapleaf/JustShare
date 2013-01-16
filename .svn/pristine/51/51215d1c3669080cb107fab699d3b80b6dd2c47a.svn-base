/*
 * 评论一条微博
 * 对一条微博信息进行评论。请求必须用POST方式提交。
	URL
	http://api.t.sina.com.cn/statuses/comment.(json%7cxml)
	支持格式
	XML/JSON
	HTTP请求方式
	POST
	注意：
	1. 为防止重复提交，发表的评论与上次发表的评论内容相同的时候，将返回400错误。
	2. 如果id及cid不存在，将返回400错误
	3. 如果提供了正确的cid参数，则该接口的表现为回复指定的评论。此时id参数将被忽略。即使cid参数代表的评论不属于id参数代表的微博消息，通过该接口发表的评论信息直接回复cid代表的评论。回复评论的返回结果参见statuses/reply
	4. 同时评论给原微博时，全部评论成功才返回成功，否则返回相应出错信息。
 */
package com.logan.weibo.ui;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logan.R;

public class Comment extends Activity {

	private ImageView writeBackBtn;
	private Button submit;

	private EditText editText;
	private TextView mTextNum;

	private String jsonData;
	private String sign = null;
	private JSONObject jsonObj = null;
	// 保存输入框中的文本内容
	// private SharedPreferences preferences =
	// getSharedPreferences("commentText",
	// MODE_PRIVATE);
	private final int WEIBO_MAX_LENGTH = 140;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_comment);
		mTextNum = (TextView) findViewById(R.id.comment_text_limit);
		writeBackBtn = (ImageView) findViewById(R.id.comment_writeBackBtn);
		writeBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// String text = editText.getText().toString();
				// // 通过preferences得到它的编辑器对象edit
				// Editor edit = preferences.edit();
				// edit.putString("text", text);
				// edit.commit();
				finish();

			}
		});
		// if (preferences != null)
		// editText.setText(preferences.getString("text", null));
		// else
		// editText.setText(null);
		editText = (EditText) findViewById(R.id.microBlog_ed);
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String mText = editText.getText().toString();
				// String mStr;
				int len = mText.length();
				if (len <= WEIBO_MAX_LENGTH) {
					len = WEIBO_MAX_LENGTH - len;
					mTextNum.setTextColor(Color.GRAY);
					if (!submit.isEnabled())
						submit.setEnabled(true);
				} else {
					len = len - WEIBO_MAX_LENGTH;

					mTextNum.setTextColor(Color.RED);
					if (submit.isEnabled())
						submit.setEnabled(false);
				}
				mTextNum.setText(String.valueOf(len));
			}
		});
		submit = (Button) findViewById(R.id.sendBtn);
		submit.setOnClickListener(new zhuanfaListener());
		this.getSharedPreferences("parameters", Context.MODE_PRIVATE);
	}

	class zhuanfaListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			String content = editText.getText().toString();

			Intent intent = getIntent();
			intent.getStringExtra("weiBoID");

//			String sina_access_token = pres.getString("sina_access_token", "");
//			String sina_access_secret = pres
//					.getString("sina_access_secret", "");

			//			jsonData = new Weibo().comment(url, httpMethod, sina_access_token,
//					sina_access_secret, null, null, content, null, weiBoID,
//					null);
			if ("400".equals(jsonData)) {
				sign = "评论重复发送！";

			} else {
				try {
					jsonObj = new JSONObject(jsonData);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (jsonObj.optString("text").equals(content)
						&& !TextUtils.isEmpty(jsonObj.optString("text"))) {
					sign = "评论成功！";
				} else {
					sign = "评论失败或评论为空！请检查……";
				}
			}
			Toast.makeText(Comment.this, sign, Toast.LENGTH_SHORT).show();
			finish();
		}

	}
}