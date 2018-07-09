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

public class PageFour extends PageView
{
    WebView myWebView;
    Switch switchButton;
    public PageFour(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_webview, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(R.string.page_four_tips);
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
                    //Log.d("TagSampleAppProject","switchButton checked") ;
                    //Android調用JS腳本是非常簡單的，直接Webview調用loadUrl方法，裡面是JS的方法名，並可以傳入參數，javascript：xxx方法名需要和JS方法名相同
                    //Ex: yourWebView.loadUrl("javascript:functionName");
                    myWebView.loadUrl("javascript:settingsMenu.show()");

                }
                else
                {
                    //Log.d("TagSampleAppProject","switchButton unchecked") ;
                    myWebView.loadUrl("javascript:settingsMenu.close()");
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
