package com.example.mytasks.data

import kotlin.random.Random

object TaskDB {

    private val mTasks: MutableList<Task> = mutableListOf()
    val tasks: List<Task> = mTasks
    private var nextId: Int = 1

    fun addTask(task: Task): Boolean {
        val success = Random.nextBoolean()
        if (success) {
            val taskWithValidId = task.copy(id = nextId)
            mTasks.add(taskWithValidId)
            nextId++
        }
        return success
    }

    fun updateTask(task: Task): Boolean {
        val success = Random.nextBoolean()
        if (success) {
            val indexToReplace = mTasks.indexOfFirst { it.id == task.id }
            mTasks[indexToReplace] = task
        }
        return success
    }

    fun deleteTask(task: Task): Boolean {
        val success = Random.nextBoolean()
        if (success) {
            val indexToDelete = mTasks.indexOfFirst { it.id == task.id }
            mTasks.removeAt(indexToDelete)
        }
        return success
    }
}