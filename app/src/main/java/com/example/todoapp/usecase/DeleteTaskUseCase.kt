package com.example.todoapp.usecase

import com.example.todoapp.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskID: String) = repository.deleteTask(taskID)
}