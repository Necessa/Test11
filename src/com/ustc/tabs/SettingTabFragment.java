package com.ustc.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ustc.USTCer.R;
import com.ustc.fragments.SettingFragment;

//topten的根Fragment，只是一个container
public class SettingTabFragment extends Fragment{
	private static final String TAG = "SettingTabFragment";
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
		//这个tabs_root.xml是一个container，只有一个Layout，具体内容由不同的Fragment提供
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.tab,
				null);
		initContent();
		return v;
	}
	
	private void initContent(){
		Fragment f = null;
		if((f = childFm.findFragmentByTag("settingFragment")) == null){
			f = new SettingFragment();
			//加具体内容,添加一个Fragment，作为R.id.topten_tab_root的孩子
			childFm.beginTransaction().add(R.id.tab_root, f, "settingFragment").commit();
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
