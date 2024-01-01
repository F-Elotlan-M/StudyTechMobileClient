package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.TipoReporteDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ITipoReporteAPIServicios {
        @GET("api/TipoReportes")
        fun getTiposReporte(): Call<List<TipoReporteDTO>>

        @GET("api/TipoReportes/{id}")
        suspend fun getTipoReporte(@Path("id") id: Int): TipoReporteDTO

        @PUT("api/TipoReportes/{id}")
        suspend fun actualizarTipoReporte(@Path("id") id: Int, @Body tipoReporteDTO: TipoReporteDTO)

        @POST("api/TipoReportes")
        suspend fun crearTipoReporte(@Body tipoReporteDTO: TipoReporteDTO): TipoReporteDTO

        @DELETE("api/TipoReportes/{id}")
        suspend fun eliminarTipoReporte(@Path("id") id: Int)
}