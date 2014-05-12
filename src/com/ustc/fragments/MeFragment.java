package com.ustc.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ustc.USTCer.R;

public class MeFragment extends Fragment {
	public static final String TAG = "MeFragment";
	private TextView meLife;
	private TextView meArticles;
	private TextView meLastLoginTime;
	private TextView meLastLoginIp;
	private TextView meInfo;
	private ListView meRecent;
	
	//################王洋#################
		private ListView myList;
		
	
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
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.me_list,null);
		
//		meLife = (TextView) v.findViewById(R.id.me_life);
//		meArticles = (TextView) v.findViewById(R.id.me_articles);
//		meLastLoginTime = (TextView) v.findViewById(R.id.me_lastlogintime);
//		meLastLoginIp = (TextView) v.findViewById(R.id.me_lastloginIP);
//		meInfo = (TextView) v.findViewById(R.id.me_info);
//		meRecent = (ListView) v.findViewById(R.id.me_recent_listview);
		
		//###################王洋############
				myList=(ListView)v.findViewById(R.id.MeListView);
				 //生成动态数组，加入数据  
				ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String, Object>>();	
					HashMap<String,Object> map=new HashMap<String,Object>();  
					map.put("Title","身份");
					map.put("Content", "本校学生");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","生命力");
					map.put("Content","436");
					listItem.add(map);
					map=new HashMap<String,Object>();
					map.put("Title", "文章数");
					map.put("Content","36");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","网龄");
					map.put("Content", "847");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","上次登录时间");
					map.put("Content","2014年05月11日17:39:39 星期日");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","上次登录IP");
					map.put("Content","202.38.79.80");
					listItem.add(map);
					map=new HashMap<String,Object>();
					map.put("Title","个人说明档");
					map.put("Content","没有个人说明");
					listItem.add(map);  
				//生成适配器的Item和动态数组对应的元素  
				SimpleAdapter listItemAdapter=new SimpleAdapter(getActivity(),listItem,//数据源
						R.layout.me_list_item,//ListItem的XML实现
						//动态数组与ImageItem对应的子项   
						new String[] {"Title","Content"},
						 //ImageItem的XML文件里面的Item的ID
						new int[] {R.id.MeTitleItem,R.id.MeContentItem}
						);
				myList.setAdapter(listItemAdapter);
		
		return v;
	}
	
}
