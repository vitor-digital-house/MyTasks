package com.example.mytasks.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskRepository {

    private val taskDataSource = TaskDB

    suspend fun getTasks(): List<Task> =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                it.resume(taskDataSource.tasks)
            }
        }

    suspend fun addTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                val success: Boolean = taskDataSource.addTask(task)
                it.resume(success)
            }
        }

    suspend fun updateTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                val success = taskDataSource.updateTask(task)
                it.resume(success)
            }
        }

    suspend fun deleteTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                val success = taskDataSource.deleteTask(task)
                it.resume(success)
            }
        }

    fun getNewId(): String = UUID.randomUUID().toString()

}