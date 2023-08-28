package dev.shreyansh.androiddevsyt.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


//private const val BASE_URL = "https://devbytes.udacity.com"
//
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//    .baseUrl(BASE_URL)
//    .build()

interface AndroidDevAPIService {
    @GET("/devbytes.json")
    suspend fun getVideos(): AndroidDevResponse
}

//object AndroidDevAPI {
//    val retrofitService: AndroidDevAPIService by lazy {
//        retrofit.create(AndroidDevAPIService::class.java)
//    }
//}