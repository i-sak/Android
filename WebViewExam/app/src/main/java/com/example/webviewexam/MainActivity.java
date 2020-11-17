package com.example.webviewexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webView);    // xml 자바코드 연결
        mWebView.getSettings().setJavaScriptEnabled(true);  // 자바스크립트 허용

        mWebView.setWebChromeClient(new WebChromeClient()); // 웹뷰에서 크롬이 실행 가능하도록 한다.
        mWebView.setWebViewClient(new WebViewClientClass() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')");
            }
        });
        mWebView.loadUrl("http://192.168.219.109:8080/");   // 웹뷰 실행
    }
    public class MyJavascriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            System.out.println(html);
        }
    }
    // 페이지 이동
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            Log.d("check url", url);
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { // 뒤로 가기 이벤트
        if ( (keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) { // 웹뷰에서 뒤로가기 버튼 클릭
            mWebView.goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

}