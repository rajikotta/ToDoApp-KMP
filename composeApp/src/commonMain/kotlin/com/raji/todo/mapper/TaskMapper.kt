package com.raji.todo.mapper

import com.raji.todo.data.models.TaskRequestDto
import com.raji.todo.data.models.TaskResponseDto
import com.raji.todo.data.models.TaskResponseItem
import com.raji.todo.domain.model.Task
import com.raji.todo.domain.model.TaskList


fun TaskResponseItem.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = completed
    )
}

fun TaskResponseDto.toTaskList(): TaskList {
  return  TaskList(
        list = taskList.mapValues { entry ->
            entry.value.map { it.toTask() }
        }
    )
}

fun Task.toTaskRequest(): TaskRequestDto {
    return TaskRequestDto(
        id = id,
        title = title,
        description = description,
        completed = isCompleted
    )
}