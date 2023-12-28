package com.desarrollo.studytechmobile.utilidades

import android.content.Context
import android.widget.Toast

class Mensajes {
    fun mostrarMensaje(context: Context, mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}