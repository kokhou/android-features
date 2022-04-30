package com.example.myapplication.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {
        val BASE_URL = "https://jsonplaceholder.typicode.com/"

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(25, TimeUnit.SECONDS)

        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                client(client)
                addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            }.build()
        }
    }
}