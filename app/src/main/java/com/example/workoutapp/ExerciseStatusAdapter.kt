package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemBinding

class ExerciseStatusAdapter(val  exerciseItem : ArrayList<ExerciseModels>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

        inner class ViewHolder(binding:ItemBinding):
            RecyclerView.ViewHolder(binding.root){
                val tvItem = binding.txtViewItem
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return exerciseItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val model :ExerciseModels = exerciseItem[position]
        holder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.selected_exercise_id)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.completed_exercise_id)
            }
            else ->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_grey_bg)
            }
        }

    }

}