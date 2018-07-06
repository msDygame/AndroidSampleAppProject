package com.myapplication.androidsampleappproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class PageFour extends PageView
{
    WebView myWebView;
    Switch switchButton;
    public PageFour(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_webview, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("建立一個 WebView 放入以下網站，建立一個Switch，打開Switch可以觸發網站右下角齒輪按鈕的選單，關閉則讓選單消失。");
        myWebView = (WebView) view.findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);//允許可以使用javascript
        myWebView.requestFocus();
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl("http://aqicn.org/city/taiwan/gutin/");
        switchButton = (Switch) view.findViewById(R.id.switchButton) ;
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if (isChecked)
                {
                    Log.d("","checked") ;
                }
                else
                {
                    Log.d("","unchecked") ;
                }
            }
        });
        addView(view);
    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
