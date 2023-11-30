package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.Video

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InicioFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                             savedInstanceState: Bundle?): View?
    {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Configurar el RecyclerView y el Adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.postrecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val courses = generateDummyCourses()
        val adapter = AdaptadorCurso(requireContext(), courses)
        recyclerView.adapter = adapter

        return view
    }

    private fun generateDummyCourses(): List<Video> {
        // Lógica para generar una lista de cursos de ejemplo
        // Reemplaza con la lógica real para obtener datos desde tu API
        return TODO("Provide the return value")
    }
}