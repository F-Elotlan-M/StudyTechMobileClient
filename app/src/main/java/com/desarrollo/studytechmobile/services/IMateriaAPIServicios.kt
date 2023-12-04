package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.MateriaDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IMateriaAPIServicios {
    @GET("api/Materias")
    suspend fun getMaterias(): List<MateriaDTO>

    @GET("api/Materias/{id}")
    suspend fun getMateria(@Path("id") id: Int): MateriaDTO

    @PUT("api/Materias/{id}")
    suspend fun actualizarMateria(@Path("id") id: Int, @Body materiaDTO: MateriaDTO)

    @POST("api/Materias")
    suspend fun crearMateria(@Body materiaDTO: MateriaDTO): MateriaDTO

    @DELETE("api/Materias/{id}")
    suspend fun eliminarMateria(@Path("id") id: Int)
}