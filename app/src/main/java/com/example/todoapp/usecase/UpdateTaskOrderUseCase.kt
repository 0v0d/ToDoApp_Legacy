package com.example.todoapp.usecase

import com.example.todoapp.model.TaskDomain
import com.example.todoapp.model.toEntity
import com.example.todoapp.repository.TaskRepository
import javax.inject.Inject


class UpdateTaskOrderUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskList: List<TaskDomain>) =
        taskRepository.updateTaskOrder(taskList.map { it.toEntity() })
}