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
import com.desarrollo.studytechmobile.data.DTOS.ReporteDTO
import com.desarrollo.studytechmobile.data.DTOS.TipoReporteDTO
import com.desarrollo.studytechmobile.data.ReporteVisual
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.services.ReporteAPIServicios
import com.desarrollo.studytechmobile.services.TipoReporteAPIServicios
import com.desarrollo.studytechmobile.services.VideoAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentReporte: Fragment() {
    private lateinit var reportes: MutableList<ReporteDTO>
    private lateinit var tipoReporte: MutableList<TipoReporteDTO>
    private lateinit var videos: MutableList<Video>
    private var reporteVisibleList: MutableList<ReporteVisual> = mutableListOf()
    val reportesAPIServicios = ReporteAPIServicios()
    val tipoReportes = TipoReporteAPIServicios()
    val videosApi = VideoAPIServicios()
    private lateinit var recyclerView : RecyclerView
    private lateinit var adaptadorReporte: AdaptadorReporte

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_reporte, container, false)
        recyclerView = view.findViewById(R.id.reporteRecyclerView)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager


        reportes = ArrayList()
        buscarReportes()
        return view
    }

    private fun buscarReportes(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (isAdded) {
                    reportes = withContext(Dispatchers.IO){
                        async { reportesAPIServicios.obtenerReportes() }.await()!!
                    } as MutableList<ReporteDTO>

                    tipoReporte = withContext(Dispatchers.IO){
                        async { tipoReportes.obtenerTiposReporte() }.await()!!
                    } as MutableList<TipoReporteDTO>

                    videos = withContext(Dispatchers.IO){
                        async { videosApi.obtenerListaVideos() }.await()!!
                    } as MutableList<Video>


                    for (reporte in reportes) {
                        val reporteVisible = ReporteVisual()

                        // Buscar el tipoReporte correspondiente
                        val tipoReporte = tipoReporte.find { it.id == reporte.tipoReporte }
                        if (tipoReporte != null) {
                            reporteVisible.tipoReporteNombre = tipoReporte.tipo
                        }

                        // Buscar el video correspondiente
                        val video = videos.find { it.id == reporte.videoReporte }
                        if (video != null) {
                            reporteVisible.videoNombre = video.nombre
                        }

                        reporteVisibleList.add(reporteVisible)
                    }
                    var visual = reporteVisibleList[0].videoNombre
                    println("la lista es: $visual")

                    if (recyclerView != null && isAdded && reporteVisibleList.isNotEmpty()){
                        if(reporteVisibleList[0] != null) {
                            adaptadorReporte = AdaptadorReporte(requireActivity(), reporteVisibleList)
                            recyclerView.adapter = adaptadorReporte
                        } else {
                            Toast.makeText(context, "Error al obtener los reportes", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context, "Aún no ay reportes", Toast.LENGTH_SHORT).show()
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