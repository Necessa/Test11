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
	 * ListView 针对List中每个item，要求 adapter “给我一个视图” (getView),一个新的视图被返回并显示
	 * convertView是暂存的view，一开始convertView为空，如果你把item1滚动到不可见，这时视图item1就会放到Recycler中，
	 * 当滚动需要重新显示item1，这时就可以直接从Recycler中取出item1作为convertView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			//加载布局
			view = LayoutInflater.from(getContext()).inflate(R.layout.addfollow_board_listview_item, null);
		} else {
			view = convertView;
		}
		final Button addbtn = (Button) view.findViewById(R.id.addfollow_board_listview_item_addBtn);
		final MyApplication app = (MyApplication)cxt;
		final UserBoardTableDao dao = new UserBoardTableDao(cxt);
		if(dao.checkIsExist(app.getCookie("utmpuserid"), getItem(position)[1]) == true){
			addbtn.setText("取消");
		}else 
			addbtn.setText("添加");
		
		TextView tv = (TextView) view.findViewById(R.id.addfollow_board_listview_item_name);
		tv.setText(getItem(position)[0]);
		
		final int pos = position;
		addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(addbtn.getText().equals("取消")){
					dao.delete(app.getCookie("utmpuserid"), getItem(pos)[1]);
					addbtn.setText("添加");
				}else{
					dao.insert(app.getCookie("utmpuserid"), getItem(pos)[1]);
					addbtn.setText("取消");
				}
			}
		});
		
		return view;
	}
	
	
}
