package com.ustc.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.ustc.tabs.MyApplication;

// Thread��Runnable���������ֻ���޸����е�run����������Runnable����Ϊ���ﻹ��Ҫ���캯����������Thread
	public class LoginThread extends Thread{
//	public class LoginThread implements Runnable{
	private static final String TAG = "LoginThread";
	private String user,pw;
	private MyApplication app;
	public LoginThread(MyApplication app, String user, String pw){
		this.user = user;
		this.pw = pw;
		this.app = app;
	}
	//�̵߳�start��run��������start�ǿ������߳�ִ�У���run�൱�ڻ������߳�ִ�У����������µ��߳�
	public void run(){
		Log.v(TAG, "run():" + user + pw);
		String url = "http://bbs.ustc.edu.cn/cgi/bbslogin";
        HttpURLConnection connection = null;  
		try {  
			URL u = new URL(url);  
			connection = (HttpURLConnection)u.openConnection(); 
            connection.setDoInput(true);  
            connection.setDoOutput(true);  //һ��Ҫ�����������������Ϊtrue
            connection.setRequestMethod("POST");  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(false);  
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
//        	connection.setRequestProperty("Cookie", sessionId[0]);
            connection.connect();  
              
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
            String content = "id=" + user + "&pw=" + pw + "&ajax=" + 1;  
            out.writeBytes(content);  
            out.flush();  
            out.close();  
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){ //�������200�������Ƿ��¼�ɹ�
            	InputStream is = connection.getInputStream();  
                BufferedReader br = new BufferedReader(new InputStreamReader(is));  
                String readLine = null; 
                
            	/*��¼�ɹ����ص�����
            	 *  <html>
				 * <head>
				 * <script>document.cookie='utmpnum=97'</script>
				 * <script>document.cookie='utmpkey=4401714293296149341'</script>
				 * <script>document.cookie='utmpuserid=wlvswbw'</script>
				 * <meta http-equiv='Refresh' content='0; url=/main.html'>
            	 */
            	
            	StringBuilder sb = new StringBuilder();
            	while((readLine = br.readLine()) != null){
            		sb.append(readLine);
            	}
            	String html = sb.toString();
            	if(html.startsWith("0")){//��¼���ɹ�
            		Log.v(TAG, "Login fail!");
            	}else{
            		Pattern patternNum = Pattern.compile("utmpnum=([0-9]+)");
                	Pattern patternKey = Pattern.compile("utmpkey=([0-9]+)");
                	Pattern patternId = Pattern.compile("utmpuserid=([a-zA-Z]+)");
                	
                	Matcher m = patternNum.matcher(html);
                	if(m.find()){
                		String mm = m.group().substring(m.group().indexOf("=")+1);
                		System.out.println(mm);
                		app.addCookie("utmpnum",m.group().substring(m.group().indexOf("=")+1));
                	}
                	m = patternKey.matcher(html);
                	if(m.find()){
                		app.addCookie("utmpkey",m.group().substring(m.group().indexOf("=")+1));
                	}
                	m = patternId.matcher(html);
                	if(m.find()){
                		app.addCookie("utmpuserid",m.group().substring(m.group().indexOf("=")+1));
                	}
            	}
                
                is.close();  
                br.close(); 
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  finally{
        	 connection.disconnect();  
        }
	}
}
