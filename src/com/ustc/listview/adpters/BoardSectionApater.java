package com.ustc.listview.adpters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.ustc.USTCer.R;

public class BoardSectionApater extends ArrayAdapter<String> {
	
	public BoardSectionApater(Context context, int textViewResourceId, List<String> objects) {
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
			view = LayoutInflater.from(getContext()).inflate(R.layout.addfollow_section_listview_item, null);
		} else {
			view = convertView;
		}
		Button btn = (Button) view.findViewById(R.id.addfollow_section_item_btn);
		btn.setText(getItem(position));
		
		return view;
	}
}
