package com.example.todoapp.model

import com.example.todoapp.utility.DateUtility
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Task(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    var dueDate: Date? = null,
    val position : Int = 0
)

fun Task.toDomain() = TaskDomain(
    id = id,
    title = title,
    description = description,
    completed = completed,
    dueDate = dueDate?.let { DateUtility().getFormattedDate(it) } ?: "",
    position = position
)