package com.example.retrofitapiintregration

data class ResponseApi(
    val status : String,
    val message : String,
    val data : ArrayList<StudentMode>
)
