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
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.services.VideoAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InicioFragment : Fragment() {
    private lateinit var videos: MutableList<Video>
    private lateinit var recyclerView : RecyclerView
    val videoAPIServicios = VideoAPIServicios()
    private lateinit var adaptadorCurso : AdaptadorCurso


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                             savedInstanceState: Bundle?): View?
    {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        recyclerView = view.findViewById(R.id.postrecyclerview)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        videos = ArrayList()
        generateDummyCourses()

        return view
    }

    private fun generateDummyCourses() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Verificar si el fragmento está adjunto a una actividad
                if (isAdded) {
                    videos = withContext(Dispatchers.IO) {
                        async { videoAPIServicios.obtenerListaVideos() }.await()!!
                    } as MutableList<Video>

                    if (recyclerView != null && isAdded && videos.isNotEmpty()) {
                        if (videos[0] != null) {
                            adaptadorCurso = AdaptadorCurso(requireActivity(), videos)
                            recyclerView.adapter = adaptadorCurso

                        } else {
                            Toast.makeText(context, "Error al obtener los videos", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "No existen cursos registrados", Toast.LENGTH_SHORT).show()
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