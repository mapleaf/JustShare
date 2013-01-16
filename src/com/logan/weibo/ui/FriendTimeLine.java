package com.logan.weibo.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.logan.R;
import com.logan.app.AppContext;
import com.logan.util.StringUtils;
import com.logan.util.UIHelper;
import com.logan.weibo.adapter.ListViewAdapter;
import com.logan.weibo.bean.BaseActivity;
import com.logan.weibo.bean.BaseTimeLine;
import com.logan.weibo.bean.QStatus;
import com.logan.weibo.bean.Status;
import com.logan.weibo.widget.PullToRefreshListView;
import com.weibo.net.WeiboException;
/**
 * 微博主页
 * @author Logan
 *
 */
public class FriendTimeLine extends BaseTimeLine {

	private final String TAG = "FriendTimeLine";

	private ListViewAdapter mAdapter = null;
	private AppContext appContext;//全局Context
	private List<Status> statusList = null;
	private String mData = "";
	private final int pageSize = 10;
	private int pageSum = 0;
	private Handler lvNewsHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setSelectedFooterTab(0);
		titleTV.setText("微博主页");
		try {
			mData = getFriendTimeline("0");
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
		statusList = getStatusList(mData);
		mAdapter = new ListViewAdapter(FriendTimeLine.this, statusList);
		pullToRefreshListView.setAdapter(mAdapter);
		
		pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		//点击头部、底部栏无效
        		if(position == 0 || view == listView_footer) return;
        		
//        		News news = null;        		
//        		//判断是否是TextView
//        		if(view instanceof TextView){
//        			news = (News)view.getTag();
//        		}else{
//        			TextView tv = (TextView)view.findViewById(R.id.news_listitem_title);
//        			news = (News)tv.getTag();
//        		}
//        		if(news == null) return;
        		
        		//跳转到微博详情
        		//UIHelper.showNewsRedirect(view.getContext(), news);
        		Intent intent = new Intent();
	    		Bundle mBundle = new Bundle();  
	            mBundle.putSerializable("detail",statusList.get(position));  
	    		intent.putExtras(mBundle);
				intent.setClass(FriendTimeLine.this, StatusDetail.class);
				startActivity(intent);
        	}        	
		});
        pullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				pullToRefreshListView.onScrollStateChanged(view, scrollState);
				
				//数据为空--不用继续下面代码了
				if(statusList.isEmpty()) return;
				
				//判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if(view.getPositionForView(listView_footer) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}
				
				int lvDataState = StringUtils.toInt(pullToRefreshListView.getTag());
				if(scrollEnd && lvDataState==UIHelper.LISTVIEW_DATA_MORE)
				{
//					pullToRefreshListView.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					listView_foot_more.setText(R.string.load_ing);
					listView_foot_progress.setVisibility(View.VISIBLE);
					//当前pageIndex
					int pageIndex = pageSum/pageSize;
					loadLvNewsData(pageIndex, lvNewsHandler, UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				pullToRefreshListView.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
            	loadLvNewsData(0, lvNewsHandler, UIHelper.LISTVIEW_ACTION_REFRESH);
            }
        });	
        
		
	}

	private void loadLvNewsData(final int pageSum,final Handler handler,final int action){ 
		mHeadProgress.setVisibility(ProgressBar.VISIBLE);		
		new Thread(){
			public void run() {				
				Message msg = new Message();
				boolean isRefresh = false;
				if(action == UIHelper.LISTVIEW_ACTION_REFRESH || action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {					
//					NewsList list = appContext.getNewsList(pageIndex, isRefresh);
					String since_id =statusList.get(pageSum).getId();
					mData = getFriendTimeline(since_id);
					statusList = getStatusList(mData);
					msg.what = statusList.size();
					msg.obj = statusList;
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	msg.what = -1;
	            	msg.obj = e;
	            }
				msg.arg1 = action;
				msg.arg2 = UIHelper.LISTVIEW_DATATYPE_NEWS;
                
			}
		}.start();
	} 
	

}
