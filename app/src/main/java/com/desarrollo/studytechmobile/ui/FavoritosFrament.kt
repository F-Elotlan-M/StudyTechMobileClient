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

class FavoritosFrament: Fragment() {
    private lateinit var videosFavoritos: MutableList<Video>
    private lateinit var videosMasTarde: MutableList<Video>
    private lateinit var recyclerView : RecyclerView
    val videoAPIServicios = VideoAPIServicios()
    private lateinit var adaptadorCurso : AdaptadorCurso

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        recyclerView = view.findViewById(R.id.favoritosRecyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        videosFavoritos = ArrayList()
        generateDummyCourses()

        return view
    }

    private fun generateDummyCourses() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val id = 1
                //UsuarioSingleton.Id
                // Verificar si el fragmento está adjunto a una actividad
                if (isAdded) {
                    videosFavoritos = withContext(Dispatchers.IO) {
                        async { videoAPIServicios.obtenerFavoritosDeUsuario(UsuarioSingleton.Id) }.await()!!
                    } as MutableList<Video>

                    videosMasTarde = withContext(Dispatchers.IO) {
                        async { videoAPIServicios.obtenerMasTardePorUsuario(UsuarioSingleton.Id) }.await()!!
                    } as MutableList<Video>

                    videosFavoritos.forEach { video ->
                        video.isFavorito = true
                    }

                    videosFavoritos.forEach{video ->
                        video.isMasTarde = videosMasTarde.any { it.id == video.id }
                    }

                    if (recyclerView != null && isAdded && videosFavoritos.isNotEmpty()) {
                        if (videosFavoritos[0] != null) {
                            adaptadorCurso = AdaptadorCurso(requireActivity(), videosFavoritos)
                            recyclerView.adapter = adaptadorCurso

                        } else {
                            Toast.makeText(context, "Error al obtener los videos", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Aún no tienes videos favoritos", Toast.LENGTH_SHORT).show()
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