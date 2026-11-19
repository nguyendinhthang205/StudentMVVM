package com.ute.studentmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val studentCode: String,
    val email: String,
    val gpa: Double,
    val major: String = "Công Nghệ Thông Tin"
)
