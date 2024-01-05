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
import com.desarrollo.studytechmobile.data.DTOS.MateriaDTO
import com.desarrollo.studytechmobile.services.MateriaAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CursoFragment : Fragment() {
    private lateinit var materias: MutableList<MateriaDTO>
    val materiaAPIServicios = MateriaAPIServicios()
    private lateinit var recyclerView : RecyclerView
    private lateinit var adaptadorMateria: AdaptadorMateria

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_materia, container, false)
        recyclerView = view.findViewById(R.id.materiaRecyclerView)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        materias = ArrayList()
        buscarReportes()
        return view
    }

    private fun buscarReportes(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (isAdded) {
                    materias = withContext(Dispatchers.IO){
                        async { materiaAPIServicios.obtenerMaterias() }.await()!!
                    } as MutableList<MateriaDTO>
                    var muestra = materias[0].nombre
                    println("la muestra es: $muestra")
                    if (recyclerView != null && isAdded && materias.isNotEmpty()){
                        if(materias[0] != null) {
                            adaptadorMateria = AdaptadorMateria(requireActivity(), materias)
                            recyclerView.adapter = adaptadorMateria
                        }else {
                            Toast.makeText(context, "Error al obtener las materias", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(context, "Aún no hay materias", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: Exception){
                println("la excepción en el reporte es: $e")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}