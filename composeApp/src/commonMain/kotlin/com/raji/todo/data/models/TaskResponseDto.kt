package com.raji.todo.data.models

import kotlinx.serialization.SerialName

data class TaskResponseDto(val taskList: Map<String, List<TaskResponseItem>>)
data class TaskResponseItem(
    val id: String,
    val title: String,
    @SerialName("remark") val description: String,
    val completed: Boolean
)
