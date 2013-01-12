package com.logan.authorize;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.logan.R;
import com.logan.weibo.BaseActivity;
import com.logan.weibo.adapter.AccountAdapter;
/**
 * 账号列表
 * @author Logan
 *
 */
public class AccountActivity extends BaseActivity {

	private final String TAG = "AccountActivity";
	// ArrayList<Account> accounts =null;
	private ArrayList<Account> listData = null;
	private ListView lv;
	private AccountAdapter adapter = null;
	private TextView title = null;
	private ImageView refreshBtn = null;
	private ImageView write = null;
	private View mAddBtn = null;
	 private View mDelBtn = null;
	private View mExitBtn = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		// accounts= new ArrayList<Account>();
		title = (TextView) findViewById(R.id.weibo_title_TV);
		title.setText("账号列表");
		title.setTextSize(25);
		refreshBtn = (ImageView) findViewById(R.id.weibo_refreshBtn);
		refreshBtn.setVisibility(View.INVISIBLE);
		write = (ImageView) findViewById(R.id.weibo_writeBtn);
		write.setVisibility(View.INVISIBLE);
		lv = (ListView) findViewById(R.id.account_lv);
		listData = (ArrayList<Account>) mDBManager.getAccounts();
		adapter = new AccountAdapter(this, listData);
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		mAddBtn = findViewById(R.id.account_add);
		mAddBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent i = new Intent();
				i.setClass(getApplicationContext(), AppList.class);
				startActivity(i);
				finish();
			}
		});
		 mDelBtn=findViewById(R.id.account_delete);
		 mDelBtn.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				AlertDialog.Builder delete = new AlertDialog.Builder(AccountActivity.this);
				delete.setTitle("删除账户");
				delete.setIcon(R.drawable.icon);
				delete.setMessage("提醒：" + "\n" + "将删除选中的账户信息，删除后无法恢复，确定要删除吗？");
				delete.setPositiveButton("删除",new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialoginterface, int which) {
								adapter.notifyDataSetChanged();
							}
						});
				delete.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialoginterface, int which) {}
						});
				delete.create().show();
				
			}
		});
		mExitBtn = findViewById(R.id.account_exit);
		mExitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder exit = new AlertDialog.Builder(AccountActivity.this);
				exit.setTitle("退出");
				exit.setIcon(R.drawable.icon);
				exit.setMessage("提醒：" + "\n" + "\n" + "确定要退出吗？");
				exit.setPositiveButton("退出", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialoginterface, int which) {
//								Intent startMain = new Intent(Intent.ACTION_MAIN);
//								startMain.addCategory(Intent.CATEGORY_HOME);
//								startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//								startActivity(startMain);
								System.exit(0);
							}
						});
				exit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialoginterface, int which) {}
						});
				exit.create().show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "添加账户").setIcon(R.drawable.add_account);
		menu.add(0, 1, 1, "查看微博").setIcon(R.drawable.ic_launcher);
		menu.add(0, 2, 2, "密码保护").setIcon(R.drawable.secret_protect);
		menu.add(0, 3, 3, "关于").setIcon(R.drawable.about);
		menu.add(0, 4, 4, "退出").setIcon(R.drawable.exit);

		return super.onCreateOptionsMenu(menu);
	}
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public int getLayout() {
		return R.layout.account_activity;
	}
}