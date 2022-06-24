package com.example.mytasks.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytasks.R
import com.example.mytasks.data.Task
import com.example.mytasks.databinding.FragmentTaskListBinding
import com.example.mytasks.ui.adapter.TaskListAdapter
import com.example.mytasks.ui.viewModel.Result
import com.example.mytasks.ui.viewModel.TaskListViewModel

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var binding: FragmentTaskListBinding
    private val viewModel by viewModels<TaskListViewModel>()

    private val taskListAdapter = TaskListAdapter(::onBtnEditClicked, ::onBtnDeleteClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserver()
    }

    private fun setupViews() {
        binding = FragmentTaskListBinding.bind(requireView())
        with(binding) {
            rvTaskList.adapter = taskListAdapter
            fabNewTask.setOnClickListener { navigateToTaskEditionFragment() }
            swipeLayout.setOnRefreshListener { viewModel.fetchTasks() }
        }
    }

    private fun navigateToTaskEditionFragment(bundle: Bundle? = null) {
        findNavController().navigate(R.id.action_taskListFragment_to_taskFormFragment, bundle)
    }

    private fun setupObserver() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading()
                is Result.Error -> showError()
                is Result.Succes -> showContent(it.data)
            }
        }
    }

    private fun showLoading() {
        updateLoadingView(true)
    }

    private fun showError() {
        updateLoadingView(false)
        binding.swipeLayout.isRefreshing = false
        Toast.makeText(
            requireContext(),
            "Something went wrong, please try again.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateLoadingView(isLoadingVisible: Boolean) {
        with(binding) {
            progressbar.isVisible = isLoadingVisible
            clContent.isVisible = progressbar.isVisible.not()
        }
    }

    private fun showContent(tasks: List<Task>) {
        updateLoadingView(false)
        binding.swipeLayout.isRefreshing = false
        taskListAdapter.updateTasks(tasks)
    }

    private fun onBtnEditClicked(task: Task) {
        val bundle = TaskFormFragment.buildBundle(task)
        navigateToTaskEditionFragment(bundle)
    }

    private fun onBtnDeleteClicked(task: Task) = viewModel.deleteTask(task)
}