package com.example.todoapp.model

import android.os.Parcelable
import com.example.todoapp.utility.DateUtility
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDomain(
    val id: String,
    val title: String,
    val description: String,
    val completed: Boolean,
    val dueDate: String,
    val position: Int,
) : Parcelable

fun TaskDomain.toEntity() = Task(
    id = id,
    title = title,
    description = description,
    completed = completed,
    dueDate = DateUtility().parseDate(dueDate),
    position = position
)