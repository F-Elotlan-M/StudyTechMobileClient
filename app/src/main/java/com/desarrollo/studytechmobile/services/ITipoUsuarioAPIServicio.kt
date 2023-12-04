package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.TipoUsuarioDTO
import retrofit2.http.*
interface ITipoUsuarioAPIServicio {
    @GET("api/TipoUsuarios")
    suspend fun getTipoUsuarios(): List<TipoUsuarioDTO>

    @GET("api/TipoUsuarios/{id}")
    suspend fun getTipoUsuario(@Path("id") id: Int): TipoUsuarioDTO

    @PUT("api/TipoUsuarios/{id}")
    suspend fun actualizarTipoUsuario(@Path("id") id: Int, @Body tipoUsuarioDTO: TipoUsuarioDTO)

    @POST("api/TipoUsuarios")
    suspend fun crearTipoUsuario(@Body tipoUsuarioDTO: TipoUsuarioDTO): TipoUsuarioDTO

    @DELETE("api/TipoUsuarios/{id}")
    suspend fun eliminarTipoUsuario(@Path("id") id: Int)
}