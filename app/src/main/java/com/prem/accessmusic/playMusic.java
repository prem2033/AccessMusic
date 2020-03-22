package com.prem.accessmusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;

public class playMusic extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        PlayMusic();
    }
    public void PlayMusic() {
        Bundle bundle = getIntent().getExtras();
        ArrayList<File> songs = (ArrayList) bundle.getParcelableArrayList("list");
        int position = bundle.getInt("position");
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        Toast.makeText(this, "playMusic", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
    }
}
