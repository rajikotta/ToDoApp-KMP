package com.raji.todo.data.repositories

import com.raji.todo.data.datasource.TaskRemoteDataSource
import com.raji.todo.domain.model.Task
import com.raji.todo.domain.model.TaskList
import com.raji.todo.domain.repository.TaskRepository
import com.raji.todo.mapper.toTask
import com.raji.todo.mapper.toTaskList
import com.raji.todo.mapper.toTaskRequest
import com.raji.todo.network.DataError
import com.raji.todo.network.Result
import com.raji.todo.network.map

class TaskRepositoryImpl(val taskRemoteDataSource: TaskRemoteDataSource) : TaskRepository {

    override suspend fun getPendingTasks(): Result<TaskList, DataError> {
        return taskRemoteDataSource.getAllPendingTasks().map {
            it.toTaskList()
        }

    }

    override suspend fun getCompletedTasks(): Result<TaskList, DataError> {
        return taskRemoteDataSource.getAllCompletedTasks().map {
            it.toTaskList()
        }
    }

    override suspend fun addTask(task: Task): Result<Task, DataError> {
        return taskRemoteDataSource.save(task.toTaskRequest()).map {
            it.toTask()
        }
    }

    override suspend fun updateTask(task: Task): Result<Task, DataError> {
        return taskRemoteDataSource.save(task.toTaskRequest()).map {
            it.toTask()
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit, DataError> {
        return taskRemoteDataSource.deleteTask(taskId)
    }

    override suspend fun getTaskById(taskId: String): Result<Task, DataError> {
        return taskRemoteDataSource.getTaskById(taskId).map {
            it.toTask()
        }
    }
}