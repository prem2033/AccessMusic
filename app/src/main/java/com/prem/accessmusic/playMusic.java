package com.prem.accessmusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class playMusic extends AppCompatActivity {
    private  static  MediaPlayer mediaPlayer;
    private TextView songsname;
    private SeekBar DurationseekBar;
    private Thread updateSeekBar;
    private int position;
    private Button pauseButton,playforward,palybackward;
    private ArrayList<File> songs;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        songsname=findViewById(R.id.songsName);
        DurationseekBar=findViewById(R.id.Durationseekbar);
        playforward=findViewById(R.id.playforward);
        pauseButton=findViewById(R.id.pausebutton);
        palybackward=findViewById(R.id.playbackward);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Now Playing");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        PlayMusic();
        //update Seekbar on moving songs
//        updateSeekBar=new Thread(){
//            @Override
//            public void run() {
//                int totaltime=mediaPlayer.getDuration();
//                int currentposotion=0;
//                while (currentposotion<totaltime){
//                    try{
//                        sleep(1000);
//                        currentposotion=mediaPlayer.getCurrentPosition();
//                        DurationseekBar.setProgress(currentposotion);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        updateSeekBar.start();
         new Timer().scheduleAtFixedRate(new TimerTask() {
             @Override
             public void run() {
                 DurationseekBar.setProgress(mediaPlayer.getCurrentPosition());
             }

         }, 0, 1000);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(mediaPlayer.isPlaying()) {
                      pauseButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                      mediaPlayer.pause();
                  }else {
                      pauseButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                      mediaPlayer.start();
                  }

            }
        });
        playforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playForward();
            }
        });
        palybackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayBackward();
            }
        });
    }
    public void PlayMusic() {
        Bundle bundle = getIntent().getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("list");
        position = bundle.getInt("position");
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
            Uri uri = Uri.parse(songs.get(position).toString());
            songsname.setText(songs.get(position).getName().toString());
            mediaPlayer = MediaPlayer.create(this, uri);
            setSeekBarOnMusic();
            mediaPlayer.start();
    }
    public  void setSeekBarOnMusic(){
        DurationseekBar.setMax(mediaPlayer.getDuration());
        DurationseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(DurationseekBar.getProgress());//push if seekBar is changed
            }
        });
    }
    public  void PlayBackward(){
        mediaPlayer.stop();
        mediaPlayer.release();
        position=((position-1)<0)?(songs.size()-1):(position-1);
        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        songsname.setText(songs.get(position).getName().toString());
        mediaPlayer.start();
    }
    public void playForward(){
        mediaPlayer.stop();
        mediaPlayer.release();
        position=(position+1)%songs.size();
        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        songsname.setText(songs.get(position).getName().toString());
        mediaPlayer.start();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
