package com.medicalproject.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;

/**
 * 智能分诊Fragment
 * @author 汪文博
 */
public class OfficeRecommendFragment extends Fragment {

    private WebView webView;
    private ProgressBar progressBar;

    public OfficeRecommendFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office_recommend, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.pb_recom);    //进度条
        webView = (WebView) view.findViewById(R.id.webview_recom);    //WebView
        webView.loadUrl("https://robot-lib-achieve.zuoshouyisheng.com/?app_id=5e1ede819ea2ea07fd5f8963");
//        webView.loadUrl("https://www.baidu.com");

        //添加JS监听
        webView.addJavascriptInterface(this, "android");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);     //允许使用JS
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);     //不使用缓存，只从网络获取数据

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);

        return view;
    }

    //处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient(){
        //页面加载完成
        @Override
        public void onPageFinished(WebView view, String url){
            progressBar.setVisibility(View.GONE);
        }

        //页面开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            if(url.equals("http://www.google.com/")){
                Toast.makeText(getActivity(), "国内不能访问google，拦截该url", Toast.LENGTH_LONG).show();
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    //处理JS对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result){
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title){
            super.onReceivedTitle(view, title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress){
            progressBar.setProgress(newProgress);
        }

//        @Override
//        public boolean onKeyDown(int keyCode, KeyEvent event){
//            //点击返回按钮时判断有没有上一页
//            if(webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){
//                webView.goBack();    //返回上一页面
//                return true;
//            }
//            return super.onKeyDown(keyCode, event);
//        }

        //JS调用Android的方法
        @JavascriptInterface
        public void getClient(String str){}

//        @Override
//        protected void onDestroy(){
//            super.onDestroy();
//            //释放资源
//            webView.destroy();
//            webView = null;
//        }

    };

}
