package com.example.todoapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.LayoutItemTaskBinding
import com.example.todoapp.model.TaskDomain

class TaskListViewHolder(
    private val binding: LayoutItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskDomain, onItemClick: (TaskDomain) -> Unit) {
        binding.task = task
        binding.root.setOnClickListener {
            onItemClick(task)
        }
        binding.executePendingBindings()
    }
}
