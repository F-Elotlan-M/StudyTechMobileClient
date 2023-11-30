package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.Video
import retrofit2.Call
import retrofit2.http.GET
interface IVideoAPIServicios {
    @GET("api/Videos")
    fun obtenerListaVideos(): Call<List<Video>>
}