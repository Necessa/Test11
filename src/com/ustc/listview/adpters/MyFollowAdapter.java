package com.ustc.listview.adpters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ustc.USTCer.R;
import com.ustc.model.Board;

public class MyFollowAdapter extends ArrayAdapter<Board> {
	
	public MyFollowAdapter(Context context, int textViewResourceId, List<Board> objects) {
		super(context, textViewResourceId, objects);
	}

	/*
	 * ListView 针对List中每个item，要求 adapter “给我一个视图” (getView),一个新的视图被返回并显示
	 * convertView是暂存的view，一开始convertView为空，如果你把item1滚动到不可见，这时视图item1就会放到Recycler中，
	 * 当滚动需要重新显示item1，这时就可以直接从Recycler中取出item1作为convertView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			//加载布局
			view = LayoutInflater.from(getContext()).inflate(R.layout.myfollow_listview_item, null);
		} else {
			view = convertView;
		}
		TextView name = (TextView) view.findViewById(R.id.myFollow_listview_item_name);
		TextView sec = (TextView) view.findViewById(R.id.myFollow_listview_item_sec);
		
		name.setText(getItem(position).getName());
		sec.setText(getItem(position).getSection());
		
		return view;
	}
}
