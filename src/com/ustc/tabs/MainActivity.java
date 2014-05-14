package com.ustc.tabs;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
//TabActivity��ActivityGroup�Ѿ�deprecated
public class MainActivity extends FragmentActivity implements OnTabChangeListener {
	private static final String TAG = "MainActivity";
	private FragmentTabHost mTabHost;
//	LocalActivityManager�Ѿ�deprecated��ʹ��Fragment��FragmentManager����
//  LocalActivityManager lam; 
	public static FragmentManager fm;
	public static MyApplication app;

	private long  exitTime=0; //˫���˳��õ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		fm = getSupportFragmentManager();
		app = (MyApplication)getApplication();
		
		initUrls();
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, fm, R.id.realtabcontent);

		mTabHost.addTab(mTabHost.newTabSpec("topten").setIndicator("����ʮ��"),
				TopTenTabFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("myfollow").setIndicator("�ҵ��ղ�"),
				MyFollowTabFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("personal").setIndicator("��"),
				PersonalTabFragment.class, null);
		
		mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);  
	}
	
	private void initUrls(){
		SharedPreferences sharedPreferences = getSharedPreferences("urls",0);
		Editor editor = sharedPreferences.edit();
		String url = null;
		if((url = sharedPreferences.getString("topten_url", "default")) == "default"){
			url = "http://bbs.ustc.edu.cn/cgi/bbstop10";
			editor.putString("topten_url", url);  
            // һ��Ҫ�ύ  
            editor.commit();  
		}
		if((url = sharedPreferences.getString("url_prefix", "default")) == "default"){
			url = "http://bbs.ustc.edu.cn/cgi/";
			editor.putString("url_prefix", url);  
            editor.commit();  
		}
	}
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		//����������ȫ��ע�͵�������ᱨ���������������˸���־���
		Log.v(TAG, "onTabChanged");
//		Fragment oldFragment = fm.findFragmentById(R.id.realtabcontent);
//		Fragment curFragment = fm.findFragmentByTag(mTabHost.getCurrentTabTag());
		
//		Fragment from = null;
		//����R.id.realtabcontent�µļ���TabFragment,��TopTenTabFragment,PersonalTabFragment,CustomTabFragment
//		from = fm.findFragmentById(R.id.realtabcontent);
//		Fragment b = fm.findFragmentById(R.id.topten_tab_root);//ʼ��null
		//����TabFragment�е�����,��from = TopTenTabFragmentʱ����TopTenFragment
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
				if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()���ۺ�ʱ���ã��϶�����2000  
				{                    
					Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",Toast.LENGTH_SHORT).show();
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
