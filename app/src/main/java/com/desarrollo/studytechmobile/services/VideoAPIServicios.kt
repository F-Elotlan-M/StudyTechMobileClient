package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.Video
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideoAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.70:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun obtenerListaVideos(): Pair<List<Video>, Int> {
        try {
            val service = httpClient.create(IVideoAPIServicios::class.java)
            val call: Call<List<Video>> = service.obtenerListaVideos()
            var respuesta = 0
            var videos: List<Video> = emptyList()
            val response = call.execute()
            if (response.isSuccessful) {
                videos = response.body() ?: emptyList()
                if (videos.isNotEmpty()) {
                    respuesta = 200
                } else {
                    val errorVideo = Video()
                    respuesta = 500
                    videos = listOf(errorVideo)
                }
            }
            return Pair(videos, respuesta)
        } catch (ex: Exception) {
            return Pair(emptyList(), 500)
        }
    }

}