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
	 * ListView ���List��ÿ��item��Ҫ�� adapter ������һ����ͼ�� (getView),һ���µ���ͼ�����ز���ʾ
	 * convertView���ݴ��view��һ��ʼconvertViewΪ�գ�������item1���������ɼ�����ʱ��ͼitem1�ͻ�ŵ�Recycler�У�
	 * ��������Ҫ������ʾitem1����ʱ�Ϳ���ֱ�Ӵ�Recycler��ȡ��item1��ΪconvertView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			//���ز���
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