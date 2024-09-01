package com.example.todoapp.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.model.Task
import com.example.todoapp.viewmodel.EditTaskFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTaskFormFragment : BaseTaskFormFragment() {
    override val viewModel: EditTaskFormViewModel by viewModels()
    private val args: EditTaskFormFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.task = args.task
    }

    override fun saveTask() {
        with(binding) {
            val title = titleInput.text.toString().ifEmpty {
                titleInputLayout.error = getString(R.string.add_task_no_title_error_message)
                return
            }
            val task = Task(
                id = args.task.id,
                title = title,
                description = descriptionInput.text.toString(),
                completed = completedCheckbox.isChecked,
                dueDate = viewModel.dueDateTime.value
            )
            viewModel.saveTask(task)
        }
        findNavController().navigate(R.id.action_editTaskFragment_to_todoListFragment)
    }
}