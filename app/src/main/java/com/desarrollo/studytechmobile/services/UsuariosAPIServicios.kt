package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.data.RespuestaAutenticacion
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UsuariosAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.70:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun login(username: String, password: String): RespuestaAutenticacion {
        val credenciales = RespuestaAutenticacion()
        var token = credenciales.token
        val service = httpClient.create(IUsuariosAPIServicios::class.java)
        try{
            val datosLogin = CredencialesUsuario()
            datosLogin.username = username
            datosLogin.password = password
            datosLogin.nombre = ""
            datosLogin.tipo = 0

            val call = service.login(datosLogin)
            val response = call.execute()

            if(response.isSuccessful){
                val credenciales = response.body()!!
                return credenciales
            }else {
                credenciales.token = "error"
                return credenciales
            }
        }catch (e: IOException) {
            // Manejar problemas de red, como falta de conexión
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "network_error"
            return credenciales
        } catch (e: ConnectException) {
            // Capturar específicamente problemas de conexión (subtipo de IOException)
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "connect_error"
            return credenciales
        } catch (e: SocketTimeoutException) {
            // Capturar específicamente errores de tiempo de espera de conexión (subtipo de IOException)
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "timeout_error"
            return credenciales
        } catch (e: UnknownHostException) {
            // Capturar específicamente errores de host desconocido (subtipo de IOException)
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "unknown_host_error"
            return credenciales
        } catch (e: HttpException) {
            // Manejar errores HTTP del servidor
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "http_error"
            return credenciales
        } catch (e: IllegalArgumentException) {
            // Capturar específicamente errores de argumentos inválidos
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "invalid_argument_error"
            return credenciales
        } catch (e: IllegalStateException) {
            // Capturar específicamente errores de estado incorrecto
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "illegal_state_error"
            return credenciales
        } catch (e: Exception) {
            // Capturar cualquier otra excepción
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "unknow_error"
            return credenciales
        }
    }

    fun registrarUsuario(username: String, password: String, nombre: String, tipo: Int){

    }
}
