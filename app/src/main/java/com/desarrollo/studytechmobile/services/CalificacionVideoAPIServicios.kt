package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.CalificacionDTO
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CalificacionVideoAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun obtenerCalificacion(idVideo: Int): Int{
        var calificacion: Int
        val service =  httpClient.create(ICalificacionVideoAPIServicios::class.java)
        val call = service.obtenerPromedio(idVideo)
        try {
            val response = call.execute()
            if(response.isSuccessful){
                calificacion = response.body()!!
                println("la respuesta es: $calificacion")
            }else{
                return -1
            }
        }catch (e: Exception){
            println("la excepcion de calificacion: $e")
            calificacion = -2
        }
        println("el return es: $calificacion")
        return calificacion
    }

    fun buscarCalificacion(idVideo: Int?, idUsuario: Int?): CalificacionDTO? {
        val respuestaCalificacon = CalificacionDTO()
        try{
            val service =  httpClient.create(ICalificacionVideoAPIServicios::class.java)
            val call = service.buscarCalificacion(idUsuario, idVideo)
            val response = call.execute()
            if(response.isSuccessful){
                println("buscar calificación respuesta bien: ${response.code()}")
                val respuestaCalificacon = response.body()!!
                if(respuestaCalificacon.id != null){
                    return respuestaCalificacon
                }else{
                    return null
                }
            }else{
                respuestaCalificacon.id = -1
                return respuestaCalificacon
            }
        }catch (e: Exception){
            println("ocurrió excepcion buscar calificación: $e")
            respuestaCalificacon.id = -2
            return respuestaCalificacon
        }
    }

    fun realizarCalificacion(calificacion: CalificacionDTO): Int{
        var respuesta: Int = 0
        var calificacionObjeto: CalificacionDTO? = null
        val service =  httpClient.create(ICalificacionVideoAPIServicios::class.java)
        calificacionObjeto = buscarCalificacion(calificacion.videoRelacionado, calificacion.usuarioRelacionado)
        if (calificacionObjeto != null) {
            if(calificacionObjeto.id == -1){
                try {
                    val jsonBody = JsonObject().apply {
                        addProperty("calificacionUsuario", calificacion.calificacionUsuario)
                        addProperty("videoRelacionado", calificacion.videoRelacionado)
                        addProperty("usuarioRelacionado", calificacion.usuarioRelacionado)
                    }
                    val call = service.realizarCalificacion(jsonBody)
                    val response = call.execute()
                    if(response.isSuccessful){
                        println("la respuesta: ${response.code()}")
                        respuesta = 1
                    }else{
                        respuesta = -1
                    }
                }catch (e: Exception){
                    println("ocurrió excepcion en calificacion: $e")
                    respuesta = -2
                }
            }else{
                try {
                    val jsonBody2 = JsonObject().apply {
                        addProperty("id", calificacionObjeto.id)
                        addProperty("calificacionUsuario", calificacion.calificacionUsuario)
                        addProperty("videoRelacionado", calificacion.videoRelacionado)
                        addProperty("usuarioRelacionado", calificacion.usuarioRelacionado)
                    }
                    val call = service.modificarCalificacion(calificacionObjeto.id, jsonBody2)
                    val response = call.execute()
                    if(response.isSuccessful){
                        println("la respuesta: ${response.code()}")
                        respuesta = 1
                    }else{
                        respuesta = -1
                    }
                }catch (e: Exception){
                    println("ocurrió excepcion en calificacion: $e")
                    respuesta = -2
                }
            }
        }
        return respuesta
    }
}