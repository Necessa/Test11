package com.ustc.fragments;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

import com.ustc.USTCer.R;

//没有android.support.v4.app.PreferenceFragment,只好用了一个第三方的支持v4的PreferenceFragment
public class SettingFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//加载preferences文件
		addPreferencesFromResource(R.xml.preferences);
	}

	
}
