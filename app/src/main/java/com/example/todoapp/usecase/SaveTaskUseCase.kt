package com.example.todoapp.usecase

import com.example.todoapp.model.Task
import com.example.todoapp.repository.TaskRepository
import jakarta.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repository.saveTask(task)
}