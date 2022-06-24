package com.example.mytasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.data.Task
import com.example.mytasks.databinding.ItemTaskListBinding

class TaskListAdapter(
    private val onBtnEditClicked: (task: Task) -> Unit,
    private val onBtnDeleteClicked: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {

    private val tasks: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val binding =
            ItemTaskListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) =
        holder.bind(tasks[position])

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(givenTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(givenTasks)
        notifyDataSetChanged()
    }

    inner class TaskListViewHolder(
        private val binding: ItemTaskListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.tvTaskTitle.text = task.title
            binding.tvTaskDescription.text = task.description
            binding.btnEditTask.setOnClickListener { onBtnEditClicked.invoke(task) }
            binding.btnDeleteTask.setOnClickListener { openDeleteDialog(task) }
        }

        private fun openDeleteDialog(task: Task) {
            AlertDialog.Builder(binding.root.context)
                .setTitle("Are you sure that you want to delete this task?")
                .setPositiveButton("Yes, I am sure") { _, _ ->
                    onBtnDeleteClicked.invoke(task)
                }
                .setNegativeButton(
                    "No, cancel it"
                ) { dialog, _ -> dialog?.dismiss() }
                .show()
        }
    }
}