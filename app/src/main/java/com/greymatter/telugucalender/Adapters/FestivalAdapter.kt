package com.greymatter.telugucalender.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greymatter.telugucalender.Model.Festival
import com.greymatter.telugucalender.R

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



class FestivalAdapter(val activity: Activity, festivals: ArrayList<Festival>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var festivals: ArrayList<Festival>

    init {
        this.festivals = festivals
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.pandugalu_model, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val festival: Festival = festivals[position]
        val strCurrentDate = "Wed, 18 Apr 2012 07:55:29 +0000"
        var format = SimpleDateFormat("yyy-MM-dd")
        var newDate: Date? = null
        try {
            newDate = format.parse(festival.date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        format = SimpleDateFormat("dd-MM-yyy")
        val date = format.format(newDate)
        holder.tvFestival.setText(festival.festival)
        holder.tvDate.text = date
    }

    override fun getItemCount(): Int {
        return festivals.size
    }

    internal class ExploreItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvFestival: TextView
        val tvDate: TextView

        init {
            tvFestival = itemView.findViewById(R.id.tvFestival)
            tvDate = itemView.findViewById(R.id.tvDate)
        }
    }
}