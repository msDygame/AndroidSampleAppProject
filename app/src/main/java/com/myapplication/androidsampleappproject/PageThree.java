package com.myapplication.androidsampleappproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PageThree extends PageView
{
    Button playButton;
    VideoView videoView;
    private MediaController mediaControls;
    private int position = 0;
    public PageThree(Context context)
    {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.page_video, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(R.string.page_three_tips);
        videoView = (VideoView) view.findViewById(R.id.rtspVideo);
        // set the media controller buttons
        if (mediaControls == null) mediaControls = new MediaController(context);
        playButton = (Button) view.findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RtspStream("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_175k.mov");
            }
        });
        // we also set an setOnPreparedListener in order to know when the video  file is ready for playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                // if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(position);
                if (position == 0)
                {
                    videoView.start();
                }
                else
                {
                    videoView.pause();
                }
            }
        });

        addView(view);
    }

    private void RtspStream(String rtspUrl)
    {
        videoView.setVideoURI(Uri.parse(rtspUrl));
        // set the media controller in the VideoView
        videoView.setMediaController(mediaControls);
        videoView.requestFocus();
        videoView.start();
    }
}
