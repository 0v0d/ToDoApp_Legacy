package com.example.todoapp.di

import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.repository.TaskRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TaskRepositoryModule {
    @Provides
    fun provideTaskRepository(dataBase: FirebaseFirestore): TaskRepository =
        TaskRepositoryImpl(dataBase)
}