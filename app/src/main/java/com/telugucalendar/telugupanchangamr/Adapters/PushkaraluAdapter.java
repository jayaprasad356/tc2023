package com.telugucalendar.telugupanchangamr.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telugucalendar.telugupanchangamr.Model.Gowri;
import com.telugucalendar.telugupanchangamr.Model.Pushkaralu;
import com.telugupanchangam.telugucalender.R;

import java.util.ArrayList;


public class PushkaraluAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity activity;
    ArrayList<Pushkaralu> gowris;

    public PushkaraluAdapter(Activity activity, ArrayList<Pushkaralu> gowris) {
        this.activity = activity;
        this.gowris = gowris;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.pushkaralu_tab_layout, parent, false);
        return new ExploreItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ExploreItemHolder holder = (ExploreItemHolder) holderParent;
        final Pushkaralu gowri = gowris.get(position);



        holder.wbsub_title.setVerticalScrollBarEnabled(true);

        holder.wbsub_title.loadDataWithBaseURL("file:///android_asset/fonts/", "<html>\n" +
                "<head>\n" +
                "<link rel='stylesheet' type='text/css' href='file:///android_asset/fonts/ramabhadra.css'>" +
                "<style>\n" +
                "body {\n" +
                "    font-family: 'ramabhadra';\n" +
                "    text-align: center;\n" + // Center-align the text horizontally
                "}\n" +
                "</style>" +
                "</head>" +
                "<body>\n" +
                 gowri.getSub_title()+
                "</body>\n" +
                "</html>", "text/html", "UTF-8", "");

        holder.wbsub_title.getSettings().setJavaScriptEnabled(true);
        holder.wbsub_title.getSettings().setLoadWithOverviewMode(true);

 holder.wbsub_description.setVerticalScrollBarEnabled(true);

        holder.wbsub_description.loadDataWithBaseURL("file:///android_asset/fonts/", "<html>\n" +
                "<head>\n" +
                "<link rel='stylesheet' type='text/css' href='file:///android_asset/fonts/ramabhadra.css'>" +
                "<style>\n" +
                "body {\n" +
                "    font-family: 'ramabhadra';\n" +
                "    text-align: center;\n" + // Center-align the text horizontally
                "}\n" +
                "</style>" +
                "</head>" +
                "<body>\n" +
                 gowri.getSub_description()+
                "</body>\n" +
                "</html>", "text/html", "UTF-8", "");

        holder.wbsub_description.getSettings().setJavaScriptEnabled(true);
        holder.wbsub_description.getSettings().setLoadWithOverviewMode(true);



    }

    @Override
    public int getItemCount()
    {
        return gowris.size();
    }

    static class ExploreItemHolder extends RecyclerView.ViewHolder {
        final WebView wbsub_title,wbsub_description;

        public ExploreItemHolder(@NonNull View itemView) {
            super(itemView);
            wbsub_title = itemView.findViewById(R.id.wbsub_title);
            wbsub_description = itemView.findViewById(R.id.wbsub_description);
          


        }
    }
}
