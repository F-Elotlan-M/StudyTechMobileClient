package com.desarrollo.studytechmobile.data

import java.time.LocalDateTime

data class RespuestaAutenticacion (
    var token: String? = null,
    var expiracion: LocalDateTime? = null
)