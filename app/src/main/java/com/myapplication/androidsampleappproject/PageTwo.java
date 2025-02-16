package com.myapplication.androidsampleappproject;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class PageTwo extends PageView
{
    ArrayAdapter<String> fileDBAdapter;
    Button addButton ;
    Button deleteButton;
    EditText editText;
    String sTarget = "";
    public PageTwo(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_firebase, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(R.string.page_two_tips);
        addButton = (Button) view.findViewById(R.id.addButton);
        deleteButton =(Button) view.findViewById(R.id.deleteButton);
        addButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String content = editText.getText().toString();
                boolean is = TextUtils.isEmpty(content) ;
                if (is == false)
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(content);
                    //Log.d("TagSampleAppProject", "DatabaseReference  setValue.\n");
                    //Log.d("TagSampleAppProject", content);
                    String sValue = getRandomNumber();
                    ref.setValue(sValue);
                }

            }
        });
        deleteButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //String content = editText.getText().toString();
                boolean is = TextUtils.isEmpty(sTarget);
                if (is == false)
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(sTarget);
                    //Log.d("TagSampleAppProject", "DatabaseReference  removeValue.\n");
                    //Log.d("TagSampleAppProject", content);
                   ref.removeValue();
                }
            }
        });
        editText = (EditText) view.findViewById(R.id.editText);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        fileDBAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,android.R.id.text1);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("androidsampleappproject");//count=0
        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference("20180706");//count=0
        rootRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Log.d("TagSampleAppProject","DatabaseReference onDataChange.\n");
                long count = dataSnapshot.getChildrenCount();
                Log.d("TagSampleAppProject","count="+count);
                fileDBAdapter.clear();
                int iCount = 0 ;
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String sKey = ds.getKey().toString() ;
                    String sValue = ds.getValue().toString() ;
                    fileDBAdapter.add(sKey+" "+sValue);
                    //Log.d("TagSampleAppProject","getKey()"+sKey);
                    //Log.d("TagSampleAppProject","getValue()="+sValue);
                    if (iCount == (count-1)) sTarget = sKey ;
                    iCount++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
        listView.setAdapter(fileDBAdapter);
        addView(view);
    }

    public String getRandomNumber()
    {
        String id = UUID.randomUUID().toString();
        return id ;
       /*
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return androidId ;
           */
       /*
            String serial = Build.SERIAL;
            return serial ;
           */
    }
}
