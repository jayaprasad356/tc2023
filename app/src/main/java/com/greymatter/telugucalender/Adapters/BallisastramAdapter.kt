package com.greymatter.telugucalender.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greymatter.telugucalender.Model.Ballisastram
import com.greymatter.telugucalender.R
import java.util.*


class BallisastramAdapter(val activity: Activity, ballisastram: ArrayList<Ballisastram>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var ballisastram: ArrayList<Ballisastram>

    init {
        this.ballisastram = ballisastram
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(activity).inflate(R.layout.ballisastram, parent, false)
        return ExploreItemHolder(view)
    }

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, position: Int) {
        val holder = holderParent as ExploreItemHolder
        val ballisastram: Ballisastram = ballisastram[position]

        holder.tvTitle.setText(ballisastram.title)
        holder.tvdescription.setVerticalScrollBarEnabled(true)

        holder.tvdescription.loadDataWithBaseURL("", ballisastram.description, "text/html", "UTF-8", "")
    }

    override fun getItemCount(): Int {
        return ballisastram.size
    }

    internal class ExploreItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvdescription: WebView
        val tvTitle: TextView

        init {
            tvdescription = itemView.findViewById(R.id.tvdescription)
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }
    }
}