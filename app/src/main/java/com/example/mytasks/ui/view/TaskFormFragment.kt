package com.example.mytasks.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytasks.R
import com.example.mytasks.data.Task
import com.example.mytasks.databinding.FragmentTaskFormBinding
import com.example.mytasks.ui.viewModel.Result
import com.example.mytasks.ui.viewModel.TaskFormViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    private lateinit var binding: FragmentTaskFormBinding
    private val viewModel by viewModels<TaskFormViewModel>()

    private val task: Task? by lazy {
        arguments?.getSerializable(TASK_KEY)?.let { it as Task }
    }
    private val isEdition: Boolean
        get() = task != null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        binding = FragmentTaskFormBinding.bind(requireView())
        binding.tvAction.text = if (isEdition) "Edit your task" else "Add your task"
        binding.etTaskTitle.setText(task?.title ?: "")
        binding.etTaskDescription.setText(task?.description ?: "")
        binding.btnSave.setOnClickListener {
            if (isValidTask().not()) return@setOnClickListener
            val task = createTaskFromInput()
            if (isEdition) viewModel.updateTask(task) else viewModel.addTask(task)
        }
    }

    private fun isValidTask(): Boolean {
        return with(binding) {
            val hasValidTitle = etTaskTitle.text.isNullOrBlank().not()
            val hasValidDescription = etTaskDescription.text.isNullOrBlank().not()

            if (hasValidTitle.not())
                ilTaskTitle.error = TITLE_ERROR
            else
                ilTaskTitle.error = null

            if (hasValidDescription.not())
                ilTaskDescription.error = DESCRIPTION_ERROR
            else
                ilTaskDescription.error = null

            hasValidTitle && hasValidDescription
        }
    }

    private fun createTaskFromInput(): Task {
        return with(binding) {
            viewModel.provideTask(
                taskId = task?.id,
                etTaskTitle.text.toString(),
                etTaskDescription.text.toString()
            )
        }
    }

    private fun setupObserver() {
        viewModel.operationResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Error -> showError()
                is Result.Succes -> showSuccess()
            }
        }
    }


    private fun showLoading() {
        updateLoadingView(true)
    }

    private fun showError() {
        updateLoadingView(false)
        Toast.makeText(
            requireContext(),
            "Something went wrong, please try again.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateLoadingView(isLoadingVisible: Boolean) {
        with(binding) {
            progressbar.isVisible = isLoadingVisible
            btnSave.isInvisible = progressbar.isVisible
        }
    }

    private fun showSuccess() {
        updateLoadingView(false)
        binding.btnSave.isEnabled = false
        Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            delay(2_000)
            findNavController().popBackStack()
        }
    }

    companion object {
        const val TASK_KEY = "task"
        private const val TITLE_ERROR = "Please provide a valid title"
        private const val DESCRIPTION_ERROR = "Please provide a valid description"
    }
}

