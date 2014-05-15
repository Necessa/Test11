package com.ustc.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.devsmart.android.ui.HorizontalListView;
import com.ustc.USTCer.R;
import com.ustc.db.BoardTableDao;
import com.ustc.listview.adpters.BoardAdapter;
import com.ustc.listview.adpters.BoardSectionApater;
import com.ustc.tabs.MyFollowTabFragment;

public class AddFollowFragment extends Fragment{
	public static final String TAG = "AddFollowFragment";
	
	private ArrayList<String> sections = new ArrayList<String>();
	//String[] = {name,title,link}
	private Map<String,ArrayList<String[]> > boardData = new HashMap<String,ArrayList<String[]> >();
	private ArrayList<String[]> curBoardData = new ArrayList<String[]>();
	private BoardAdapter boardAdapter;
	private HorizontalListView secListview;
	private ListView boardListview;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		initData();
	}
	
	private void initData(){
		BoardTableDao dao = new BoardTableDao(getActivity());
		sections = dao.fetchSections();
		for(String sec : sections){
			boardData.put(sec, dao.queryBySec(sec));
		}
		Log.v(TAG,"initData finish!");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.addfollow,null);
		
		secListview = (HorizontalListView)v.findViewById(R.id.addfollow_section_listview);
		BoardSectionApater secAdapter = new BoardSectionApater(getActivity().getApplication(), 0, sections);
		secListview.setAdapter(secAdapter);
		
		boardListview = (ListView) v.findViewById(R.id.addfollow_board_listview);
		curBoardData = boardData.get(sections.get(0));
		boardAdapter = new BoardAdapter(getActivity().getApplication(), 0, curBoardData);
		boardListview.setAdapter(boardAdapter);
		
		secListview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				changeBoardData(position);
			}
		});
		return v;
	}
	
	private void changeBoardData(int position){
		curBoardData = boardData.get(sections.get(position));
		boardAdapter.notifyDataSetChanged();
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



}
