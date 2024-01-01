package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ComentariosAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.8:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = httpClient.create(IComentariosAPIServicios::class.java)

    fun obtenerListaComentarios(idVideo: Int): List<ComentariosDTO> {
        try {
            println("El id es: $idVideo")
            val call = service.obtenerListaComentarios(idVideo)
            call.execute().let {
                if (it.isSuccessful) {
                    val comentarios = it.body() ?: emptyList()
                    println("Recuperó comentarios: $comentarios")
                    return comentarios
                } else {
                    println("Error en la respuesta: ${it.code()} - ${it.message()}")
                    return emptyList()
                }
            }
        } catch (e: IOException) {
            println("Error de red: $e")
            return emptyList()
        } catch (e: Exception) {
            println("Excepción desconocida: $e")
            return emptyList()
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