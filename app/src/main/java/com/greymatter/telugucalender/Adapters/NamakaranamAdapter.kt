package com.greymatter.telugucalender.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greymatter.telugucalender.Model.Ballisastram
import com.greymatter.telugucalender.Model.Festival
import com.greymatter.telugucalender.Model.Namakaranam
import com.greymatter.telugucalender.R

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NamakaranamAdapter(val activity: Activity, namakaranam: ArrayList<Namakaranam>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var namakaranam: ArrayList<Namakaranam>

    init {
        this.namakaranam = namakaranam
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.namakaranam, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val namakaranam: Namakaranam = namakaranam[position]

        holder.tvdescription.setText(namakaranam.description)
        holder.tvTitle.setText(namakaranam.title)
    }

    override fun getItemCount(): Int {
        return namakaranam.size
    }

    internal class ExploreItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvdescription: TextView
        val tvTitle: TextView

        init {
            tvdescription = itemView.findViewById(R.id.tvdescription)
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }
    }
}