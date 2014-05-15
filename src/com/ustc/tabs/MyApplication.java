package com.ustc.tabs;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ustc.USTCer.R;
import com.ustc.db.BoardTableDao;
import com.ustc.db.UserTableDao;
import com.ustc.model.Board;
import com.ustc.model.User;
import com.ustc.thread.LoginAsyncTask;
import com.ustc.thread.LoginAsyncTaskInterface;


public class MyApplication extends Application implements LoginAsyncTaskInterface{
	private static final String TAG = "MyApplication";
    private static MyApplication mInstance = null;
    
    //用于保存用户登录的状态，有utmpnum, utmpkey, utmpuserid
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
		initUrls();
		checkNetwork();
		initData();
	}
	
	private void initUrls(){
		SharedPreferences sharedPreferences = getSharedPreferences("urls",0);
		Editor editor = sharedPreferences.edit();
		String url = null;
		if((url = sharedPreferences.getString("topten_url", null)) == null){
			url = "http://bbs.ustc.edu.cn/cgi/bbstop10";
			editor.putString("topten_url", url);  
            // 一定要提交  
            editor.commit();  
		}
		if((url = sharedPreferences.getString("url_prefix", null)) == null){
			url = "http://bbs.ustc.edu.cn/cgi/";
			editor.putString("url_prefix", url);  
            editor.commit();  
		}
		
		if((url = sharedPreferences.getString("url_login", null)) == null){
			url = "http://bbs.ustc.edu.cn/cgi/bbslogin";
			editor.putString("url_login", url);  
            editor.commit();  
		}
	}
	
	private void checkNetwork(){
		ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if(mNetworkInfo == null){//如果无网络
			Builder builder = new Builder(this);  
			builder.setTitle("网络提示信息");  
			builder.setMessage("网络不可用，如果继续，请先设置网络！");  
			builder.setPositiveButton("设置", new OnClickListener() {  
				public void onClick(DialogInterface dialog, int which) {  
					Intent intent = null;  
					/** 
					 * 判断手机系统的版本！如果API大于10 就是3.0+ 因为3.0以上的版本的设置和3.0以 
					 * 下的设置不一样，调用的方法不同。 
					 */  
					if (android.os.Build.VERSION.SDK_INT > 10) {  
						intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);  
					} else {  
						intent = new Intent();  
						ComponentName component = new ComponentName(  
								"com.android.settings",  
								"com.android.settings.WirelessSettings");  
						intent.setComponent(component);  
						intent.setAction("android.intent.action.VIEW");  
					}  
					startActivity(intent);  
				}  
			});  
			builder.setNegativeButton("取消", new OnClickListener() {  
				public void onClick(DialogInterface dialog, int which) {  
					try {
						System.exit(0);// 结束程序
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};  
				}  
			});  
			builder.create();  
			builder.show();  
		}else{
		}
	}
	
	public void initData() {
        initLogin();//初始化登录信息
        
        new Thread(new Runnable(){//初始化版本信息
			@Override
			public void run() {
				// TODO Auto-generated method stub
				initBoard();
			}
		}).start();
	}
	public void initLogin(){
		UserTableDao dao = new UserTableDao(this);
		ArrayList<User> list = dao.fetchAll();
		if(list.size() > 0){
			String user = list.get(0).getUsername();
			String passwd = list.get(0).getPassword();
			LoginAsyncTask newTask = new LoginAsyncTask(this,this);
			newTask.execute(user,passwd);
		}
	}

	private void initBoard(){
		try {
			BoardTableDao dao = new BoardTableDao(this);
			ArrayList<Board> list = dao.fetchAll();
			if(list.size() == 0){//boards表为空
				Log.v(TAG, "initBoard!");
				InputStream in = getResources().openRawResource(R.raw.boards);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while((line = br.readLine()) != null){
					String[] arr = line.split(" ### ");
					if(arr.length == 5){
						dao.insert(arr[0], arr[1], arr[2], arr[3], arr[4]);
					}
 				}
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static MyApplication getInstance() {
		return mInstance;
	}

	@Override
	public void processSuccess() {
		// TODO Auto-generated method stub
		
	}
}