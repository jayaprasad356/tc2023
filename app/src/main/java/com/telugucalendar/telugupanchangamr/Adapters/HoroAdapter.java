package com.telugucalendar.telugupanchangamr.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.telugucalendar.telugupanchangamr.Model.Gowri;
import com.telugupanchangam.telugucalender.R;

import java.util.ArrayList;


public class HoroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Gowri> gowris;

    public HoroAdapter(Activity activity, ArrayList<Gowri> gowris) {
        this.activity = activity;
        this.gowris = gowris;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.gowri_tab_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Gowri gowri = gowris.get(position);


        holder.tvtime.setText(gowri.getTime());
        holder.tvMorning.setText(gowri.getMorning());
        holder.tvNight.setText(gowri.getNight());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, VideosActivity.class);
//                intent.putExtra(Constant.VIDEO_CATEGORY_ID,videoTab.getId());
//                intent.putExtra(Constant.NAME,videoTab.getName());
//                activity.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount()
    {
        return gowris.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final TextView tvtime,tvNight,tvMorning;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tvtime);
            tvNight = itemView.findViewById(R.id.tvNight);
            tvMorning = itemView.findViewById(R.id.tvMorning);


        }
    }
}
