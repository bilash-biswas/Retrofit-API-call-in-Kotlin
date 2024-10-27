package com.example.retrofitapiintregration

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentApi {
    @GET("read.php")
    suspend fun getStudent() : Response<ResponseApi>

    @FormUrlEncoded
    @POST("delete.php/{id}")
    suspend fun deleteStudent(@Field("id") id : String) : Response<DeleteResponse>

    @FormUrlEncoded
    @POST("update.php/{id}")
    suspend fun updateStudent(@Field("id") id : String, @Field("name") name : String, @Field("email") email : String, @Field("salary") salary : String) : Response<DeleteResponse>

    @FormUrlEncoded
    @POST("index.php")
    suspend fun addStudent(@Field("name") name : String, @Field("email") email : String, @Field("salary") salary : String) : Response<DeleteResponse>
}