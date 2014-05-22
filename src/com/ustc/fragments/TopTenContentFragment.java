package com.ustc.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ustc.USTCer.R;
import com.ustc.tabs.TopTenTabFragment;

@SuppressLint("ValidFragment")
public class TopTenContentFragment extends Fragment{
	private static final String TAG = "TopTenContentFragment";
	private static WebView webHolder;
	private WebSettings settings;
	private WebViewClient client;
	private ProgressBar pb; //������
	private Button closeBtn;
	private String url;
	private static boolean est = false;  //�ж��Ƿ��Ѿ�OnCreate������Ҫ������onKeyDown����

	public TopTenContentFragment(String ss)
	{
		this.url = ss;
	}

	@Override  
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		est = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.topten_content, null);
		webHolder = (WebView)v.findViewById(R.id.web_holder);

		pb = (ProgressBar)v.findViewById(R.id.pb);
		pb.setMax(100);

		//����WebView���ԣ�
		settings = webHolder.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("UTF-8");
		client=new OwnerWebView();

		//����Web��ͼ 
		webHolder.setWebViewClient(this.client);

		//������
		webHolder.setWebChromeClient(new WebChromeClient(){
			@Override  
			public void onProgressChanged(WebView view, int newProgress) {  
				pb.setProgress(newProgress);  
				if(newProgress == 100){  
					pb.setVisibility(View.GONE);  
				}  
				super.onProgressChanged(view, newProgress);  
			}  
		});

		webHolder.loadUrl(url);
		
		closeBtn = (Button) v.findViewById(R.id.toptenContent_close);
		closeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				changeTo();
			}
		});
		
		return v;
	}

	//Web��ͼ 
	//���ϣ��������Ӽ����ڵ�ǰ browser ����Ӧ���������¿� Android ��ϵͳ browser ����Ӧ�����ӣ����븲�� webview �� WebViewClient ���� Java
	private class OwnerWebView extends WebViewClient{
		@Override
		//����ҳ�г����Ӱ�ť����Ӧ
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	//���û��� 
	//ActivityĬ�ϵ�back������Ϊ������ǰActivity��webView�鿴�˺ܶ���ҳ��ϣ����back��������һ�������ҳ�棬���ʱ�����Ǿ���Ҫ����Activity��onKeyDown��������������δ���
	public static boolean onKeyDown(int keyCode, KeyEvent event) {  
		if(est == false)  //����û�д�����ť
			return false;

		if (webHolder.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			webHolder.goBack();  
			return true;  
		}  
		else if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)   //ֻ�����һ�£�����webView�Ѿ��˵���
		{
			changeTo();
			return false;
		}     
		return false;
	} 
	
	private static void changeTo(){
		//�л�fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from�ǵ�ǰFragment��to��Ҫ�л���Fragment
		from = TopTenTabFragment.childFm.findFragmentByTag("OneItem");
		if((to = TopTenTabFragment.childFm.findFragmentByTag("topTenFragment")) == null)
			to = new TopTenFragment();
		toTag = "topTenFragment";
		//from������Ҫ����������
//		TopTenTabFragment.childFm.beginTransaction().replace(R.id.tab_root, to,toTag).commit();
		TopTenTabFragment.switchContent(from,to,toTag);
	}
}
