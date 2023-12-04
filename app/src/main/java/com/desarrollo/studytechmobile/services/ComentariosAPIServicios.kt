package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComentariosAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.70:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = httpClient.create(IComentariosAPIServicios::class.java)

    fun obtenerListaComentarios(idVideo: Int): Pair<List<ComentariosDTO>, Int> {
        try{
            val call = service.obtenerListaComentarios(idVideo)
            call.execute().let {
                if(it.isSuccessful){
                    val comentarios = it.body() ?: emptyList()
                    return Pair(comentarios, 200)
                }else{
                    return Pair(emptyList(), 500)
                }
            }
        }catch (e: Exception){
            return Pair(emptyList(), 500)
        }
    }


    suspend fun actualizarComentario(id: Int, comentario: ComentariosDTO): Boolean {
        return try {
            val response = service.actualizarComentario(id, comentario)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun crearComentario(comentario: ComentariosDTO): ComentariosDTO {
        return try {
            val response = service.crearComentario(comentario)
            response
        } catch (e: Exception) {
            ComentariosDTO()
        }
    }

    suspend fun eliminarComentario(id: Int): Boolean {
        return try {
            val response = service.eliminarComentario(id)
            response
        } catch (e: Exception) {
            false
        }
    }



}