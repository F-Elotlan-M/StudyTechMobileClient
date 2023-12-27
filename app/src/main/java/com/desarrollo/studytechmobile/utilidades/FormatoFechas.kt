package com.desarrollo.studytechmobile.utilidades

class FormatoFechas {
    fun formatoFechasString (fecha: String?): String{
        val fechaRecortada = fecha?.take(10)
        return fechaRecortada!!
    }
}