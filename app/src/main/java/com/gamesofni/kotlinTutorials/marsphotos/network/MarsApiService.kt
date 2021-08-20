package com.gamesofni.kotlinTutorials.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.gamesofni.kotlinTutorials.marsphotos.network.MarsPhoto
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    // Using scalar converter
    //    .addConverterFactory(ScalarsConverterFactory.create())
    // Using Moshi converter
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    // specifies GET request /photos endpoint
    @GET("photos")
    // suspend to be able to call it from within a coroutine
    // with scalar to String converter
//    suspend fun getPhotos(): String
    // with Moshi to Kotlin Obj converter
    // TODO: another bug, they forgot suspend (also photo instead of photos)
    suspend fun getPhotos(): List<MarsPhoto>

}

// initialize the Retrofit service
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java) }
}
