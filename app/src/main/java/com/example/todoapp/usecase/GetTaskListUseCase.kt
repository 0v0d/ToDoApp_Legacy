package com.example.todoapp.usecase

import com.example.todoapp.model.Task
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Flow<List<Task>> = repository.getTaskList()
}