package com.ustc.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ustc.USTCer.R;

public class MeFragment extends Fragment {
	public static final String TAG = "MeFragment";
	
	private ListView myList;
	private String url;
	private ImageView myImage;
	private TextView myName;
	private ProgressBar pb;
	public MeFragment()
	{
		url="http://bbs.ustc.edu.cn/cgi/bbsqry?userid=icewing"; 
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		//�ӵ�¼����ȡ��ֵ
		String string = getArguments().getString("key"); 		
		if(string!="")
			url="http://bbs.ustc.edu.cn/cgi/bbsqry?userid="+string;
		MyTask me=new MyTask();	
		me.execute(url);	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.me_list,null);
//		meLife = (TextView) v.findViewById(R.id.me_life);
//		meArticles = (TextView) v.findViewById(R.id.me_articles);
//		meLastLoginTime = (TextView) v.findViewById(R.id.me_lastlogintime);
//		meLastLoginIp = (TextView) v.findViewById(R.id.me_lastloginIP);
//		meInfo = (TextView) v.findViewById(R.id.me_info);
//		meRecent = (ListView) v.findViewById(R.id.me_recent_listview);	
		myList=(ListView)v.findViewById(R.id.MeListView);
		myImage=(ImageView)v.findViewById(R.id.MeImage);
		myName=(TextView)v.findViewById(R.id.myName);
		pb=(ProgressBar)v.findViewById(R.id.pb);
		pb.setVisibility(View.VISIBLE);
		return v;
	}
	
	public class MyTask extends AsyncTask<String,Integer,String>{
		//onPreExecute����������ִ�к�̨����ǰ��һЩUI����  
		ArrayList<HashMap<String,Object>> listItem;
		Bitmap bm;
		String myid,myact;
	    @Override  
	    protected void onPreExecute() {  
	        Log.i(TAG, "onPreExecute() called"); 
	        listItem=new ArrayList<HashMap<String, Object>>();	 
	    }  
	    
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i(TAG, "doInBackground(Params... params) called");  
			try {  	
				Document doc=Jsoup.connect(params[0]).get();
		    	Elements elems=doc.getElementsByAttributeValue("class", "face");
		    	Element elem=elems.first();
		    	//����ͷ���ȡ
		    	String pic=elem.select("img").first().attr("abs:src");
		    	//��Ծ�̶Ȼ�ȡ
		    	String act_t=elem.select("span").get(2).attr("style");
		    	int t=act_t.indexOf(":");
		    	String act=act_t.substring(t+1,act_t.length()-1);
		    	myact=act;
		    	//�û����
		    	String ident_t=elem.select("span").get(4).attr("class");  
		    	String ident;
		    	switch(ident_t)
		    	{
		    	case "identity ALU":
		    		ident="�ƴ�У��";
		    		break;
		    	case "identity STA":
		    		ident="��У�̹�";
		    		break;
		    	default:
		    		ident="��Уѧ��";
		    	}	    	
		    	//�û������ǳ�
		    	elems=doc.getElementsByAttributeValue("class", "detailinfo");  
		    	elem=elems.first();
		    	String id=elem.getElementsByAttributeValue("class", "id").first().text();  //�û�id
		    	myid=id;
		    	String nickname=elem.getElementsByAttributeValue("class", "nickname").first().text();  //�ǳ�
		    	String vitality=elem.getElementsByAttributeValue("class", "vitality").first().text();  	//������
		    	String post_count=elem.getElementsByAttributeValue("class", "post_count").first().text(); //������
		    	String login_count=elem.getElementsByAttributeValue("class", "login_count").first().text(); //��վ����
		    	String age=elem.getElementsByAttributeValue("class", "age").first().text();  //����
		    	String mail=elem.getElementsByAttributeValue("class", "mail").first().text();   //����
		    	String smd=elem.getElementsByAttributeValue("class", "smd").first().text();    //����˵��
		    	
		    	////��������
		    	bm=returnBitMap(pic);
		    	HashMap<String,Object> item=new HashMap<String,Object>();
		    	item.put("Title", "���");
		    	item.put("Content", ident);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "�ǳ�");
		    	item.put("Content", nickname);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "������");
		    	item.put("Content", vitality);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "������");
		    	item.put("Content", post_count);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "��վ����");
		    	item.put("Content", login_count);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "����");
		    	item.put("Content", age);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "����");
		    	item.put("Content", mail);
		    	listItem.add(item);
		    	item=new HashMap<String,Object>();
		    	item.put("Title", "����˵��");
		    	item.put("Content", smd);
		    	listItem.add(item);

            } catch (Exception e) {  
                Log.e(TAG, e.getMessage());  
            }  
			return null;
		}
		
		//onProgressUpdate�������ڸ��½�����Ϣ  
        @Override  
        protected void onProgressUpdate(Integer... progresses) {  
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");  
        }
        
        //onPostExecute����������ִ�����̨��������UI,��ʾ���  
        @Override  
        protected void onPostExecute(String result) {  
            Log.i(TAG, "onPostExecute(Result result) called");
            ///////ͷ������
	    	myImage.setImageBitmap(bm);
	    	myName.setText(myid);
	    	pb.setProgress(Integer.parseInt(myact));
	    	
	    	 ///////��ListView��Adapter
          	//������������Item�Ͷ�̬�����Ӧ��Ԫ��  
	    	SimpleAdapter listItemAdapter=new SimpleAdapter(getActivity(),listItem,//����Դ
				R.layout.me_list_item,//ListItem��XMLʵ��
				//��̬������ImageItem��Ӧ������   
				new String[] {"Title","Content"},
				//ImageItem��XML�ļ������Item��ID
				new int[] {R.id.MeTitleItem,R.id.MeContentItem}
			
			);
			myList.setAdapter(listItemAdapter);	
        } 
        
        //onCancelled����������ȡ��ִ���е�����ʱ����UI  
        @Override  
        protected void onCancelled() {  
            Log.i(TAG, "onCancelled() called");  	 
        }  
	}

	public Bitmap returnBitMap(String url) { 
		URL myFileUrl = null; 
		Bitmap bitmap = null; 
		try { 
		myFileUrl = new URL(url); 
		} catch (MalformedURLException e) { 
		e.printStackTrace(); 
		} 
		try { 
		HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection(); 
		conn.setDoInput(true); 
		conn.connect(); 
		InputStream is = conn.getInputStream(); 
		bitmap = BitmapFactory.decodeStream(is); 
		is.close(); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		return bitmap; 
	} 
}
