package com.ustc.fragments;

import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;

import com.ustc.USTCer.R;

//û��android.support.v4.app.PreferenceFragment,ֻ������һ����������֧��v4��PreferenceFragment
public class SettingFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//����preferences�ļ�
		addPreferencesFromResource(R.xml.preferences);
	}

	
}
