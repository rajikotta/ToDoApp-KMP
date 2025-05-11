package com.raji.todo.domain.model

data class TaskList(val list: Map<String,List<Task>>)
data class Task(val id: String, val title: String, val description: String, val isCompleted: Boolean)