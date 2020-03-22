package com.prem.accessmusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class playMusic extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    TextView textView;
    SeekBar DurationseekBar;
    ImageButton playButton,pauseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        textView=findViewById(R.id.songsName);
        DurationseekBar=findViewById(R.id.durationseekbar);
        playButton=findViewById(R.id.palybutton);
        pauseButton=findViewById(R.id.pausebutton);
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        PlayMusic();
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButotnClick();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButtonClick();
            }
        });

    }
    public void PlayMusic() {
        Bundle bundle = getIntent().getExtras();
        ArrayList<File> songs = (ArrayList) bundle.getParcelableArrayList("list");
        int position = bundle.getInt("position");
        Uri uri = Uri.parse(songs.get(position).toString());
        textView.setText(songs.get(position).getName().toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        setSeekBarOnMusic();
        Toast.makeText(this, "playMusic", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
    }
    public  void setSeekBarOnMusic(){
        DurationseekBar.setMax(mediaPlayer.getDuration());
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                DurationseekBar.setProgress(mediaPlayer.getCurrentPosition());
//            }
//
//        }, 0, 1000);

        DurationseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    public void pauseButotnClick(){
        mediaPlayer.pause();
    }
    public void playButtonClick(){
        mediaPlayer.start();
    }
}
