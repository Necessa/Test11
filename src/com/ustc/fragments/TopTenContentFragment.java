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
	private ProgressBar pb; //进度条
	private Button closeBtn;
	private String url;
	private static boolean est = false;  //判断是否已经OnCreate过，主要是用在onKeyDown函数

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

		//设置WebView属性，
		settings = webHolder.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("UTF-8");
		client=new OwnerWebView();

		//设置Web视图 
		webHolder.setWebViewClient(this.client);

		//进度条
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

	//Web视图 
	//如果希望点击链接继续在当前 browser 中响应，而不是新开 Android 的系统 browser 中响应该链接，必须覆盖 webview 的 WebViewClient 对象： Java
	private class OwnerWebView extends WebViewClient{
		@Override
		//对网页中超链接按钮的响应
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	//设置回退 
	//Activity默认的back键处理为结束当前Activity，webView查看了很多网页后，希望按back键返回上一次浏览的页面，这个时候我们就需要覆盖Activity的onKeyDown函数，告诉他如何处理
	public static boolean onKeyDown(int keyCode, KeyEvent event) {  
		if(est == false)  //当还没有创建按钮
			return false;

		if (webHolder.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			webHolder.goBack();  
			return true;  
		}  
		else if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)   //只点击了一下，但是webView已经退到底
		{
			changeTo();
			return false;
		}     
		return false;
	} 
	
	private static void changeTo(){
		//切换fragement
		Fragment from = null,to = null;
		String toTag = "";
		//from是当前Fragment，to是要切换的Fragment
		from = TopTenTabFragment.childFm.findFragmentByTag("OneItem");
		if((to = TopTenTabFragment.childFm.findFragmentByTag("topTenFragment")) == null)
			to = new TopTenFragment();
		toTag = "topTenFragment";
		//from不再需要，所以销毁
//		TopTenTabFragment.childFm.beginTransaction().replace(R.id.tab_root, to,toTag).commit();
		TopTenTabFragment.switchContent(from,to,toTag);
	}
}
