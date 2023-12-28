package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.services.VideoAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MasTardeFragment: Fragment() {
    private lateinit var videosFavoritos: MutableList<Video>
    private lateinit var videosMasTarde: MutableList<Video>
    private lateinit var recyclerView : RecyclerView
    val videoAPIServicios = VideoAPIServicios()
    private lateinit var adaptadorCurso : AdaptadorCurso

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_mas_tarde, container, false)
        recyclerView = view.findViewById(R.id.masTardeRecyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        videosMasTarde = ArrayList()
        generateDummyCourses()

        return view
    }

    private fun generateDummyCourses() {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                if (isAdded) {
                    videosMasTarde = withContext(Dispatchers.IO) {
                        async { videoAPIServicios.obtenerMasTardePorUsuario(UsuarioSingleton.Id) }.await()!!
                    } as MutableList<Video>

                    videosFavoritos = withContext(Dispatchers.IO) {
                        async { videoAPIServicios.obtenerFavoritosDeUsuario(UsuarioSingleton.Id) }.await()!!
                    } as MutableList<Video>

                    videosMasTarde.forEach { video ->
                        video.isMasTarde = true
                    }

                    videosMasTarde.forEach{video ->
                        video.isFavorito = videosFavoritos.any { it.id == video.id }
                    }

                    println("la lista de mas tarde es: $videosMasTarde")

                    if (recyclerView != null && isAdded && videosMasTarde.isNotEmpty()) {
                        if (videosMasTarde[0] != null) {
                            adaptadorCurso = AdaptadorCurso(requireActivity(), videosMasTarde)
                            recyclerView.adapter = adaptadorCurso

                        } else {
                            Toast.makeText(context, "Error al obtener los videos", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "No hay videos para ver más tarde", Toast.LENGTH_SHORT).show()
                    }

                }
            } catch (e: Exception) {
                // Manejar cualquier excepción que pueda ocurrir durante la operación asíncrona
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}