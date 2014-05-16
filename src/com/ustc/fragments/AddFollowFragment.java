package com.ustc.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	private Button backBtn;
	
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
			ArrayList<String[]> board = dao.queryBySec(sec);
			boardData.put(sec, board);
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
		secListview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				changeBoardData(position);
			}
		});
		
		boardListview = (ListView) v.findViewById(R.id.addfollow_board_listview);
		curBoardData.addAll(boardData.get(sections.get(0)));
		boardAdapter = new BoardAdapter(getActivity().getApplication(), 0, curBoardData);
		boardListview.setAdapter(boardAdapter);
		
		backBtn = (Button) v.findViewById(R.id.addfollow_allBtn);
		backBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeTo();
			}
		});
		
		return v;
	}
	
	private void changeBoardData(int position){
		/*List.clear与removeAll区别：
		 * public void clear() {
    	 * modCount++;
    	 * // Let gc do its work
    	 * for (int i = 0; i < size; i++)
         *    elementData[i] = null;
    	 *    size = 0;
		 * }
		 * 
		 * public boolean removeAll(Collection<?> c) {
    	 * boolean modified = false;
    	 * Iterator<?> e = iterator();
    	 * while (e.hasNext()) {
         *   if (c.contains(e.next())) {
         *     e.remove();
         *     modified = true;
         *   }
         *  }
         *  return modified;
         * }
		 */
		curBoardData.clear();
		Iterator<Entry<String, ArrayList<String[]>>> iterator = boardData.entrySet().iterator();
		while (iterator.hasNext()) {
		    Map.Entry<String, ArrayList<String[]>> entry = iterator.next();
		    if(entry.getKey().equals(sections.get(position))){
		    	curBoardData.addAll(entry.getValue());
		    	break;
		    }
		}
		
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
