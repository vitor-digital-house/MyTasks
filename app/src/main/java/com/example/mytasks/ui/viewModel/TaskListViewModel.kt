package com.example.mytasks.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytasks.data.Task
import com.example.mytasks.data.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {

    private val repository: TaskRepository = TaskRepository()

    private val _tasks: MutableLiveData<Result<List<Task>>> = MutableLiveData()
    val tasks: LiveData<Result<List<Task>>> = _tasks

    fun fetchTasks() {
        _tasks.value = Result.Loading

        viewModelScope.launch {
            try {
                val tasks = repository.getTasks()
                _tasks.value = Result.Succes(tasks)
            } catch (ex: Exception) {
                Log.e(TAG, "fetchTasks: Error when trying to get tasks", ex)
                _tasks.value = Result.Error
            }
        }
    }

    fun deleteTask(task: Task) {
        _tasks.value = Result.Loading

        viewModelScope.launch {
            val success = repository.deleteTask(task)

            if (success)
                fetchTasks()
            else
                _tasks.value = Result.Error
        }
    }

    companion object {
        private const val TAG: String = "TaskListViewModel"
    }
}