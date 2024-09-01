package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskDomain
import com.example.todoapp.model.toDomain
import com.example.todoapp.usecase.DeleteTaskUseCase
import com.example.todoapp.usecase.GetTaskListUseCase
import com.example.todoapp.usecase.UpdateTaskOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TodoListViewModel
@Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskOrderUseCase: UpdateTaskOrderUseCase
) : ViewModel() {

    private val _taskList = MutableStateFlow<List<TaskDomain>>(emptyList())
    val taskList = _taskList.asStateFlow()

    private val _loadingState = MutableStateFlow(true)
    val loadingState = _loadingState.asStateFlow()

    private val _isTaskEmpty = MutableStateFlow(false)
    val isTaskEmpty = _isTaskEmpty.asStateFlow()

    init {
        loadTaskList()
    }

    private fun loadTaskList() {
        viewModelScope.launch {
            try {
                getTaskListUseCase().collect { taskList ->
                    _taskList.value = taskList.map { it.toDomain() }
                }

                _isTaskEmpty.value = _taskList.value.isEmpty()
            } catch (e: Exception) {
                _isTaskEmpty.value = true
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun deleteTask(taskID: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskID)
        }
        loadTaskList()
    }

    fun updateTaskOrder(currentList: List<TaskDomain>) {
        viewModelScope.launch {
            try {
                updateTaskOrderUseCase(currentList)
                Log.d("TodoListViewModel", "updateTaskOrder: Success")
                loadTaskList()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TodoListViewModel", "updateTaskOrder: Failed ${e.message}")
            }
        }
    }
}
