package com.ustc.listview.adpters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ustc.USTCer.R;
import com.ustc.model.TopTenItem;

public class MyAdapter extends ArrayAdapter<TopTenItem> {

	public MyAdapter(Context context, int textViewResourceId, List<TopTenItem> objects) {
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
			view = LayoutInflater.from(getContext()).inflate(R.layout.topten_item, null);
		} else {
			view = convertView;
		}
		TextView title = (TextView) view.findViewById(R.id.topten_item_title);
		TextView hot = (TextView) view.findViewById(R.id.topten_item_hot);
		TextView author = (TextView) view.findViewById(R.id.topten_item_author);
		TextView department = (TextView) view.findViewById(R.id.topten_item_department);
		
		title.setText(getItem(position).getTitle());
		hot.setText(getItem(position).getHot());
		author.setText(getItem(position).getAuthor());
		department.setText(getItem(position).getDepartment());
		
		return view;
	}

}