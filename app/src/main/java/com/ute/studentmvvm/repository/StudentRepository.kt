package com.ute.studentmvvm.repository

import com.ute.studentmvvm.data.StudentDao
import com.ute.studentmvvm.model.Student
import com.ute.studentmvvm.network.StudentApiService
import com.ute.studentmvvm.network.toEntity
import kotlinx.coroutines.flow.Flow

class StudentRepository(
    private val studentDao: StudentDao,
    private val apiService: StudentApiService
) {
    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun delete(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun deleteById(id: Int) {
        studentDao.deleteStudentById(id)
    }

    suspend fun getStudentByCode(code: String): Student? {
        return studentDao.getStudentByCode(code)
    }

    suspend fun syncFromCloud(): Boolean {
        return try {
            val response = apiService.getStudents()
            if (response.isSuccessful) {
                response.body()?.let { dtos ->
                    val entities = dtos.map { it.toEntity() }
                    studentDao.insertAll(entities)
                    true
                } ?: false
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
