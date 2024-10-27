package com.example.retrofitapiintregration
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val BASE_URL = "http://192.168.0.103/StudentDatabase/"
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val client = OkHttpClient.Builder().build()
    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}