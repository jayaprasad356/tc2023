package com.greymatter.telugucalender.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greymatter.telugucalender.Activites.AudioPlayActivity;
import com.greymatter.telugucalender.Model.Audio;
import com.greymatter.telugucalender.R;
import com.greymatter.telugucalender.helper.Constant;


import java.util.ArrayList;


public class AudioLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Audio> audio;
    private final int limit = 4;


    public AudioLiveAdapter(Activity activity, ArrayList<Audio> audio) {
        this.activity = activity;
        this.audio = audio;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.audio_tab_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Audio audio1 = audio.get(position);


        holder.tvName.setText(audio1.getTitle());
        Glide.with(activity).load(audio1.getImage()).placeholder(R.drawable.temple_img).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AudioPlayActivity.class);
                intent.putExtra(Constant.AUDIO_TITLE,audio1.getTitle());
                intent.putExtra(Constant.AUDIO,audio1.getAudio());
                intent.putExtra(Constant.LYRICS,audio1.getLyrics());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {

        if(audio.size() > limit){
            return limit;
        }
        else
        {
            return audio.size();
        }

    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final ImageView image;
        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            image = itemView.findViewById(R.id.image);

        }
    }
}
