package com.example.listapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.R
import com.example.listapp.models.Task
import com.example.listapp.models.TaskList
import kotlinx.android.synthetic.main.item_step.view.*

class StepAdapter(var list: TaskList): RecyclerView.Adapter<StepAdapter.StepViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = StepViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false))
        return view
    }

    override fun getItemCount(): Int = list.tasks.size

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val row = list.tasks[position]
        holder.updateFrom(row)
    }


    inner class StepViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var stepTitle : TextView = itemView.findViewById<TextView>(R.id.stepNameTextView)

        fun updateFrom(task: String){
            stepTitle.text = task
        }
    }
}
