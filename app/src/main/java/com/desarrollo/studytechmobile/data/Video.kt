package com.desarrollo.studytechmobile.data

data class Video (
    var id: Int = 0,
    var nombre: String = "",
    var ruta: String = "",
    var imagen: String = "",
    var fechaSubida: String? = null,
    var fechaModificacion: String? = null,
    var materia: Int = 0,
    var valoracion: Int = 0
)