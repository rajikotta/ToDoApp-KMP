package com.raji.todo.data.datasource

import com.raji.todo.data.models.TaskRequestDto
import com.raji.todo.data.models.TaskResponseDto
import com.raji.todo.data.models.TaskResponseItem
import com.raji.todo.network.DataError
import com.raji.todo.network.Result
import com.raji.todo.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class TaskRemoteDataSource(val htpClient: HttpClient) {

    suspend fun save(
        taskRequestDto: TaskRequestDto
    ): Result<TaskResponseItem, DataError> {

        return safeCall {
            htpClient.post(urlString = "$BASE_URL/tasks") {
                taskRequestDto.id?.let {
                    parameter("id", it)
                }
                taskRequestDto.completed?.let {
                    parameter("completed", it)
                }
                taskRequestDto.description?.let {
                    parameter("description", it)
                }

                parameter("title", taskRequestDto.title)
            }
        }
    }

    suspend fun getAllCompletedTasks(): Result<TaskResponseDto, DataError> {
        return safeCall {
            htpClient.get(urlString = "$BASE_URL/tasks") {

                parameter("completed", true)
            }

        }
    }

    suspend fun getAllPendingTasks(): Result<TaskResponseDto, DataError> {
        return safeCall {
            htpClient.get(urlString = "$BASE_URL/tasks") {

                parameter("completed", false)
            }

        }
    }

    suspend fun deleteTask(
        id: String
    ): Result<Unit, DataError> {
        return safeCall {
            htpClient.post(urlString = "$BASE_URL/tasks") {
                parameter("id", id)
            }
        }
    }

    suspend fun getTaskById(
        id: String
    ): Result<TaskResponseItem, DataError> {
        return safeCall {
            htpClient.get(urlString = "$BASE_URL/tasks") {
                parameter("id", id)
            }
        }
    }
}