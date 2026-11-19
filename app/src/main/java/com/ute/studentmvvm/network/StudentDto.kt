package com.ute.studentmvvm.network

import com.google.gson.annotations.SerializedName
import com.ute.studentmvvm.model.Student

data class StudentDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("studentCode")
    val studentCode: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gpa")
    val gpa: Double,
    @SerializedName("major")
    val major: String? = null
)

fun StudentDto.toEntity(): Student {
    return Student(
        id = id ?: 0,
        fullName = fullName,
        studentCode = studentCode,
        email = email,
        gpa = gpa,
        major = major ?: "Công Nghệ Thông Tin"
    )
}
