package com.telugucalendar.telugupanchangamr.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telugucalendar.telugupanchangamr.Model.Bharava;
import com.telugupanchangam.telugucalender.R;

import java.util.ArrayList;


public class BharavaPanchangamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Bharava> bharavas;

    public BharavaPanchangamAdapter(Activity activity, ArrayList<Bharava> bharavas) {
        this.activity = activity;
        this.bharavas = bharavas;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.bharva_tab_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Bharava bharava = bharavas.get(position);


        holder.tvtime.setText(bharava.getTime());
        holder.tvDescription.setText(bharava.getDescription());



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
        return bharavas.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final TextView tvtime,tvDescription;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tvtime);
            tvDescription = itemView.findViewById(R.id.tvDescription);



        }
    }
}
