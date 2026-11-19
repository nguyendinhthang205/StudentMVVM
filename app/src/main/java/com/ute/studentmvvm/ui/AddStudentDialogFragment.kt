package com.ute.studentmvvm.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.ute.studentmvvm.databinding.DialogAddStudentBinding
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.viewmodel.StudentViewModel

class AddStudentDialogFragment : DialogFragment() {

    private var _binding: DialogAddStudentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by lazy {
        (requireActivity() as MainActivity).viewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddStudentBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Thêm", null)
            .setNegativeButton("Hủy", null)
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val fullName = binding.etFullName.text.toString().trim()
                val studentCode = binding.etStudentCode.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val gpaStr = binding.etGpa.text.toString().trim()

                if (fullName.isEmpty() || studentCode.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập Họ tên và MSSV!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val gpa = gpaStr.toDoubleOrNull() ?: 0.0

                val student = Student(
                    fullName = fullName,
                    studentCode = studentCode,
                    email = email.ifEmpty { "$studentCode@student.ute.edu.vn" },
                    gpa = gpa
                )

                viewModel.insert(student)
                dialog.dismiss()
            }
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
