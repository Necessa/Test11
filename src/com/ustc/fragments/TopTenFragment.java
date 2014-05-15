package com.ustc.fragments;

import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ustc.USTCer.R;
import com.ustc.listview.adpters.MyAdapter;
import com.ustc.model.TopTenItem;
import com.ustc.tabs.TopTenTabFragment;

public class TopTenFragment extends Fragment implements AsyncResponse{
	private static final String TAG = "TopTenFragment";
	private ListView listview;
	private  ArrayList<TopTenItem> listData = new ArrayList<TopTenItem>();
	SharedPreferences sharedPreferences = null;
	Editor editor = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		//listData����Ӧ�÷���onCreate�У�������onCreateView����ΪonCreateֻ��ִ��һ�Σ���onCreateView��ÿ���л�ʱ����ִ��һ��
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("urls",0);//���е�url���浽urls.xml�ļ���
		String url = sharedPreferences.getString("topten_url", null);
		loadHtmlThread newThread = new loadHtmlThread(getActivity(),listData);
		newThread.delegate = this;
		newThread.execute(url);
	}
	
	/*AysncTask��Thread��Runnable������
	 * AsyncTask is a convenience class for doing some work on a new thread and use the results on the thread from which it got called (usually the UI thread) when finished
	 * AsyncTask enables proper and easy use of the UI thread. This class allows to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
	 * AsyncTask is designed to be a helper class around Thread and Handler and does not constitute a generic threading framework. AsyncTasks should ideally be used for short operations (a few seconds at the most.)
	 * If you need to keep threads running for long periods of time, it is highly recommended you use the various APIs provided by the java.util.concurrent pacakge such as Executor, ThreadPoolExecutor and FutureTask.
	 * An asynchronous task is defined by a computation that runs on a background thread and whose result is published on the UI thread. 
	 * An asynchronous task is defined by 3 generic types, called Params, Progress and Result, and 4 steps, called onPreExecute, doInBackground, onProgressUpdate and onPostExecute.
	 */
	static class loadHtmlThread extends AsyncTask<String, String, String>{
		public AsyncResponse delegate = null;
//		ProgressDialog bar;
        private Document doc;
        private ArrayList<TopTenItem> listData;
        private Context cxt;
        
        public loadHtmlThread(Context cxt, ArrayList<TopTenItem> listData){
        	this.listData = listData;
        	this.cxt = cxt;
        }
        
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String url = params[0];
                doc = Jsoup.parse(new URL(url), 5000);
     			Elements trs = doc.select("tr");
     			String url_prefix = cxt.getSharedPreferences("urls",0).getString("url_prefix", null);
     			for(Element tr : trs){
     				Elements tds = tr.select("td");
     				if(tds.size() <= 0)
     					continue;
     				
     				TopTenItem item = new TopTenItem();
     				item.setTitle(tds.get(2).getElementsByTag("a").html().trim());
     				item.setHot(tds.get(4).html().trim());
     				item.setAuthor(tds.get(3).getElementsByTag("a").html().trim());
     				item.setDepartment(tds.get(1).getElementsByTag("a").html().trim());
     				String s = tds.get(2).getElementsByTag("a").attr("href");
     				item.setUrl(url_prefix + s);
     				
     				listData.add(item);
     			}
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            delegate.processFinish();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v(TAG, "onCreateView");
		//ÿ���л�ʱ����ִ��onCreateView,�����һ��View v����onCreateֻ��ִ��һ��
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.topten,
				null);
		
		listview = (ListView) v.findViewById(R.id.my_list_view);
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			 	ListView listView = (ListView)parent;  
			 	TopTenItem adp = (TopTenItem) listView.getItemAtPosition(position);  
			    String url = adp.getUrl();  
			    changeToContent(url);
			}
		});

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	public void changeToContent(String url){
		Fragment from = null,to = null;
		String toTag = "";
		//from�ǵ�ǰFragment��to��Ҫ�л���Fragment
		from = TopTenTabFragment.childFm.findFragmentByTag("topTenFragment");
		to = new TopTenContentFragment(url);///www.bbs.ustc.edu.cn/cgi/sw?m=1";
		toTag = "OneItem";
		TopTenTabFragment.switchContent(from, to, toTag);
	}
	
	@Override
	public void processFinish() {
		//listDataһ��Ҫ����onCreate�У�onCreate����ֻ�������Fragment����ʱ�Ż�ִ��һ�Ρ�������֤listDataֻ���ʼ��һ��
		//����,����listview���ݳ�ʼ��һ��Ҫ����onCreateView��
		//��Ϊ����ִ�е�onCreateViewʱlistData��û���غã���ʱlistDataΪnull.�������Ҫ�ȵ�listData��ʼ�����
		if(listData.size() == 0){
			Toast.makeText(getActivity(), "��ȡʮ������ʧ��!", 0).show();
		}else{
			ListAdapter adapter = new MyAdapter(getActivity(), 0, listData);
	        listview.setAdapter(adapter);
		}
	}
}
interface AsyncResponse {
    void processFinish();
}
