package com.example.todo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.todo.databinding.DialogAddTodoBinding
import com.example.todo.models.Priority
import com.example.todo.models.ToDo

class AddToDoDialog(
    private val onAdd: (ToDo) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val binding = DialogAddTodoBinding.inflate(inflater)
        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.add) { _, _ ->
                onPositiveSelected(binding)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->

            }
        return builder.create()
    }

    private fun onPositiveSelected(binding: DialogAddTodoBinding) {
        val name = binding.newTodoName.editText?.text?.toString() ?: ""
        val prioritySelected = binding.groupPriority.checkedRadioButtonId
        when {
            name.isBlank() -> {
                Toast.makeText(
                    requireContext(),
                    "Please enter an item",
                    Toast.LENGTH_LONG
                ).show()
            }
            prioritySelected == -1 -> {
                Toast.makeText(
                    requireContext(),
                    "Please select a priority",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                val priority = Priority.from(prioritySelected)
                val toDo = ToDo(name, priority)
                onAdd(toDo)
            }
        }

    }

}