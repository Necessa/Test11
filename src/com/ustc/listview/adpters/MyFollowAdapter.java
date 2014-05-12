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
import android.widget.Toast;

import com.ustc.USTCer.R;

public class MyFollowAdapter extends ArrayAdapter<String> {
	
	public MyFollowAdapter(Context context, int textViewResourceId, List<String> objects) {
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
			view = LayoutInflater.from(getContext()).inflate(R.layout.myfollow_listview_item, null);
		} else {
			view = convertView;
		}
		TextView title = (TextView) view.findViewById(R.id.myFollow_listview_item_title);
		Button btn = (Button) view.findViewById(R.id.myFollow_listview_item_cancelBtn);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ɾ��
				
			}
			
		});
		title.setText(getItem(position));
		
		return view;
	}
}
