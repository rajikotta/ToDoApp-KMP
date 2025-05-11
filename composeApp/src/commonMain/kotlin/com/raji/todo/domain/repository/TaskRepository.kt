package com.raji.todo.domain.repository

import com.raji.todo.domain.model.Task
import com.raji.todo.domain.model.TaskList
import com.raji.todo.network.DataError
import com.raji.todo.network.Result

interface TaskRepository {
    suspend fun getPendingTasks(): Result<TaskList, DataError>

    suspend fun getCompletedTasks(): Result<TaskList, DataError>

    suspend fun addTask(task: Task): Result<Task, DataError>

    suspend fun updateTask(task: Task): Result<Task, DataError>

    suspend fun deleteTask(taskId: String): Result<Unit, DataError>

    suspend fun getTaskById(taskId: String): Result<Task, DataError>
}