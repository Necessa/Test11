package com.ustc.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ustc.USTCer.R;
import com.ustc.fragments.MeFragment;
import com.ustc.fragments.UserLoginFragment;

//个人中心的根View只是一个container
public class MeTabFragment extends Fragment {
	public static final String TAG = "PersonalTabFragment";
	public static FragmentManager childFm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		childFm = getChildFragmentManager();
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//这个personal_tab_fragment.xml是一个container，只有一个Layout，具体内容由不同的Fragment提供
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.tab,null);
		initContent();
		return v;
	}
	
	@SuppressLint("NewApi")
	private void initContent(){
		MyApplication app = (MyApplication)getActivity().getApplication();
		Fragment f = null;
		String tag = "";
		if(app.getCookie("utmpkey") == null){//如果不存在cookie
			if(childFm.findFragmentByTag("personalLoginFragment") == null){
				f = new UserLoginFragment();
				tag = "personalLoginFragment";
			}
		}else{
			if(childFm.findFragmentByTag("meFragment") == null){
				f = new MeFragment();
				tag = "meFragment";
			}
		}
		//加具体内容,添加一个Fragment
		if(f != null){
			if(childFm.findFragmentByTag("personalLoginFragment") == null)
				childFm.beginTransaction().add(R.id.tab_root, f, tag).commit();
			else {//如果登录界面存在，则撤掉
				//replace(int containerViewId, Fragment fragment, String tag)
				childFm.beginTransaction().replace(R.id.tab_root, f, tag).commit();
			}
		}
	}
	
	public static void switchContent(Fragment from, Fragment to, String toTag) {
        if (from != to) {
            FragmentTransaction transaction = childFm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.tab_root, to, toTag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
            	// 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commit(); 
            }
        }
    }
}
