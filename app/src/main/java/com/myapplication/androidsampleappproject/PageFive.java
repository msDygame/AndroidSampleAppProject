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
        textView.setText("用 ConstraintLayout 設計一個計算機的畫面，不需要功能。");
        addView(view);
    }
}
