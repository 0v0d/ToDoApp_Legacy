package com.example.todoapp.view

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.model.Task
import com.example.todoapp.viewmodel.AddTaskFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFormFragment : BaseTaskFormFragment() {
    override val viewModel: AddTaskFormViewModel by viewModels()

    override fun saveTask() {
        with(binding) {
            val title = titleInput.text.toString().ifEmpty {
                titleInputLayout.error = getString(R.string.add_task_no_title_error_message)
                return
            }
            val task = Task(
                title = title,
                description = descriptionInput.text.toString(),
                completed = completedCheckbox.isChecked,
                dueDate = viewModel.dueDateTime.value
            )
            viewModel.saveTask(task)
        }
        findNavController().navigate(R.id.action_addTaskFragment_to_todoListFragment)
    }
}