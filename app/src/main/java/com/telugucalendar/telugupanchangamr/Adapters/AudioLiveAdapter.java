package com.telugucalendar.telugupanchangamr.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity;
import com.telugucalendar.telugupanchangamr.Fragments.AudioPlayFragment;
import com.telugucalendar.telugupanchangamr.Model.Audio;
import com.telugucalendar.telugupanchangamr.helper.Constant;
import com.telugucalendar.telugupanchangamr.helper.Session;
import com.telugucalendar.telugupanchangamr.Activites.HomeActivity;
import com.telugupanchangam.telugucalender.R;


import java.util.ArrayList;


public class AudioLiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Audio> audio;



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

                Session session = new Session(activity);


                session.setData(Constant.AUDIO_TITLE,audio1.getTitle());
                session.setData(Constant.AUDIO_IMAGE,audio1.getImage());
                session.setData(Constant.AUDIO,audio1.getAudio());
                session.setData(Constant.LYRICS,audio1.getLyrics());


                HomeActivity.fm.beginTransaction().replace(R.id.Container, new AudioPlayFragment()).commit();

//                Intent intent = new Intent(activity, AudioPlayActivity.class);
//                intent.putExtra(Constant.AUDIO_TITLE,audio1.getTitle());
//                intent.putExtra(Constant.AUDIO_IMAGE,audio1.getImage());
//                intent.putExtra(Constant.AUDIO,audio1.getAudio());
//                intent.putExtra(Constant.LYRICS,audio1.getLyrics());
//                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {


            return audio.size();


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
