package com.example.tarefeiro

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TaskAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvDueDate: TextView = itemView.findViewById(R.id.tvDueDate)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(task: Task) {
            tvTaskTitle.text = "Título: ${task.title}"
            tvDescription.text = "Descrição: ${task.description}"
            tvDueDate.text = "Data: ${task.dueDate}"

            btnDelete.setOnClickListener {
                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("tasks").document(task.id).delete()
            }
        }
    }
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
