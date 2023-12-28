package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.desarrollo.studytechmobile.R
import com.google.android.material.bottomnavigation.BottomNavigationView
class BarraActivity: AppCompatActivity() {

    private lateinit var actionBar: ActionBar
    private lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barra)

        actionBar = supportActionBar!!
        actionBar.title = "Profile Activity"

        navigationView = findViewById(R.id.navigation)
        navigationView.setOnNavigationItemSelectedListener(selectedListener)
        actionBar.title = "Inicio"

        val fragment = InicioFragment()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content, fragment, "")
        fragmentTransaction.commit()

    }

    private val selectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId){
            R.id.nav_home -> {
                actionBar.title = "Inicio"
                val fragmentInicio = InicioFragment()
                val transactionFramentInicio: FragmentTransaction = supportFragmentManager.beginTransaction()
                transactionFramentInicio.replace(R.id.content, fragmentInicio, "")
                transactionFramentInicio.commit()
                true
            }

            R.id.nav_buscar -> {
                actionBar.title = "Busqueda"
                val fragmentBusqueda = BuscarFragment()
                val transactionFragmentBuscar: FragmentTransaction = supportFragmentManager.beginTransaction()
                transactionFragmentBuscar.replace(R.id.content, fragmentBusqueda, "")
                transactionFragmentBuscar.commit()
                true
            }

            R.id.nav_favoritos -> {
                actionBar.title = "Favoritos"
                val fragmentFavoritos = FavoritosFrament()
                val transactionFavoritosFrament : FragmentTransaction = supportFragmentManager.beginTransaction()
                transactionFavoritosFrament.replace(R.id.content, fragmentFavoritos, "")
                transactionFavoritosFrament.commit()
                true
            }
            R.id.nav_masTarde -> {
                actionBar.title = "Ver más tarde"
                val fragmentMasTarde = MasTardeFragment()
                val transactionMasTardeFragment: FragmentTransaction = supportFragmentManager.beginTransaction()
                transactionMasTardeFragment.replace(R.id.content, fragmentMasTarde, "")
                transactionMasTardeFragment.commit()
                true
            }


            else -> false
        }

    }
}