package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.CalificacionDTO
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ICalificacionVideoAPIServicios {
    @POST("api/CalificacionVideos")
    fun realizarCalificacion(@Body calificacion: JsonObject): Call<CalificacionDTO>

    @GET("promedioPorVideo/{id}")
    fun obtenerPromedio(@Path("id") idVideo: Int): Call<Int>

    @GET("calificacionVideoPorUsuario")
    fun buscarCalificacion(
        @Query("idUsuario") idUsuario: Int?,
        @Query("idVideo") idVideo: Int?
    ): Call<CalificacionDTO>

    @PUT("api/CalificacionVideos/{id}")
    fun modificarCalificacion(@Path("id") id: Int? ,@Body calificacion: JsonObject): Call<Void>


}