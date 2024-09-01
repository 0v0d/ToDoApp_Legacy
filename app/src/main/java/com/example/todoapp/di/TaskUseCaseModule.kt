package com.example.todoapp.di

import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.usecase.DeleteTaskUseCase
import com.example.todoapp.usecase.GetTaskListUseCase
import com.example.todoapp.usecase.SaveTaskUseCase
import com.example.todoapp.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TaskUseCaseModule {
    @Provides
    fun provideSaveTaskUseCase(repository: TaskRepository): SaveTaskUseCase =
        SaveTaskUseCase(repository)

    @Provides
    fun provideGetTaskListUseCase(repository: TaskRepository): GetTaskListUseCase =
        GetTaskListUseCase(repository)

    @Provides
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase =
        UpdateTaskUseCase(repository)

    @Provides
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase =
        DeleteTaskUseCase(repository)
}