package com.ustc.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.util.Log;

import com.ustc.tabs.MyApplication;

public class LoginAsyncTask extends AsyncTask<String, String, String> {
	private static final String TAG = "LoginAsyncTask";
	private LoginAsyncTaskInterface delegate = null;
	private MyApplication app = null;
	public LoginAsyncTask(MyApplication app,LoginAsyncTaskInterface delegate){
		this.app = app;
		this.delegate = delegate;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = "http://bbs.ustc.edu.cn/cgi/bbslogin";
		HttpURLConnection connection = null;
		String user = params[0];
		String pw = params[1];
		try {  
			URL u = new URL(url);  
			connection = (HttpURLConnection)u.openConnection(); 
			connection.setDoInput(true);  
			connection.setDoOutput(true);  //一定要将可以输出参数设置为true
			connection.setRequestMethod("POST");  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(false);  
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
			connection.connect();  

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
			String content = "id=" + user + "&pw=" + pw + "&ajax=" + 1;  
			out.writeBytes(content);  
			out.flush();  
			out.close();  
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){ //如果返回200，则检查是否登录成功
				InputStream is = connection.getInputStream();  
				BufferedReader br = new BufferedReader(new InputStreamReader(is));  
				String readLine = null; 

				/*登录成功返回的内容
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
				if(html.startsWith("0")){//登录不成功
					Log.v(TAG, "登录失败!");
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
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		delegate.processSuccess();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
}
