package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
class BarraActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_inicio)

        // Cargar FragmentInicio en el contenedor
        supportFragmentManager.beginTransaction()
            .replace(R.id.postrecyclerview, InicioFragment())
            .commit()
    }
}