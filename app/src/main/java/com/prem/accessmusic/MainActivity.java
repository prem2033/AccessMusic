package com.prem.accessmusic;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prem.accessmusic.RecylerViewMethod.RecylerViewAdapterClass;

import java.io.File;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity  implements RecylerViewAdapterClass.OnNoteListener {
    private ListView listView;
     String[] songsName;
     RecyclerView recyclerView;
    ArrayList<File> songs;
    private  RecylerViewAdapterClass recylerViewAdapterClass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // listView=findViewById(R.id.listview);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setLogo(R.drawable.ic_whatshot_black_24dp);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        if(!CheckPermissions())
             RequestPermissions();
        recyclerView=findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songs=readSong(Environment.getExternalStorageDirectory());
        songsName=new String[songs.size()];
        for(int i=0;i<songs.size();i++){
            songsName[i]=songs.get(i).getName();//.replace(".mp3","");
        }
        recylerViewAdapterClass = new RecylerViewAdapterClass(MainActivity.this, songsName,this);
       recyclerView.setAdapter(recylerViewAdapterClass);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.songs_layout,R.id.textView,songsName);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    private ArrayList<File> readSong(File root){
        ArrayList<File> arrayList=new ArrayList<File>();
        File files[]=root.listFiles();
        for(File file: files){
            if(file.isDirectory()){
                arrayList.addAll(readSong(file));
            }else{
                if(file.getName().endsWith(".mp3")){
                    arrayList.add(file);
                }
            }
        }
        return arrayList;
    }
    public  boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public void RequestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 1);
    }
    @Override
    public void onNoteClick(int position) {
        Toast.makeText(MainActivity.this, "Clicked onNotemethod", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,playMusic.class);
        intent.putExtra("position",position);
        intent.putExtra("list",songs);
       startActivity(intent);

    }
}
