package com.example.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.databinding.FragmentDeleteConfirmationDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteConfirmationDialogFragment : BottomSheetDialogFragment() {
    private var deleteConfirmationDialogBinding: FragmentDeleteConfirmationDialogBinding? = null
    private val binding get() = deleteConfirmationDialogBinding!!

    private var onConfirmListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        deleteConfirmationDialogBinding =
            FragmentDeleteConfirmationDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmDeleteButton.setOnClickListener {
            onConfirmListener?.invoke()
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    fun setOnConfirmListener(listener: () -> Unit) {
        onConfirmListener = listener
    }

    companion object {
        const val TAG = "DeleteConfirmationBottomSheet"
    }
}