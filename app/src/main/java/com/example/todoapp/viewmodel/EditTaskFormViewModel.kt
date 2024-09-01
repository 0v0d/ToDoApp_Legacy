package com.example.todoapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Task
import com.example.todoapp.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskFormViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase
) : BaseTaskFormViewModel() {
    override fun saveTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }
}