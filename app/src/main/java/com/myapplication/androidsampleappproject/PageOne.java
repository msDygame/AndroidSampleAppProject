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
import java.util.ArrayList;
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
    CustomRecyclerView recyclerViewAdapter;
    public PageOne(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_http, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(R.string.page_one_tips);
        httpGetButton = (Button) view.findViewById(R.id.httpGetButton);
        httpPostButton =(Button) view.findViewById(R.id.httpPostBbutton);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        editText = (EditText) view.findViewById(R.id.editText);
        editText.setText(sUrl);
        final ExecutorService service = Executors.newSingleThreadExecutor();
        httpGetButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                service.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Request request = new Request.Builder().url(sUrl).build();
                        try
                        {
                            Log.d("TagSampleAppProject", "Button GET  Request.\n");
                            final Response response = client.newCall(request).execute();
                            try
                            {
                                final String resStr = response.body().string();
                                //Log.d("TagSampleAppProject", "Request response="+resStr);
                                JSONObject json = new JSONObject(resStr);

                                int jsonCount =(int)json.getJSONObject("result").get("count");
                                JSONArray jsonArray = json.getJSONObject("result").getJSONArray("results");

                                int iCount = (int)jsonCount;
                                //Log.d("TagSampleAppProject", " count="+iCount);
                                ArrayList<String> arrayName = new ArrayList<>();
                                ArrayList<String> arrayFile = new ArrayList<>();
                                ArrayList<String> arrayPath = new ArrayList<>();
                                for (int i = 0 ; i < iCount ; i++)
                                {
                                    final String owner = jsonArray.getJSONObject(i).getString("E_Name");
                                    final String image = jsonArray.getJSONObject(i).getString("E_Pic_URL");
                                    final String path = jsonArray.getJSONObject(i).getString("E_URL");
                                    arrayName.add(owner);
                                    arrayFile.add(image);
                                    arrayPath.add(path);
                                }
                                recyclerViewAdapter.setData(arrayName,arrayFile,arrayPath);
                            }
                            catch (JSONException e)
                            {
                                Log.d("TagSampleAppProject", e.toString());
                            }
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

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> myDataSet = new ArrayList<>();
        ArrayList<String> myFileSet = new ArrayList<>();
        ArrayList<String> myPathSet = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            myDataSet.add(Integer.toString(i));
            myFileSet.add("");
            myPathSet.add("");
        }
        recyclerViewAdapter = new CustomRecyclerView(context,myDataSet,myFileSet,myPathSet);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        addView(view);
    }
}
