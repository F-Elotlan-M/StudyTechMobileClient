package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.DTOS.ReporteDTO
import com.desarrollo.studytechmobile.data.DTOS.TipoReporteDTO
import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.services.ReporteAPIServicios
import com.desarrollo.studytechmobile.services.TipoReporteAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Reportar : AppCompatActivity() {
    private lateinit var botonReportar: Button
    val tipoReporteAPIServicios = TipoReporteAPIServicios()
    val reporteAPIServicios = ReporteAPIServicios()
    private var listaTipoReporte: List<TipoReporteDTO>? = null

    private var idVideo: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_reportar)
        botonReportar = findViewById<Button>(R.id.saveButton)
        idVideo = intent.getIntExtra("idVideo", 0)
        // Llamada al método para obtener la lista de tipos de reporte
        obtenerTiposReporte()

        botonReportar.setOnClickListener {
            onGuardarClick()
        }
    }

    private fun onGuardarClick() {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        if (selectedRadioButtonId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            enviarReporte(selectedRadioButton.id)
        } else {
            Toast.makeText(this, "Selecciona una opción", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enviarReporte(idTipoReporte: Int){
        var respuesta: Int = 0
        var reporte = ReporteDTO()
        reporte.tipoReporte = idTipoReporte
        reporte.usuarioReporte = UsuarioSingleton.Id
        reporte.videoReporte = idVideo

        CoroutineScope(Dispatchers.IO).launch {
            try {
                respuesta = withContext(Dispatchers.IO) {
                    async { reporteAPIServicios.crearReporte(reporte) }.await()!!
                }
                if (respuesta == 1){
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Reporte registrado", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }else if(respuesta == -1){
                    Toast.makeText(applicationContext, "Error al comentar", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "Ocurrio una excepción", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                println("ocurrió excepcion ui: $e")
            }
        }
    }

    private fun mostrarOpcionesEnRadioGroup(opciones: List<TipoReporteDTO>) {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        for ((index, opcion) in opciones.withIndex()) {
            val radioButton = RadioButton(this)
            radioButton.text = opcion.tipo
            radioButton.id = opcion.id!!
            radioGroup.addView(radioButton)
        }
    }

    private fun obtenerTiposReporte() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaTipoReporte = withContext(Dispatchers.IO) {
                    async { tipoReporteAPIServicios.obtenerTiposReporte() }.await()
                } as List<TipoReporteDTO>

                if (listaTipoReporte != null) {
                    // Muestra las opciones en el RadioGroup
                    mostrarOpcionesEnRadioGroup(listaTipoReporte!!)
                } else {
                    // Maneja el caso en que la lista sea nula o vacía
                    Toast.makeText(this@Reportar, "No se obtuvieron opciones de reporte", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                println("Genera excepción: $e")
            }
        }
    }
}
