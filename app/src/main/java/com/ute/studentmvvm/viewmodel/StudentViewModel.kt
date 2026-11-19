package com.ute.studentmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    val allStudents: LiveData<List<Student>> = repository.allStudents.asLiveData()

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> get() = _statusMessage

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insert(student)
            _statusMessage.postValue("Thêm sinh viên thành công!")
        } catch (e: Exception) {
            _statusMessage.postValue("Thêm sinh viên thất bại: ${e.localizedMessage}")
        }
    }

    fun update(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.update(student)
            _statusMessage.postValue("Cập nhật sinh viên thành công!")
        } catch (e: Exception) {
            _statusMessage.postValue("Cập nhật thất bại: ${e.localizedMessage}")
        }
    }

    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.delete(student)
            _statusMessage.postValue("Xóa sinh viên thành công!")
        } catch (e: Exception) {
            _statusMessage.postValue("Xóa thất bại: ${e.localizedMessage}")
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.deleteById(id)
            _statusMessage.postValue("Xóa sinh viên thành công!")
        } catch (e: Exception) {
            _statusMessage.postValue("Xóa thất bại: ${e.localizedMessage}")
        }
    }

    fun syncFromCloud() = viewModelScope.launch(Dispatchers.IO) {
        _statusMessage.postValue("Đang đồng bộ dữ liệu...")
        val success = repository.syncFromCloud()
        if (success) {
            _statusMessage.postValue("Đồng bộ dữ liệu thành công!")
        } else {
            _statusMessage.postValue("Đồng bộ dữ liệu thất bại!")
        }
    }
}
