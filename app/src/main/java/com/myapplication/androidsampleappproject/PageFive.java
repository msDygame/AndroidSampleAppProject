package com.myapplication.androidsampleappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PageFive extends PageView
{
    public PageFive(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_calculator, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(R.string.page_five_tips);
        addView(view);
    }
}
