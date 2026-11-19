package com.ute.studentmvvm.network

import retrofit2.Response
import retrofit2.http.GET

interface StudentApiService {
    @GET("students")
    suspend fun getStudents(): Response<List<StudentDto>>
}
