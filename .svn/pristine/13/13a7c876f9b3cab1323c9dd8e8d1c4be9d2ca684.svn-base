package com.logan.weibo.more;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.logan.R;

public class AccountAcitvity extends Activity{

	private RelativeLayout addCount=null;
	private RelativeLayout managerCount=null;
	private ImageView back=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_account);
		
		addCount=(RelativeLayout)findViewById(R.id.addcount);
		addCount.setOnClickListener(new AddCountListener());
		
		managerCount=(RelativeLayout)findViewById(R.id.managerCount);
		managerCount.setOnClickListener(new ManagerCount());
		
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new BackListener());


	}

	class AddCountListener implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("addcount");
		}
		
	}
	
	class ManagerCount implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("managercount");
		}
		
	}
	
	class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}

}
