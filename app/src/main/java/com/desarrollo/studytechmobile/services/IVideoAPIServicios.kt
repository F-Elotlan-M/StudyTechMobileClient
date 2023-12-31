package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IVideoAPIServicios {
    @GET("api/Videos")
    fun obtenerListaVideos(): Call<List<Video>>
    @GET("api/Videos/{id}")
    fun obtenerVideoPorId(@Path("id")id: Int):Call<Video>
    @GET("favoritos/{id}")
    fun obtenerFavoritosDeUsuario(@Path("id")idUser: Int?):Call<List<Video>>
    @GET("mas-tarde/{id}")
    fun obtenerMasTardeDeUsuario(@Path("id")idUser: Int?):Call<List<Video>>
}