package com.ustc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ustc.USTCer.R;
import com.ustc.db.UserTableDao;
import com.ustc.tabs.MainActivity;
import com.ustc.tabs.MyApplication;
import com.ustc.tabs.MyFollowTabFragment;
import com.ustc.tabs.MeTabFragment;
import com.ustc.thread.LoginAsyncTaskInterface;
import com.ustc.thread.LoginAsyncTask;

public class UserLoginFragment extends Fragment implements LoginAsyncTaskInterface{
	public static final String TAG = "PersonalLoginFragment";
	ImageView userLogo;
	EditText user;
	EditText pwd;
	Button loginButton;
	private LoginAsyncTaskInterface me = null;
	private String u = null;
	private String pw = null;
	private MyApplication app;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		me = this;
		app = (MyApplication)getActivity().getApplication();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.user_login,
				null);
		
//		userLogo = (ImageView) v.findViewById(R.id.personal_login_image_logo);
		user = (EditText) v.findViewById(R.id.personal_login_user);
		pwd = (EditText) v.findViewById(R.id.personal_login_pwd);
		loginButton = (Button) v.findViewById(R.id.personal_login_btn);
		
		loginButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				u = user.getText().toString();
				pw = pwd.getText().toString();
//					new LoginThread(app,u,pw).start();
				LoginAsyncTask newTask = new LoginAsyncTask(app,me);
				newTask.execute(u,pw);
			}
		});

		return v;
	}
	
	private void changeTo(){
		//切换fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from是当前Fragment，to是要切换的Fragment
		if(MainActivity.fm.findFragmentById(R.id.realtabcontent).getTag().equals("myfollow")){//如果是myfollow的tab下
			from = MyFollowTabFragment.childFm.findFragmentByTag("personalLoginFragment");
			if((to = MyFollowTabFragment.childFm.findFragmentByTag("myFollowFragment")) == null)
				to = new MyFollowFragment();
			toTag = "myFollowFragment";
			MyFollowTabFragment.switchContent(from, to, toTag);
		}else if(MainActivity.fm.findFragmentById(R.id.realtabcontent).getTag().equals("personal")){//如果是personal的tab下
			from = MeTabFragment.childFm.findFragmentByTag("personalLoginFragment");
			if((to = MeTabFragment.childFm.findFragmentByTag("meFragment")) == null)
				to = new MeFragment();
			toTag = "meFragment";
			MeTabFragment.switchContent(from, to, toTag);
		}
	}

	@Override
	public void processSuccess() {//登录成功会执行该方法
		// TODO Auto-generated method stub
		if(app.getCookie("utmpkey") != null){//如果登录成功，放入数据库
			UserTableDao dao = new UserTableDao(getActivity());
			if(dao.query(u).size() > 0){
				dao.update(u, pw);
			}else{
				dao.insert(u, pw);
			}
			changeTo();//切换视图
		}else{
			Toast.makeText(getActivity(), "用户或密码错误!", 0).show();
			pwd.setText("");
		}
	}

}
