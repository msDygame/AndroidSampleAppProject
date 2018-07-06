package com.myapplication.androidsampleappproject;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PageOne extends PageView
{
    Button httpGetButton ;
    Button httpPostButton;
    EditText editText;
    RecyclerView recyclerView;
    String sUrl = "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a" ;
    final OkHttpClient client = new OkHttpClient();
    public PageOne(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_http, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("建立列表，點擊任一個RecyclerView的item則開啟WebView顯示連結，以 OkHttp  來處理網路資料。");
        httpGetButton = (Button) view.findViewById(R.id.httpGetButton);
        httpPostButton =(Button) view.findViewById(R.id.httpPostBbutton);
        final ExecutorService service = Executors.newSingleThreadExecutor();
        httpGetButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String content = editText.getText().toString();
                boolean is = TextUtils.isEmpty(content) ;
                if (is == false)
                {
                    Log.d("TagSampleAppProject", "GET  setOnClickListener.\n");
                }
                service.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.d("TagSampleAppProject", "GET  newSingleThreadExecutor.\n");
                        Request request = new Request.Builder().url(sUrl).build();
                        try
                        {
                            Log.d("TagSampleAppProject", "GET  Request.\n");
                            final Response response = client.newCall(request).execute();
                            try
                            {
                                final String resStr = response.body().string();
                                Log.d("TagSampleAppProject", resStr);
                                JSONObject json = new JSONObject(resStr);
                                int jsonCount =(int)json.getJSONObject("result").get("count");
                                Object jsonOb = json.getJSONObject("result").get("results");
                                JSONArray jsonArray = json.getJSONObject("result").getJSONArray("results");

                                Log.d("TagSampleAppProject", " count="+jsonCount);
                                Log.d("TagSampleAppProject", jsonOb.toString());

                                final String owner = jsonArray.getJSONObject(0).getString("E_Name");
                                Log.d("TagSampleAppProject",owner);
                            }
                            catch (JSONException e)
                            {
                                Log.d("TagSampleAppProject", e.toString());
                            }
                            /*    runOnUiThread(new Runnable()
                                                {
                                                  @Override
                                                 public void run() {
                                                       editText.setText(resStr);
                                                 }
                                                 });
                                                */
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            Log.d("TagSampleAppProject", e.toString());
                        }
                    }
                });
            }
        });
        httpPostButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String content = editText.getText().toString();
                boolean is = TextUtils.isEmpty(content);
                if (is == false)
                {
                    Log.d("TagSampleAppProject", "POST  setOnClickListener.\n");
                }
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        /*
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ItemData itemsData[] = {
                new ItemData("hello1"),
                new ItemData("hello2"),
                new ItemData("hello3"),
        };
        MyAdapter mAdapter = new MyAdapter(itemsData);
        recyclerView.setAdapter(mAdapter);*/
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        editText = (EditText) view.findViewById(R.id.editText);
        addView(view);
    }
}
