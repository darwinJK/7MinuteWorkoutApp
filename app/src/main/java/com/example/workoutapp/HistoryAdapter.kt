package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ActivityHistoryBinding
import com.example.workoutapp.databinding.HistoryLayoutBinding

class HistoryAdapter(val item : ArrayList<exerciseHistory>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(binding : HistoryLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        val date = binding.datetxtTvLl
        val layout = binding.llRcHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history :exerciseHistory = item[position]
        holder.date.text = history.date

        if(position%2 == 0){
            holder.layout.setBackgroundColor(Color.parseColor("#c3c3c3"))
        }else{
            holder.layout.setBackgroundColor(Color.parseColor("#ebebeb"))
        }
    }
}