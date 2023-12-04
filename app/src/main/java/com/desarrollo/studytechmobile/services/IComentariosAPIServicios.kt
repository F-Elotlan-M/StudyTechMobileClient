package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO
import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.data.RespuestaAutenticacion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IComentariosAPIServicios {

    @POST("api/Comentarios/comentarios_por_video/{id}")
    fun obtenerListaComentarios(@Body idVideo: Int): retrofit2.Call<List<ComentariosDTO>>

    @GET("api/Comentarios")
    suspend fun getComentarios(): retrofit2.Call<List<ComentariosDTO>>

    @PUT("api/Comentarios/{id}")
    suspend fun actualizarComentario(@Path("id") id: Int, @Body comentario: ComentariosDTO): Response<Boolean>

    @POST("api/Comentarios")
    suspend fun crearComentario(@Body comentario: ComentariosDTO): ComentariosDTO

    @DELETE("api/Comentarios/{id}")
    suspend fun eliminarComentario(@Path("id") id: Int): Boolean
}