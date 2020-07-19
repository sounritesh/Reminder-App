package com.curiositymeetsminds.doreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder (view: View): RecyclerView.ViewHolder (view) {
    val taskId = view.findViewById<TextView>(R.id.taskId)
    val taskName = view.findViewById<TextView>(R.id.taskName)
    val taskDescription = view.findViewById<TextView>(R.id.taskDescription)
}

class TaskAdapter(private val taskList: ArrayList<TaskData>): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_record, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (taskList.isNotEmpty()) taskList.size else 0
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskId.text = task.taskId.toString()
        holder.taskName.text = task.taskName
        holder.taskDescription.text = task.taskDescription
    }
}