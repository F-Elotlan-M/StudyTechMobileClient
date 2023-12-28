package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.VideoType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IVideoTypeAPIServicios {
    @GET("api/VideoTypes")
    fun recuperarVideosTypes(): Call<List<VideoType>>
    @POST("api/VideoTypes")
    fun registrarFavoritos(@Body videoType: VideoType): Call<VideoType>
    @DELETE("api/VideoTypes/{id}")
    fun eliminarVideoTypes(@Path("id") id: Int): Call<Int>
}