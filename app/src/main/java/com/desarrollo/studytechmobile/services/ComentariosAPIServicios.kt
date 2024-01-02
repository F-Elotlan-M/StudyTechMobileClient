package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ComentariosAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun obtenerListaComentarios(idVideo: Int): List<ComentariosDTO> {
        val service = httpClient.create(IComentariosAPIServicios::class.java)
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
        val service = httpClient.create(IComentariosAPIServicios::class.java)
        return try {
            val response = service.actualizarComentario(id, comentario)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    fun crearComentario(comentario: ComentariosDTO): Int {
        var Respuesta: Int
        try {
            val service = httpClient.create(IComentariosAPIServicios::class.java)
            val jsonBody = JsonObject().apply {
                addProperty("videoRelacionado", comentario.videoRelacionado)
                addProperty("comentario", comentario.comentario)
                addProperty("username", comentario.username)
            }
            val call = service.crearComentario(jsonBody)
            val response = call.execute()

            if (response.code() == 201){
                return 1
            }else{
                return 0
            }
        }catch(e: Exception){
            println("comentario excepcion $e")
            return 2
        }
        return Respuesta
    }


    suspend fun eliminarComentario(id: Int): Boolean {
        val service = httpClient.create(IComentariosAPIServicios::class.java)
        return try {
            val response = service.eliminarComentario(id)
            response
        } catch (e: Exception) {
            false
        }
    }



}