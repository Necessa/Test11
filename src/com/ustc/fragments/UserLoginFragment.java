package com.ustc.fragments;

import java.util.ArrayList;

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
import com.ustc.model.User;
import com.ustc.tabs.MainActivity;
import com.ustc.tabs.MeTabFragment;
import com.ustc.tabs.MyApplication;
import com.ustc.tabs.MyFollowTabFragment;
import com.ustc.thread.LoginAsyncTask;
import com.ustc.thread.LoginAsyncTaskInterface;

public class UserLoginFragment extends Fragment implements LoginAsyncTaskInterface{
	public static final String TAG = "PersonalLoginFragment";
	ImageView userLogo;
	EditText uEditText;
	EditText pwEditText;
	Button loginButton;
	private LoginAsyncTaskInterface me = null;
	private String u = null;
	private String pw = null;
	private MyApplication app;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		me = this;
		app = (MyApplication)getActivity().getApplication();
		getUser();
	}
	
	private void getUser(){
		UserTableDao dao = new UserTableDao(getActivity());
		ArrayList<User> users = dao.fetchAll();
		if(users.size() > 0){//�������Ҿ�ʹ�ñ��е�һ���û���Ӧ���ǻ�ȡ���һ�ε�¼���Ǹ��û�
			u = users.get(0).getUsername();
			pw = users.get(0).getPassword();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.user_login,
				null);
		
//		userLogo = (ImageView) v.findViewById(R.id.personal_login_image_logo);
		uEditText = (EditText) v.findViewById(R.id.personal_login_user);
		uEditText.setText(u);
		pwEditText = (EditText) v.findViewById(R.id.personal_login_pwd);
		pwEditText.setText(pw);
		
		loginButton = (Button) v.findViewById(R.id.personal_login_btn);
		loginButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				LoginAsyncTask newTask = new LoginAsyncTask(app,me);
				newTask.execute(u,pw);
			}
		});

		return v;
	}
	
	private void changeTo(){
		//�л�fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from�ǵ�ǰFragment��to��Ҫ�л���Fragment
		if(MainActivity.fm.findFragmentById(R.id.realtabcontent).getTag().equals("myfollow")){//�����myfollow��tab��
			from = MyFollowTabFragment.childFm.findFragmentByTag("personalLoginFragment");
			if((to = MyFollowTabFragment.childFm.findFragmentByTag("myFollowFragment")) == null)
				to = new MyFollowFragment();
			toTag = "myFollowFragment";
			MyFollowTabFragment.switchContent(from, to, toTag);
		}else if(MainActivity.fm.findFragmentById(R.id.realtabcontent).getTag().equals("personal")){//�����personal��tab��
			from = MeTabFragment.childFm.findFragmentByTag("personalLoginFragment");
			if((to = MeTabFragment.childFm.findFragmentByTag("meFragment")) == null)
				to = new MeFragment();
			toTag = "meFragment";
			MeTabFragment.switchContent(from, to, toTag);
		}
	}

	@Override
	public void processSuccess() {//��¼�ɹ���ִ�и÷���
		// TODO Auto-generated method stub
		if(app.getCookie("utmpkey") != null){//�����¼�ɹ����������ݿ�
			UserTableDao dao = new UserTableDao(getActivity());
			if(dao.query(u).size() > 0){
				dao.update(u, pw);
			}else{
				dao.insert(u, pw);
			}
			changeTo();//�л���ͼ
		}else{
			Toast.makeText(getActivity(), "�û����������!", 0).show();
			pwEditText.setText("");
		}
	}

}
