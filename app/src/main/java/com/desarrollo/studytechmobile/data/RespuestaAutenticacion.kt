package com.desarrollo.studytechmobile.data

data class RespuestaAutenticacion (
    var id: Int? = 0,
    var token: String? = "",
    var expiracion: String? = "",
    var tipoUsuario: Int? = 0
)