package com.desarrollo.studytechmobile.data
import java.io.Serializable

data class Usuario (
    var Id: Int? = null,
    var Username: String? = null,
    var Tipo: Int? = null,
    var Contrasena: String? = null,
    var NombreDeUsuario: String? = null
): Serializable