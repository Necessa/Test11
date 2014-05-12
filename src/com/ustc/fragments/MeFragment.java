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
	
	//################����#################
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
		
		//###################����############
				myList=(ListView)v.findViewById(R.id.MeListView);
				 //���ɶ�̬���飬��������  
				ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String, Object>>();	
					HashMap<String,Object> map=new HashMap<String,Object>();  
					map.put("Title","���");
					map.put("Content", "��Уѧ��");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","������");
					map.put("Content","436");
					listItem.add(map);
					map=new HashMap<String,Object>();
					map.put("Title", "������");
					map.put("Content","36");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","����");
					map.put("Content", "847");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","�ϴε�¼ʱ��");
					map.put("Content","2014��05��11��17:39:39 ������");
					listItem.add(map);  
					map=new HashMap<String,Object>();
					map.put("Title","�ϴε�¼IP");
					map.put("Content","202.38.79.80");
					listItem.add(map);
					map=new HashMap<String,Object>();
					map.put("Title","����˵����");
					map.put("Content","û�и���˵��");
					listItem.add(map);  
				//������������Item�Ͷ�̬�����Ӧ��Ԫ��  
				SimpleAdapter listItemAdapter=new SimpleAdapter(getActivity(),listItem,//����Դ
						R.layout.me_list_item,//ListItem��XMLʵ��
						//��̬������ImageItem��Ӧ������   
						new String[] {"Title","Content"},
						 //ImageItem��XML�ļ������Item��ID
						new int[] {R.id.MeTitleItem,R.id.MeContentItem}
						);
				myList.setAdapter(listItemAdapter);
		
		return v;
	}
	
}
