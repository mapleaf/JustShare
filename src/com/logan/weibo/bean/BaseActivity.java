package com.logan.weibo.bean;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.logan.util.DBManager;
import com.tencent.weibo.api.StatusesAPI;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;
import com.weibo.net.AccessToken;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;

public abstract class BaseActivity extends Activity {

	public synchronized static OAuthV2 getInstance() {
		if (oAuth == null) {
			oAuth = new OAuthV2(CLIENT_ID, CLIENT_SECRET, TENCENT_REDIRECT_URL);
		}
		return oAuth;
	}

	// ----------------------公用参数--------------------------------//

	private final String TAG = "BaseActivity";
	public static Boolean isProtected = false;

	public static Boolean isSina = false;// 标识符

	public static Boolean isTencent = false;
	public DBManager mDBManager;
	public String token = "";// 这里token即代表Sina中的token，也代表Tencent中的AccessToken
	public String expires_in = "";
	public String plf = "";
	
	
	// ----------------------新浪API接口所需的参数-----------------------------//
	public static final String TOKEN = "com.logan.weibo.token";

	public static final String EXPIRE = "com.logan.weibo.expire";
	public AccessToken accessToken = null;
	public Weibo mWeibo = Weibo.getInstance();// Sina API 实例
	public static final String CONSUMER_KEY = "4188464852";
	public static final String CONSUMER_SECRET = "cf1e022b33ecee613f1c4ec5104a6586";

	// 此处回调页内容应该替换为与appkey对应的应用回调页
	// 应用回调页不可为空
	public static final String REDIRECT_URL = "http://blog.csdn.net/logan676";
	
	
	// ----------------------腾讯API接口所需的参数-----------------------------//
	public static OAuthV2 oAuth = null;// Tencent API 实例
	public String openid = "";
	public String openkey = "";
	public static final String TENCENT_REDIRECT_URL = "http://blog.csdn.net/logan676";
	public static final String CLIENT_ID = "801218195";
	public static final String CLIENT_SECRET = "897a59f506ef91de34e8cec2d0ff90d0";
	public static final String FORMAT = "json";
	public static final String REQNUM = "10";
	// 拉取类型 0x1 原创发表 0x2 转载 0x8 回复 0x10 空回 0x20 提及 0x40 点评
	// 如需拉取多个类型请使用|，如(0x1|0x2)得到3，此时type=3即可，填零表示拉取所有类型
	public final String TYPE = "0";
	// 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频
	public final String CONTENTTYPE = "0";
	public StatusesAPI mStatusesAPI = null;
	public UserAPI userAPI;
	public TAPI mTAPI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		oAuth = BaseActivity.getInstance();
		mDBManager = new DBManager(getApplicationContext());
		userAPI = new UserAPI(OAuthConstants.OAUTH_VERSION_2_A);
		mTAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
		mStatusesAPI = new StatusesAPI(OAuthConstants.OAUTH_VERSION_2_A);

	}

	@Override
	protected void onDestroy() {
		mDBManager.closeDB();// this mDBManager serves for sina and tencent
		mTAPI.shutdownConnection();// this mTAPI just serves for tencent
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		super.onStop();
	}

	public abstract int getLayout();
	
	
	// ---------- 新浪 API接口 --------------------------//
	/**
	 * Sina FriendTimeline
	 * 
	 * @return String
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	public String getFriendTimeline(String since_id) throws MalformedURLException, IOException, WeiboException {
		String url = Weibo.SERVER + "statuses/friends_timeline.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", CONSUMER_KEY);
		bundle.add("since_id", since_id);
		Log.v(TAG, "accessToken:" + accessToken);
		Log.v(TAG, "since_id:" + since_id);
		String rlt;
		try {
			rlt = mWeibo.request(this, url, bundle, "GET", mWeibo.getAccessToken());
			return rlt;
		} catch (com.weibo.net.WeiboException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sina PublicTimeLine
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	public String getPublicTimeLine() throws MalformedURLException, IOException, WeiboException {
		String url = Weibo.SERVER + "statuses/public_timeline.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", CONSUMER_KEY);
		String rlt;
		try {
			rlt = mWeibo.request(this, url, bundle, "GET", mWeibo.getAccessToken());
			return rlt;
		} catch (com.weibo.net.WeiboException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sina UID
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	public String getUID() throws MalformedURLException, IOException, WeiboException {
		String id = "";
		String url = Weibo.SERVER + "account/get_uid.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", CONSUMER_KEY);

		Log.v(TAG, "url:    " + url);
		Log.v(TAG, "CONSUMER_KEY:    " + CONSUMER_KEY);
		Log.v(TAG, "accessToken:    " + accessToken);
		String rlt = null;
		try {
			rlt = mWeibo.request(this, url, bundle, "GET", Weibo.getInstance().getAccessToken());
		} catch (com.weibo.net.WeiboException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			JSONObject js;
			js = new JSONObject(rlt);
			id = js.getString("uid");
			Log.v(TAG, "id:    " + id);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return id;
	}

	/**
	 * Sina UserInfo
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	public String getUserInfo() throws MalformedURLException, IOException, WeiboException {
		String url = Weibo.SERVER + "users/show.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", CONSUMER_KEY);
		bundle.add("uid", getUID());
		String rlt;
		try {
			rlt = mWeibo.request(this, url, bundle, "GET", mWeibo.getAccessToken());
			return rlt;
		} catch (com.weibo.net.WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sina UserTimeline
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	public String getUserTimeline() throws MalformedURLException, IOException, WeiboException {
		String url = Weibo.SERVER + "statuses/user_timeline.json";
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", CONSUMER_KEY);
		bundle.add("uid", getUID());
		String rlt;
		try {
			rlt = mWeibo.request(this, url, bundle, "GET", mWeibo.getAccessToken());
			return rlt;
		} catch (com.weibo.net.WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 新浪微博接口
	 * 发布一条微博 
	 * @param status
	 * @param lon
	 * @param lat
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws WeiboException
	 */
	public String update(String status, String lon, String lat) throws MalformedURLException, IOException, WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", BaseActivity.CONSUMER_KEY);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(mWeibo);
		weiboRunner.request(BaseActivity.this, url, bundle, Utility.HTTPMETHOD_POST, new RequestListener() {

					@Override
					public void onComplete(String response) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(WeiboException e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onIOException(IOException e) {
						// TODO Auto-generated method stub

					}
				});
		return rlt;
	}

	
	// ---------腾讯  API接口 -----------------------//
	/**
	 * 获取主页时间线微博信息
	 * 
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E4%B8%BB%E9%A1%B5%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String getQFriendTimeline(String pageFlag, String pageTime) {
		String rlt = "";
		try {
			rlt = mStatusesAPI.homeTimeline(oAuth, FORMAT, pageFlag, pageTime, REQNUM, TYPE, CONTENTTYPE);
			Log.v(TAG, "getQFriendTimeline:" + rlt);
			return rlt;
		} catch (Exception e1) {
			e1.printStackTrace();
			Log.v(TAG, "getQFriendTimeline2:" + rlt);
			return null;
		}
	
	}

	/**
	 * 获取用户提及时间线微博信息
	 * 
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param lastid 和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id） 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E7%94%A8%E6%88%B7%E6%8F%90%E5%8F%8A%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
		 */
	public String getQMentionsTimeLine(String pageFlag, String pageTime, String lastId) {
		String rlt = "";
		try {
			rlt = mStatusesAPI.mentionsTimeline(oAuth, FORMAT, pageFlag, pageTime, REQNUM, lastId, TYPE, CONTENTTYPE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlt;
	}

	/**
	 * QuserInfo
	 * 获取自己的资料
	 * 
	 * @param oAuth
	 * @return
	 */
	public String getQuserInfo(OAuthV2 oAuth) {
		String rlt = "";
		try {
			rlt = userAPI.info(oAuth, FORMAT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rlt;

	}
		
	/**
	 * 获取我发表时间线信息
	 * @param pageflag 分页标识（0：第一页，1：向下翻页，2向上翻页）
	 * @param pagetime 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间） 
	 * @param lastid 和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id） 
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E6%97%B6%E9%97%B4%E7%BA%BF/%E6%88%91%E5%8F%91%E8%A1%A8%E6%97%B6%E9%97%B4%E7%BA%BF">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public String getQUserTimeLine(String pageFlag, String pageTime, String lastId) {
		String rlt = "";
		try {
			rlt = mStatusesAPI.broadcastTimeline(oAuth, FORMAT, pageFlag, pageTime, REQNUM, lastId, TYPE, CONTENTTYPE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlt;
	}
	
	/**
	 * 发表一条微博
	 * 
	 * @param oAuth
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博开放平台上关于此条API的文档</a>
	 */
	public void update(OAuthV2 oAuth, String content, String clientip) {
			try {
				mTAPI.add(oAuth, FORMAT, content, clientip);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
