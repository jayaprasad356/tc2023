package com.telugucalendar.telugupanchangamr.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.telugucalendar.telugupanchangamr.Model.Festival
import com.telugucalendar.telugupanchangamr.Model.Grahalu
import com.telugupanchangam.telugucalender.R


import java.util.*



class GrahaluAdapter(val activity: Activity, festivals: ArrayList<Grahalu>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var festivals: ArrayList<Grahalu>
    private val ITEMS_BETWEEN_ADS = 6

    init {
        this.festivals = festivals
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.moudya_tab_layout, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val festival: Grahalu = festivals[position]

        holder.tvTitle.text = festival.sub_title
        holder.tvDescription.text = festival.sub_description



    }
    override fun getItemCount(): Int {
        return festivals.size
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