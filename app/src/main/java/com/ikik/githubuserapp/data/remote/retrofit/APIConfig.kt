package com.ikik.githubuserapp.data.remote.retrofit

import com.ikik.githubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig {
    companion object{
        fun getAPIService(): APIService {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val mySuperSecretKey = BuildConfig.KEY
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", mySuperSecretKey)
                    .build()
                chain.proceed(requestHeaders)
            }
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
            val myBaseUrl = BuildConfig.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(myBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}