package com.example.todoapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Task
import com.example.todoapp.usecase.SaveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskFormViewModel @Inject constructor(
    private val saveTaskUseCase: SaveTaskUseCase
) : BaseTaskFormViewModel() {
    override fun saveTask(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase(task)
        }
    }
}