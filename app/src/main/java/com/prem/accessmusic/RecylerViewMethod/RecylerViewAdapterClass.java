package com.prem.accessmusic.RecylerViewMethod;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prem.accessmusic.MainActivity;
import com.prem.accessmusic.R;
import com.prem.accessmusic.playMusic;

import java.util.Arrays;
import java.util.List;
public class RecylerViewAdapterClass extends RecyclerView.Adapter<RecylerViewAdapterClass.ViewHolder> {
    private Context context;
    private List<String> songsList;
    OnNoteListener monNoteListener;
    //constructor to initilize variable
    public RecylerViewAdapterClass(Context context, String[] SongsList,OnNoteListener onNoteListener) {
        this.context = context; //setting the Contact
        this.songsList = Arrays.asList((SongsList)); //Stroes Contact List
        this.monNoteListener=onNoteListener;

    }
    //design of the card or the view
    @NonNull
    @Override
    public RecylerViewAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creating view for the card
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_for_songs, parent, false);
        return new ViewHolder(view,monNoteListener);//returing view holder
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewAdapterClass.ViewHolder holder, int position) {
       // String  contact = songsList.get(position); //get the data from position
        holder.textView.setText((CharSequence) songsList.get(position)); //set using holder
    }

    //item count to show in recylwe View
    @Override
    public int getItemCount() {
        return songsList.size();
    }
    //definde the Holder of the Card
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView imageView;
        public TextView textView;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewcard);
            textView = itemView.findViewById(R.id.textViewcard);
             imageView.setOnClickListener(this);//for the listener
            textView.setOnClickListener(this);
            this.onNoteListener=onNoteListener;

        }

        @Override
        public void onClick(View v) {
              onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
