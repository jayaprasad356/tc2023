package com.telugucalendar.telugupanchangamr.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.telugucalendar.telugupanchangamr.Model.Festival
import com.telugucalendar.telugupanchangamr.Model.Grahalu
import com.telugucalendar.telugupanchangamr.Model.Pushkaralu
import com.telugupanchangam.telugucalender.R


import java.util.*



class PushkaraluAdapter(val activity: Activity, pushkaralu: ArrayList<Pushkaralu>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var pushkaralu: ArrayList<Pushkaralu>

    init {
        this.pushkaralu = pushkaralu
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.moudya_tab_layout, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val pushkaralu: Pushkaralu = pushkaralu[position]

        holder.tvTitle.text = pushkaralu.sub_title
        holder.tvDescription.text = pushkaralu.sub_description



    }
    override fun getItemCount(): Int {
        return pushkaralu.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    internal class ExploreItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView
        val tvDescription: TextView


        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
        }
    }
}