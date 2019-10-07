package com.example.listapp.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.R
import com.example.listapp.activities.ListDetailActivity
import com.example.listapp.models.TaskList

class TaskSelectionRecyclerViewAdapter(var taskList: ArrayList<TaskList>, val clickListener: TaskSelectionRecyclerViewListener): RecyclerView.Adapter<TaskSelectionRecyclerViewAdapter.TaskViewHolder>(){


    interface TaskSelectionRecyclerViewListener {
        fun listItemClicked(list: TaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val row = taskList[position]
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(taskList[position])
        }
        holder.updateFrom(row)
    }

    fun addList(list: TaskList){
        taskList.add(list)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var taskTitle: TextView = itemView.findViewById<View>(R.id.taskTextView) as TextView

        fun updateFrom(taskList: TaskList){
            taskTitle.text = taskList.name

        }

    }
}