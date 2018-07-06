package com.myapplication.androidsampleappproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class PageThree extends PageView
{
    Button playButton;
    VideoView videoView;
    public PageThree(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_video, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("建立一個影片播放器，播放這個影片串流檔案。");
        videoView = (VideoView) view.findViewById(R.id.rtspVideo);
        playButton = (Button) view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RtspStream("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov");
            }
        });
        addView(view);
    }

    private void RtspStream(String rtspUrl)
    {
        videoView.setVideoURI(Uri.parse(rtspUrl));
        videoView.requestFocus();
        videoView.start();
    }
}
