package com.ustc.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.USTCer.R;
import com.ustc.listview.adpters.MyFollowAdapter;
import com.ustc.tabs.MyFollowTabFragment;
import com.ustc.tabs.PersonalTabFragment;

public class MyFollowFragment extends Fragment {
	public static final String TAG = "MyFollowFragment";
	private ListView myFollowListView;
	private TextView myFollowNoFollow;
	private Button addBtn;
	private ArrayList<String> followData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		followData = new ArrayList<String>();
		initFollowData();
	}
	
	private void initFollowData(){
		followData.add("������֯ [����][����]--�ʲ�����ڹ���");
		followData.add("���ǵļ� [Ժϵ] --�ȿ�ѧ����Դ����ϵ(13ϵ)");
		followData.add("������ѧ [��Ծ]-- �����Ծ");
		followData.add("ѧ����ѧ [����][��ѧ][ѧ��] -- ��վ������");
		followData.add("���и��� [����][����][����] -- 嫺���ˮ");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.myfollow,
				null);
		addBtn = (Button) v.findViewById(R.id.myFollow_add);
		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeTo();
			}
			
		});
		myFollowListView = (ListView) v.findViewById(R.id.myFollow_listview);
		myFollowNoFollow = (TextView) v.findViewById(R.id.myFollow_nofollow);

		if(followData.size() <= 0){
			myFollowNoFollow.setVisibility(android.view.View.VISIBLE);
			myFollowListView.setVisibility( android.view.View.INVISIBLE);
		}else{
			myFollowNoFollow.setVisibility(android.view.View.INVISIBLE);
			myFollowListView.setVisibility( android.view.View.VISIBLE);
			ListAdapter adapter = new MyFollowAdapter(getActivity(),0,followData);
			myFollowListView.setAdapter(adapter);
			myFollowListView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "position " + position, 0).show();
				}
			});
		}
		return v;
	}
	
	private void changeTo(){
		//�л�fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from�ǵ�ǰFragment��to��Ҫ�л���Fragment
		from = MyFollowTabFragment.childFm.findFragmentByTag("myFollowFragment");
		if((to = MyFollowTabFragment.childFm.findFragmentByTag("addFollowFragment")) == null)
			to = new AddFollowFragment();
		toTag = "addFollowFragment";

		MyFollowTabFragment.switchContent(from, to, toTag);
	}
}
