package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todoapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Date

abstract class BaseTaskFormViewModel : ViewModel() {
    private val _dueDateTime = MutableStateFlow<Date?>(null)
    val dueDateTime = _dueDateTime.asStateFlow()

    private val calendar = Calendar.getInstance()

    fun setDueDate(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
    }

    fun setDueTime(hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        _dueDateTime.value = calendar.time
    }

    abstract fun saveTask(task: Task)
}