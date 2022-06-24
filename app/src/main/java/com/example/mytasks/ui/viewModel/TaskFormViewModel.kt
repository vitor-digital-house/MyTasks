package com.example.mytasks.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytasks.data.Task
import com.example.mytasks.data.TaskRepository
import kotlinx.coroutines.launch

class TaskFormViewModel : ViewModel() {

    private val repository = TaskRepository()

    private val _operationResult: MutableLiveData<Result<Unit>> = MutableLiveData()
    val operationResult: LiveData<Result<Unit>> = _operationResult

    fun updateTask(task: Task) {
        _operationResult.value = Result.Loading

        viewModelScope.launch {
            val success = repository.updateTask(task)

            if (success)
                _operationResult.value = Result.Success(Unit)
            else
                _operationResult.value = Result.Error
        }
    }

    fun addTask(task: Task) {
        _operationResult.value = Result.Loading

        viewModelScope.launch {
            val success = repository.addTask(task)

            if (success)
                _operationResult.value = Result.Success(Unit)
            else
                _operationResult.value = Result.Error
        }
    }

    fun provideTask(taskId: String?, title: String, description: String): Task = Task(
        id = taskId ?: repository.getNewId(),
        title = title,
        description = description
    )

}
