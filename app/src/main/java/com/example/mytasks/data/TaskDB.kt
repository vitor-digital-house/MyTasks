package com.example.mytasks.data

import kotlin.random.Random

object TaskDB {

    private val mTasks: MutableList<Task> = mutableListOf()
    val tasks: List<Task> = mTasks

    fun addTask(task: Task): Boolean {
        val success = Random.nextBoolean()
        if (success) {
            mTasks.add(task)
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