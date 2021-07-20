package net.zjueva.homework7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VideoDetailActivity extends AppCompatActivity {
    String mockUrl = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";

    private SeekBar seekBar;
    private TextView textViewTime;
    private TextView textViewCurrentPosition;
    private TextView textViewStatus;
    private VideoView videoView;

    private boolean isChangingProcess=false;

    private String CURRENT="current";
    private Handler handler = new Handler();
    private int current;
    private Runnable runnable = new Runnable() {
        public void run() {

            //在指针移动的同时不进行重绘
            if(videoView.isPlaying()&&!isChangingProcess){
                 current = videoView.getCurrentPosition();
                seekBar.setProgress(current);
                textViewCurrentPosition.setText(time(videoView.getCurrentPosition()));
                System.out.println(current);

            }
            handler.postDelayed(runnable,100);
            System.out.println("is changing? "+isChangingProcess);

        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        if(savedInstanceState!=null){
            current=savedInstanceState.getInt(CURRENT,0);

        }
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        textViewStatus.setText("玩命加载中");

        textViewTime = (TextView) findViewById(R.id.textViewTime);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        textViewCurrentPosition = (TextView) findViewById(R.id.textViewCurrentPosition);


        videoView=findViewById(R.id.vv_detail);

        Intent intent=getIntent();
        String url;
        if(intent!=null&&Intent.ACTION_VIEW.equals(intent.getAction())){
            videoView.setVideoURI(intent.getData());
        } else {
            videoView.setVideoURI(Uri.parse(mockUrl));
        }
//        videoView.setMediaController(new MediaController(this));
        videoView.start();
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(VideoDetailActivity.this, ""+videoView.isPlaying(), Toast.LENGTH_SHORT).show();
                if (videoView.isPlaying()){
                    videoView.pause();
                }else {
                    videoView.start();
                }
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                textViewTime.setText(time(videoView.getDuration()));
                textViewStatus.setText("视频加载完毕");
                beginPlay();
            }
        });
        videoView.setOnCompletionListener(mp -> {
            Toast.makeText(VideoDetailActivity.this, "播放完成,自动重播", Toast.LENGTH_SHORT).show();
            mp.start();
        });
    }


    private void beginPlay(){
        textViewStatus.setText("请您欣赏");
        seekBar.setMax(videoView.getDuration());
        if(current>0){
            videoView.seekTo(current);//切到当前的进度
        }
        videoView.start();
        handler.post(runnable);

    }
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        // 当进度条停止修改的时候触发
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
                // 设置当前播放的位置
            videoView.seekTo(progress);
            isChangingProcess=false;

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isChangingProcess=true;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }
    };

    protected String time(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(videoView!=null){
            outState.putInt(CURRENT,current);
        }

    }
}
