package com.ustc.tabs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.ustc.USTCer.R;
import com.ustc.fragments.TopTenContentFragment;

//TabActivity和ActivityGroup已经deprecated
//LocalActivityManager已经deprecated，使用Fragment和FragmentManager代替
/* 
 * android.app.Fragment使用 getFragmentManager()获得  ，继承Activity
 * android.support.v4.app.Fragment使用 getSupportFragmentManager()获得 ，需要继承android.support.v4.app.FragmentActivity
 * FragmentTabHost只有android.support.v4.app.FragmentTabHost，没android.app.FragmentTabHost
 */
public class MainActivity extends FragmentActivity implements OnTabChangeListener {
	private static final String TAG = "MainActivity";
	private FragmentTabHost mTabHost;
 
	public static FragmentManager fm;
	public static MyApplication app;

	private long exitTime = 0; //双击退出用的
	
//	Class<?>[] frames = {TopTenTabFragment.class,MyFollowTabFragment.class,MeTabFragment.class,SettingTabFragment.class};
//	private final String[] titles = {"今日十大","我的板块","我","设置"};
//	private final int[] images = {R.drawable.tab_information,R.drawable.tab_outpatient,R.drawable.tab_hospital,R.drawable.tab_disease};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		fm = getSupportFragmentManager();
		app = (MyApplication)getApplication();
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, fm, R.id.realtabcontent);
		
		//为每个tabhost设置内容
//		for(int i=0;i<titles.length;i++){
//			TabSpec tabSpec = mTabHost.newTabSpec(titles[i]).setIndicator(getTabItemView(i));
//			mTabHost.addTab(tabSpec,frames[i],null);
//			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.main_tab_bg);
//		}
		  
		mTabHost.addTab(mTabHost.newTabSpec("topten").setIndicator("今日十大"),
				TopTenTabFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("myfollow").setIndicator("我的板块"),
				MyFollowTabFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("personal").setIndicator("我"),
				MeTabFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("setting").setIndicator("设置"),
				SettingTabFragment.class, null);
		mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);  
	}
	
	/**
 	 * 给Tab按钮设置图标和文字
 	 */
// 	private View getTabItemView(int index){
// 		View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
// 		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
// 		imageView.setImageResource(images[index]);
// 		TextView textView = (TextView) view.findViewById(R.id.textview);		
// 		textView.setText(titles[index]);
// 		return view;
// 	}
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		//如果这个函数全部注释掉，好像会报错，所以这里随便加了个日志语句
		Log.v(TAG, "onTabChanged");
//		Fragment oldFragment = fm.findFragmentById(R.id.realtabcontent);
//		Fragment curFragment = fm.findFragmentByTag(mTabHost.getCurrentTabTag());
		
//		Fragment from = null;
		//返回R.id.realtabcontent下的几个TabFragment,如TopTenTabFragment,PersonalTabFragment,CustomTabFragment
//		from = fm.findFragmentById(R.id.realtabcontent);
//		Fragment b = fm.findFragmentById(R.id.topten_tab_root);//始终null
		//返回TabFragment中的内容,如from = TopTenTabFragment时就是TopTenFragment
//		Fragment tabContent = from.getChildFragmentManager().findFragmentById(R.id.tab_root);//TopTenFragment
//		System.out.println(tabContent.getId());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if(keyCode == KeyEvent.KEYCODE_BACK)  
		{  
			if(TopTenContentFragment.onKeyDown(keyCode, event))
				return true;
			else{		  
				if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000  
				{                    
					Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();  
					return true;
				}else
				{
//					finish();  
//					System.exit(0);  
					return super.onKeyDown(keyCode, event); 
				}
			}  
		}
		return super.onKeyDown(keyCode, event);   
	}
}
