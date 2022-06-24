package com.example.mytasks.data

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TaskRepository {

    private val taskDataSource = Firebase.database.getReference("tasks")

    suspend fun getTasks(): List<Task> =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                taskDataSource.get().addOnCompleteListener { firebaseTask ->
                    if (firebaseTask.isSuccessful) {
                        val tasks: List<Task> = firebaseTask.result.children.mapNotNull {
                            it.getValue(Task::class.java)
                        }
                        it.resume(tasks)
                    } else {
                        throw IllegalStateException()
                    }
                }
            }
        }

    suspend fun addTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                taskDataSource.child(task.id).setValue(task).addOnCompleteListener { firebaseTask ->
                    it.resume(firebaseTask.isSuccessful)
                }
            }
        }

    suspend fun updateTask(task: Task): Boolean = addTask(task)

    suspend fun deleteTask(task: Task): Boolean =
        withContext(Dispatchers.IO) {
            suspendCoroutine {
                taskDataSource.child(task.id).setValue(null).addOnCompleteListener { firebaseTask ->
                    it.resume(firebaseTask.isSuccessful)
                }
            }
        }

    fun getNewId(): String = UUID.randomUUID().toString()

}