package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.ReporteDTO
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IReporteAPIServicios {
    @GET("api/Reportes")
    fun getReportes(): Call<List<ReporteDTO>>

    @GET("api/Reportes/{id}")
    suspend fun getReporte(@Path("id") id: Int): ReporteDTO

    @PUT("api/Reportes/{id}")
    suspend fun actualizarReporte(@Path("id") id: Int, @Body reporteDTO: ReporteDTO)

    @POST("api/Reportes")
    fun crearReporte(@Body reporteDTO: JsonObject): Call<ReporteDTO>

    @DELETE("api/Reportes/{id}")
    suspend fun eliminarReporte(@Path("id") id: Int)
}