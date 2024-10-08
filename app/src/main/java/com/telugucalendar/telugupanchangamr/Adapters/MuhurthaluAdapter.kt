package com.telugucalendar.telugupanchangamr.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telugupanchangam.telugucalender.databinding.MuhurthaluModelBinding

class MuhurthaluAdapter(private val MuhurtahluArrayList : ArrayList<com.telugucalendar.telugupanchangamr.Model.MuhurthamTab>)
     : RecyclerView.Adapter<MuhurthaluAdapter.MuhurthaluItemViewHolder>() {
     private lateinit var  binding : MuhurthaluModelBinding
     inner class MuhurthaluItemViewHolder(val itemView: MuhurthaluModelBinding)
         : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuhurthaluAdapter.MuhurthaluItemViewHolder {
        binding = MuhurthaluModelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MuhurthaluItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MuhurthaluAdapter.MuhurthaluItemViewHolder, position: Int) {
        with(holder) {
            with(MuhurtahluArrayList[position]) {
             binding.tvTitle.text = this.title
             binding.tvDescription.text = this.description
            }
        }
    }

    override fun getItemCount(): Int = MuhurtahluArrayList.size


 }