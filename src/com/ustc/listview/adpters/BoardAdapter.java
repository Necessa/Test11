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
import com.ustc.db.UserBoardTableDao;
import com.ustc.tabs.MyApplication;

//String[] = {name,title,link}
public class BoardAdapter extends ArrayAdapter<String[]>{
	private Context cxt;
	public BoardAdapter(Context context, int textViewResourceId, List<String[]> objects) {
		super(context, textViewResourceId, objects);
		this.cxt = context;
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
			view = LayoutInflater.from(getContext()).inflate(R.layout.addfollow_board_listview_item, null);
		} else {
			view = convertView;
		}
		final Button addbtn = (Button) view.findViewById(R.id.addfollow_board_listview_item_addBtn);
		final MyApplication app = (MyApplication)cxt;
		final UserBoardTableDao dao = new UserBoardTableDao(cxt);
		if(dao.checkIsExist(app.getCookie("utmpuserid"), getItem(position)[1]) == true){
			addbtn.setText("ȡ��");
		}else 
			addbtn.setText("���");
		
		TextView tv = (TextView) view.findViewById(R.id.addfollow_board_listview_item_name);
		tv.setText(getItem(position)[0]);
		
		final int pos = position;
		addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(addbtn.getText().equals("ȡ��")){
					dao.delete(app.getCookie("utmpuserid"), getItem(pos)[1]);
					addbtn.setText("���");
				}else{
					dao.insert(app.getCookie("utmpuserid"), getItem(pos)[1]);
					addbtn.setText("ȡ��");
				}
			}
		});
		
		return view;
	}
	
	
}
