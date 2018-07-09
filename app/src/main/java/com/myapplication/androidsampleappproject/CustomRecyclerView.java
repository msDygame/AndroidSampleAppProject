package com.myapplication.androidsampleappproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.ViewHolder>
{
    Context context;
    private List<String> mData;
    private List<String> mPicFile;
    private List<String> mWebPath;
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTextView;
        public ImageView mImageView;
        public ViewHolder(View v)
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            mImageView = (ImageView) v.findViewById(R.id.imageView);
            //Log.d("TagSampleAppProject", " CustomRecyclerView'ViewHolder constructor.\n");//step 2
        }
    }
    public CustomRecyclerView(Context context,List<String> dataArray,List<String> fileArray,List<String> pathArray)
    {
        this.context = context ;
        //Log.d("TagSampleAppProject", " CustomRecyclerView constructor.\n");//step1
        mData = dataArray;
        mPicFile = fileArray;
        mWebPath = pathArray ;
    }
    @NonNull
    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
      ViewHolder vh = new ViewHolder(v);
      // Log.d("TagSampleAppProject", " CustomRecyclerView onCreateViewHolder.\n");//step3
      return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        holder.mTextView.setText(mData.get(position));
        // 讀取圖片
        String szFilename = mPicFile.get(position);
        boolean is = TextUtils.isEmpty(szFilename) ;
        if (is == false)
        {
            Picasso.with(context).load(szFilename).placeholder(R.drawable.downloading).into(holder.mImageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
          @Override
            public void onClick(View v)
            {
                String szPath = mWebPath.get(position);
                boolean is = TextUtils.isEmpty(szPath) ;
                if (is == false)
                {
                    Log.d("TagSampleAppProject", " onBindViewHolder setOnClickListener" + mData.get(position));
                    Uri uri = Uri.parse(szPath);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    v.getContext().startActivity(it);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
        @Override
            public boolean onLongClick(View v)
            {
                Log.d("TagSampleAppProject", "onBindViewHolder  setOnLongClickListener.\n");
                return true;
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public void setData(List<String> nameArray,List<String> fileArray,List<String> pathArray)
    {
        mData = nameArray;
        mPicFile = fileArray;
        mWebPath = pathArray;
        //notifyDataSetChanged();
        //notifyItemChanged(iIndex);
    }
}
