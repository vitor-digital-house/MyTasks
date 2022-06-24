package com.example.mytasks.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskRepository {
    private val myDB = TaskDB

    suspend fun getTasks(): List<Task> =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                it.resume(myDB.tasks)
            }
        }

    suspend fun addTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                val success: Boolean = myDB.addTask(task)
                it.resume(success)
            }
        }


    suspend fun updateTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                val success = myDB.updateTask(task)
                it.resume(success)
            }
        }

    suspend fun deleteTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            delay(1_000)
            suspendCoroutine {
                val success = myDB.deleteTask(task)
                it.resume(success)
            }
        }

}