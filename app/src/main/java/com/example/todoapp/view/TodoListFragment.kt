package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.view.callback.SwipeToDeleteCallback
import com.example.todoapp.view.adapter.TaskListAdapter
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.model.TaskDomain
import com.example.todoapp.viewmodel.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private var fragmentTodoListBinding: FragmentTodoListBinding? = null
    private val binding get() = fragmentTodoListBinding!!

    private val todoListAdapter by lazy {
        TaskListAdapter(onTaskItemClick)
    }

    private val onTaskItemClick: (TaskDomain) -> Unit = { task ->
        navigateToEditTask(task)
    }

    private val viewModel: TodoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTodoListBinding = FragmentTodoListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        binding.addTaskButton.setOnClickListener {
            navigateToAddTask()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { observeTaskList() }
                launch { observeLoadingState() }
                launch { observeIsEmpty() }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.taskRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoListAdapter
            val swipeHandler = SwipeToDeleteCallback(requireContext()) { position ->
                showDeleteConfirmationDialog(position)
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val existingDialog =
            childFragmentManager.findFragmentByTag(
                DeleteConfirmationDialogFragment.TAG
            )
        if (existingDialog != null) {
            todoListAdapter.notifyItemChanged(position)
            return
        }
        val deleteConfirmationDialog = DeleteConfirmationDialogFragment()
        deleteConfirmationDialog.setOnConfirmListener {
            val task = todoListAdapter.currentList[position]
            viewModel.deleteTask(task.id)
        }

        deleteConfirmationDialog.show(childFragmentManager, DeleteConfirmationDialogFragment.TAG)

        todoListAdapter.notifyItemChanged(position)
    }

    private suspend fun observeTaskList() {
        viewModel.taskList.collect {
            binding.taskRecyclerView.isVisible = it.isNotEmpty()
            todoListAdapter.submitList(it)
        }
    }

    private suspend fun observeLoadingState() {
        viewModel.loadingState.collect {
            binding.loadingProgressBar.isVisible = it
        }
    }

    private suspend fun observeIsEmpty() {
        viewModel.isTaskEmpty.collect {
            binding.emptyTaskTextView.isVisible = it
            binding.taskRecyclerView.isVisible = !it
        }
    }

    private fun navigateToAddTask() {
        if (findNavController().currentDestination?.id != R.id.addTaskFragment) {
            findNavController().navigate(
                R.id.action_todoListFragment_to_addTaskFragment
            )
        }
    }

    private fun navigateToEditTask(task: TaskDomain) {
        val action = TodoListFragmentDirections.actionTodoListFragmentToEditTaskFragment(task)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        binding.taskRecyclerView.adapter = null
        fragmentTodoListBinding = null
        super.onDestroyView()
    }
}