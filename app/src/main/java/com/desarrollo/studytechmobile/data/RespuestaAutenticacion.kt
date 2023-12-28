package com.desarrollo.studytechmobile.data

data class RespuestaAutenticacion (
    var id: Int? = null,
    var token: String? = null,
    var expiracion: String? = null,
    var tipoUsuaro: Int? = null
)