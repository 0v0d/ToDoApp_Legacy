package com.example.todoapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.todoapp.databinding.LayoutItemTaskBinding
import com.example.todoapp.model.TaskDomain
import com.example.todoapp.view.viewholder.TaskListViewHolder

class TaskListAdapter(
    private val onItemClickListener: (TaskDomain) -> Unit
) : ListAdapter<TaskDomain, TaskListViewHolder>(
    diffUtilItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val binding = LayoutItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    private companion object {
        private val diffUtilItemCallback = object : DiffUtil.ItemCallback<TaskDomain>() {
            override fun areContentsTheSame(oldItem: TaskDomain, newItem: TaskDomain): Boolean =
                oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: TaskDomain, newItem: TaskDomain): Boolean =
                oldItem == newItem
        }
    }
}