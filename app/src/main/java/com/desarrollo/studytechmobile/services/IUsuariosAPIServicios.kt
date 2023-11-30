package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.data.RespuestaAutenticacion
import com.desarrollo.studytechmobile.data.UsuarioDTO
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IUsuariosAPIServicios {
    @POST("api/Cuentas/login")
    fun login(@Body credencialesUser: CredencialesUsuario): Call<RespuestaAutenticacion>

    @POST("api/Cuentas/registrar")
    fun registrar(@Body usuario: JsonObject): Call<RespuestaAutenticacion>

    @GET("api/Usuarios/username")
    fun getExistingUser(@Path("username") username:String): Call<UsuarioDTO>
}