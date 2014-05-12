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

import com.ustc.USTCer.R;
import com.ustc.tabs.MyFollowTabFragment;

public class AddFollowFragment extends Fragment implements OnClickListener{
	public static final String TAG = "AddFollowFragment";
	private Button allBtn;
	private static ArrayList<String> contentData = new ArrayList<String>();
	private Button btn1,btn2,btn3,btn4,btn5;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.addfollow,null);
		
		btn1 = (Button) v.findViewById(R.id.addfollow_btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) v.findViewById(R.id.addfollow_btn2);
		btn1.setOnClickListener(this);
		btn3 = (Button) v.findViewById(R.id.addfollow_btn3);
		btn1.setOnClickListener(this);
		btn4 = (Button) v.findViewById(R.id.addfollow_btn4);
		btn1.setOnClickListener(this);
		btn5 = (Button) v.findViewById(R.id.addfollow_btn5);
		btn1.setOnClickListener(this);
		
		allBtn = (Button) v.findViewById(R.id.addfollow_allBtn);
		allBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeTo();
			}
		});
		return v;
	}
	
	private void changeTo(){
		//切换fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from是当前Fragment，to是要切换的Fragment
		from = MyFollowTabFragment.childFm.findFragmentByTag("addFollowFragment");
		if((to = MyFollowTabFragment.childFm.findFragmentByTag("myFollowFragment")) == null)
			to = new MyFollowFragment();
		toTag = "myFollowFragment";

		MyFollowTabFragment.switchContent(from, to, toTag);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.addfollow_btn1:
			
			break;
		case R.id.addfollow_btn2:

			break;
		case R.id.addfollow_btn3:

			break;
		case R.id.addfollow_btn4:

			break;
		case R.id.addfollow_btn5:

			break;
		}
	}

}
