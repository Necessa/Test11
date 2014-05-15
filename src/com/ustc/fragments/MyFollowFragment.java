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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.USTCer.R;
import com.ustc.db.UserBoardTableDao;
import com.ustc.listview.adpters.MyFollowAdapter;
import com.ustc.model.Board;
import com.ustc.tabs.MyApplication;
import com.ustc.tabs.MyFollowTabFragment;

public class MyFollowFragment extends Fragment {
	public static final String TAG = "MyFollowFragment";
	private ListView followListView;
	private TextView nofollowTextView;
	private Button addBtn;
	private ArrayList<Board> followData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		followData = new ArrayList<Board>();
		initFollowData();
	}
	
	private void initFollowData(){
		MyApplication app = (MyApplication)getActivity().getApplication();
		UserBoardTableDao dao = new UserBoardTableDao(getActivity());
		followData = dao.query(app.getCookie("utmpuserid"));
		Log.v(TAG, "followData initialized!");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		View v = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.myfollow,
				null);
		addBtn = (Button) v.findViewById(R.id.myFollow_add);
		nofollowTextView = (TextView) v.findViewById(R.id.myFollow_nofollow);
		addBtn.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeTo();
			}
			
		});
		followListView = (ListView) v.findViewById(R.id.myFollow_listview);

		if(followData.size() <= 0){//提示用户没有关注板块
			//Gone是不显示也不占空，而invisible是不显示但占空
			followListView.setVisibility(android.view.View.GONE);
			nofollowTextView.setVisibility(android.view.View.VISIBLE);
		}else{
			nofollowTextView.setVisibility(android.view.View.GONE);
			followListView.setVisibility( android.view.View.VISIBLE);
			ListAdapter adapter = new MyFollowAdapter(getActivity(),0,followData);
			followListView.setAdapter(adapter);
			followListView.setOnItemClickListener(new OnItemClickListener(){
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
		//切换fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from是当前Fragment，to是要切换的Fragment
		from = MyFollowTabFragment.childFm.findFragmentByTag("myFollowFragment");
		if((to = MyFollowTabFragment.childFm.findFragmentByTag("addFollowFragment")) == null)
			to = new AddFollowFragment();
		toTag = "addFollowFragment";

		MyFollowTabFragment.switchContent(from, to, toTag);
	}
	
	
}
