package com.example.todoapp.repository

import android.util.Log
import com.example.todoapp.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface TaskRepository {
    suspend fun saveTask(task: Task)
    suspend fun getTaskList(): Flow<List<Task>>
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskID: String)
    suspend fun updateTaskOrder(tasks: List<Task>)
}

@Singleton
class TaskRepositoryImpl @Inject constructor(
    dataBase: FirebaseFirestore
) : TaskRepository {
    private val collectionPath = "tasks"
    private val collection = dataBase.collection(collectionPath)

    override suspend fun saveTask(task: Task) {
        try {
            val newTask = task.copy(position = getNextPosition())
            collection.add(newTask).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getTaskList(): Flow<List<Task>> {
        return flow {
            try {
                val tasks = collection
                    .orderBy("position")
                    .get()
                    .await()
                    .toObjects(Task::class.java)
                emit(tasks)
            } catch (e: Exception) {
                emit(emptyList<Task>())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateTask(task: Task) {
        try {
            if (task.id.isEmpty()) {
                return
            }
            collection.document(task.id).set(task).await()
        } catch (e: Exception) {
            Log.d("TaskRepositoryImpl", "updateTask: Failed ${e.message}")
        }
    }

    override suspend fun deleteTask(taskID: String) {
        try {
            collection.document(taskID).delete().await()
            reorderRemainingTasks()  // タスク削除後に残りのタスクの順序を更新
        } catch (e: Exception) {
            Log.d("TaskRepositoryImpl", "deleteTask: Failed ${e.message}")
        }
    }

    override suspend fun updateTaskOrder(tasks: List<Task>) {
        try {
            val batch = collection.firestore.batch()
            tasks.forEachIndexed { index, task ->
                val updatedTask = task.copy(position = index)
                batch.set(collection.document(task.id), updatedTask)
                Log.d("TaskRepositoryImpl", "updateTaskOrder: $updatedTask")
            }
            batch.commit().await()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TaskRepositoryImpl", "updateTaskOrder: Failed ${e.message}")
        }
    }

    private suspend fun getNextPosition(): Int {
        return try {
            val tasks = collection
                .orderBy("position", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()
                .toObjects(Task::class.java)
            if (tasks.isNotEmpty()) tasks[0].position + 1 else 0
        } catch (e: Exception) {
            Log.d("TaskRepositoryImpl", "getNextPosition: Failed ${e.message}")
        }
    }

    private suspend fun reorderRemainingTasks() {
        try {
            val tasks = collection
                .orderBy("position")
                .get()
                .await()
                .toObjects(Task::class.java)
            updateTaskOrder(tasks)
        } catch (e: Exception) {
            Log.e("TaskRepositoryImpl", "reorderRemainingTasks: Failed ${e.message}")
        }
    }
}