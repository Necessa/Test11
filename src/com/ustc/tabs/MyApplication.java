package com.ustc.tabs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.ustc.db.BoardTableDao;
import com.ustc.db.UserTableDao;
import com.ustc.model.Board;
import com.ustc.model.User;
import com.ustc.thread.LoginThread;


public class MyApplication extends Application {
	public static final String TAG = "MyApplication";
    private static MyApplication mInstance = null;
    public String user = "";
    public String passwd = "";
    
    //���ڱ����û���¼��״̬����session,cookie
    private Map<String,String> cookies = new HashMap<String,String>();   
    
    public String getCookie(String key){
    	Iterator<Entry<String, String>> it = cookies.entrySet().iterator();
    	while(it.hasNext()){
    		Map.Entry<String, String> entry =  it.next();  
            if(entry.getKey().equals(key))
            	return entry.getValue();
    	}
        return null;
    }
    
    public void addCookie(String key,String value){
        cookies.put(key, value);
    }
    
	@Override
    public void onCreate() {
	    super.onCreate();
		mInstance = this;
		initEngineManager(this);
	}
	public void initEngineManager(Context context) {
        login();
        //TODO
//        getBoard();
	}
	public void login(){
		UserTableDao dao = new UserTableDao(this);
		ArrayList<User> list = dao.fetchAll();
		if(list.size() > 0){
			user = list.get(0).getUsername();
			passwd = list.get(0).getPassword();
			new LoginThread(this,user,passwd).start();//start�ǿ��������߳�
		}
	}

	private void getBoard(){
		BoardTableDao dao = new BoardTableDao(this);
		ArrayList<Board> list = dao.fetchAll();
		if(list.size() > 0){
			Log.v(TAG, "getBoard:" + list.size());
		}else{
			dao.insert("�����г�", "1��", "?link=1");
			dao.insert("�����г�2", "2��", "?link=2");
			dao.insert("�����г�3", "34��", "?link=11");
			dao.insert("�����г�45", "1��", "?link=13");
		}
	}
	public static MyApplication getInstance() {
		return mInstance;
	}
}