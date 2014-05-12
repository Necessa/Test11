package com.ustc.fragments;

import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

import com.ustc.USTCer.R;
import com.ustc.listview.adpters.MyAdapter;
import com.ustc.model.TopTenItem;
import com.ustc.tabs.TopTenTabFragment;

public class TopTenFragment extends Fragment implements AsyncResponse{
	public static final String TAG = "TopTenFragment";
	private ListView listview;
	private  ArrayList<TopTenItem> listData = new ArrayList<TopTenItem>();
	private boolean dataIsReady = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//ͬonCreateViewһ������ʼҲִ������
		Log.v(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		//listData����Ӧ�÷���onCreate�У�������onCreateView����ΪonCreateֻ��ִ��һ�Σ���onCreateView��ÿ���л�ʱ����ִ��һ��
		String url = "http://bbs.ustc.edu.cn/cgi/bbstop10";
		loadHtmlThread newThread = new loadHtmlThread(listData);
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
        
        public loadHtmlThread(ArrayList<TopTenItem> listData){
        	this.listData = listData;
        }
        
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String url = params[0];
                doc = Jsoup.parse(new URL(url), 5000);
     			Elements trs = doc.select("tr");
     			for(Element tr : trs){
     				Elements tds = tr.select("td");
     				if(tds.size() <= 0)
     					continue;
     				TopTenItem item = new TopTenItem();
     				item.setTitle(tds.get(2).getElementsByTag("a").html().trim());
     				item.setHot(tds.get(4).html().trim());
     				item.setAuthor(tds.get(3).getElementsByTag("a").html().trim());
     				item.setDepartment(tds.get(1).getElementsByTag("a").html().trim());
//     				listData.add(item);
     				
     				//#############����
     				String s=tds.get(2).getElementsByTag("a").attr("href");
     				item.setUrl("http://bbs.ustc.edu.cn/cgi/"+s);
     				listData.add(item);
     				Log.v("��ַ", "http://bbs.ustc.edu.cn/cgi/"+s);
     				//############
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
//            bar.dismiss();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            bar = new ProgressDialog(getActivity());
//            bar.setMessage("�����С�������");
//            bar.setIndeterminate(true);
//            bar.setCancelable(true);
//            bar.show();
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
				// TODO Auto-generated method stub
				//##############����
			 	ListView listView = (ListView)parent;  
			 	TopTenItem adp= (TopTenItem) listView.getItemAtPosition(position);  
			    String url = adp.getUrl();  
//			    Toast.makeText(getActivity().getApplicationContext(), url ,Toast.LENGTH_LONG).show();  
			    changeTocontent(url);
			//#############
			}
		});
		//listDataһ��Ҫ����onCreate�У�onCreate����ֻ�������Fragment����ʱ�Ż�ִ��һ�Ρ�������֤listDataֻ���ʼ��һ��
		//����,����listview���ݳ�ʼ��һ��Ҫ����onCreateView��
		//��Ϊ����ִ�е�onCreateViewʱlistData��û���غã���ʱlistDataΪnull.�������Ҫ�ȵ�listData��ʼ�����
		if(dataIsReady == true && listData.size() > 0){
			processFinish();
		}
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void processFinish() {
		// TODO Auto-generated method stub
		dataIsReady = true;
		ListAdapter adapter = new MyAdapter(getActivity(), 0, listData);
        listview.setAdapter(adapter);
	}
	//################����
	public void changeTocontent(String url){
		Fragment from = null,to = null;
		String toTag = "";
		//from�ǵ�ǰFragment��to��Ҫ�л���Fragment
		from = TopTenTabFragment.childFm.findFragmentByTag("topTenFragment");
//		if((to = TopTenTabFragment.childFm.findFragmentByTag("OneItem")) == null)
			to = new TopTenContentFragment(url);///www.bbs.ustc.edu.cn/cgi/sw?m=1");
		toTag = "OneItem";
		TopTenTabFragment.switchContent(from, to, toTag);
	}
	//###################
}
interface AsyncResponse {
    void processFinish();
}
