package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.data.VideoType
import com.google.gson.JsonParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException

class VideoTypeAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun recuperarVideoType(): List<VideoType> = withContext(Dispatchers.IO){
        try{
            val service = httpClient.create(IVideoTypeAPIServicios::class.java)
            val call = service.recuperarVideosTypes()
            val response = call.execute()

            val videoType: List<VideoType> = if (response.isSuccessful){
                val videoTypeList = response.body() ?: emptyList()
                println("lista en recuperar es: $videoTypeList")
                videoTypeList
            }else{
                println("Error al obtener la lista de videos: ${response.code()} - ${response.message()}")
                listOf(VideoType(id = -1, tipo = "error"))
            }
            return@withContext videoType
        }catch (e: IOException) {
            // Manejar IOException (problemas de red, E/S, etc.)
            println("Error de E/S: $e")
            return@withContext listOf(VideoType(id = -2, tipo = "io_exception"))
        } catch (e: IllegalStateException) {
            // Manejar IllegalStateException (respuesta inesperada, estado incorrecto)
            println("Error de estado inesperado: $e")
            return@withContext listOf(VideoType(id = -3, tipo = "illegal_state_exception"))
        } catch (e: SocketTimeoutException) {
            // Manejar SocketTimeoutException (tiempo de espera de conexión agotado)
            println("Tiempo de espera de conexión agotado: $e")
            return@withContext listOf(VideoType(id = -4, tipo = "socket_timeout_exception"))
        } catch (e: JsonParseException) {
            // Manejar JsonParseException (problema al parsear la respuesta JSON)
            println("Error al parsear la respuesta JSON: $e")
            return@withContext listOf(VideoType(id = -5, tipo = "json_parse_exception"))
        } catch (e: Exception) {
            // Manejar cualquier otra excepción no esperada
            println("Error no esperado: $e")
            return@withContext listOf(VideoType(id = -6, tipo = "excepcion"))
        }
    }

    //Favoritos y Tarde
    suspend fun eliminarTypeVideo(idVideo: Int, tipoVideo: String): Int = withContext(Dispatchers.IO){
        val service = httpClient.create(IVideoTypeAPIServicios::class.java)

        var salida = 0
        var idEliminar = 0
        val listaVideoType: List<VideoType> = recuperarVideoType()
        println("la lista es: $listaVideoType")
        for(lista in listaVideoType){
            if (lista.idVideo == idVideo && lista.idUsuario == UsuarioSingleton.Id && lista.tipo == tipoVideo){
                idEliminar = lista.id!!
            }
        }
        println("el id es: $idEliminar")
        try{
            val call = service.eliminarVideoTypes(idEliminar)
            val response = call.execute()
            if(response.isSuccessful){
                salida = response.code()
            }else{
                return@withContext response.code()
            }
        }catch(e: Exception){
            println("Message: $e")
            return@withContext -2
        }
        return@withContext salida
    }

    suspend fun agregarFavorito(idVi: Int): Int = withContext(Dispatchers.IO) {
        try {
            val respuesta: Int
            val datosFavorito = VideoType().apply {
                id = 0
                idVideo = idVi
                idUsuario = UsuarioSingleton.Id!!
                tipo = "Favoritos"
            }

            val service = httpClient.create(IVideoTypeAPIServicios::class.java)
            val call = service.registrarFavoritos(datosFavorito)
            val response = call.execute()

            respuesta = if (response.isSuccessful) {
                response.code()
            } else {
                -1
            }

            respuesta
        } catch (e: Exception) {
            // Manejar la excepción según tus necesidades
            -1
        }
    }

    suspend fun agregarMasTarde(idVi: Int): Int = withContext(Dispatchers.IO) {
        try {
            val respuesta: Int
            val datosFavorito = VideoType().apply {
                id = 0
                idVideo = idVi
                idUsuario = UsuarioSingleton.Id!!
                tipo = "Tarde"
            }

            val service = httpClient.create(IVideoTypeAPIServicios::class.java)
            val call = service.registrarFavoritos(datosFavorito)
            val response = call.execute()

            respuesta = if (response.isSuccessful) {
                response.code()
            } else {
                -1
            }

            respuesta
        } catch (e: Exception) {
            // Manejar la excepción según tus necesidades
            -1
        }
    }


}