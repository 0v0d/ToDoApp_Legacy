package com.example.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todoapp.databinding.FragmentBaseTaskFormBinding
import com.example.todoapp.utility.DateUtility
import com.example.todoapp.viewmodel.BaseTaskFormViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

abstract class BaseTaskFormFragment : Fragment() {
    private var fragmentBaseTaskBinding: FragmentBaseTaskFormBinding? = null
    protected val binding get() = fragmentBaseTaskBinding!!

    protected abstract val viewModel: BaseTaskFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBaseTaskBinding = FragmentBaseTaskFormBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()

    }

    private fun setupUI() {
        with(binding) {
            selectDueDateButton.setOnClickListener { showDatePicker() }
            saveButton.setOnClickListener { saveTask() }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                viewModel.setDueDate(year, month, day)
                showTimePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, hour, minute -> viewModel.setDueTime(hour, minute) },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dueDateTime.collect { dueDateTime ->
                    if (dueDateTime != null) {
                        binding.dueDateText.text =
                            dueDateTime.let { DateUtility().getFormattedDate(it) }
                    }
                }
            }
        }
    }

    protected abstract fun saveTask()

    override fun onDestroyView() {
        fragmentBaseTaskBinding = null
        super.onDestroyView()
    }
}