package com.example.retrofitexample.network

import com.example.retrofitexample.data.model.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("/todos")
    suspend fun getTodos() : Response<List<Todo>>
}