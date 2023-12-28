package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import java.util.Locale

class BuscarFragment: Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorCurso : AdaptadorCurso
    private lateinit var videos: MutableList<Video>
    val videoAPIServicios = VideoAPIServicios()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_buscar, container, false)
        recyclerView = view.findViewById(R.id.buscarRecyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        videos = ArrayList()
        return view
    }

    private fun buscarVideos(query: String){
        CoroutineScope(Dispatchers.Main).launch {
            videos = withContext(Dispatchers.IO){
                async{ videoAPIServicios.obtenerListaVideos()}.await()!!
            } as MutableList<Video>

            videos = videos.filter { video ->
                video.nombre.toLowerCase(Locale.getDefault()).contains(query.toLowerCase())
            }.toMutableList()

            if(videos.isNotEmpty()){
                adaptadorCurso = AdaptadorCurso(requireActivity(), videos)
                recyclerView.adapter = adaptadorCurso
            }else {
                adaptadorCurso = AdaptadorCurso(requireActivity(), videos)
                recyclerView.adapter = adaptadorCurso
                Toast.makeText(context, "Error al obtener los videos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_principal, menu)
        menu.findItem(R.id.salir).isVisible = false
        val item = menu.findItem(R.id.buscarContenido)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.trim().isEmpty()) {
                    buscarVideos(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!newText.trim().isEmpty()) {
                    buscarVideos(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}