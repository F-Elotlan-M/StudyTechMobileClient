package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.desarrollo.studytechmobile.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class BarrAdminActivity : AppCompatActivity() {
    private lateinit var actionBar: ActionBar
    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barra_administrador)

        actionBar = supportActionBar!!
        actionBar.title = "Profile Activity"

        navigationView = findViewById(R.id.navigation)
        navigationView.setOnNavigationItemSelectedListener(selectedListener)
        actionBar.title = "Reportes"
    }

    private val selectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId){
            R.id.nav_Reportes -> {
                actionBar.title = "Reportes"
                val fragmentReportes = FragmentReporte()
                val transactionFramentInicio: FragmentTransaction = supportFragmentManager.beginTransaction()
                transactionFramentInicio.replace(R.id.Admin, fragmentReportes, "")
                transactionFramentInicio.commit()
                true
            }

            R.id.nav_Materias -> {
                actionBar.title = "Cursos"
                val fragmentMaterias = CursoFragment()
                val transactionFragmentMaterias: FragmentTransaction = supportFragmentManager.beginTransaction()
                transactionFragmentMaterias.replace(R.id.Admin, fragmentMaterias, "")
                transactionFragmentMaterias.commit()
                true
            }
            else -> false
        }

    }

}