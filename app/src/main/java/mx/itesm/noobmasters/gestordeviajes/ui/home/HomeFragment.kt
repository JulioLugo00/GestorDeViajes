package mx.itesm.noobmasters.gestordeviajes.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.itesm.noobmasters.gestordeviajes.CrearEventoActivity
import mx.itesm.noobmasters.gestordeviajes.databinding.FragmentHomeBinding
//import mx.itesm.noobmasters.gestordeviajes.ui.crearEvento.CrearEventoFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    //biding
    private lateinit var binding:FragmentHomeBinding

    //private var _binding: FragmentHomeBinding? = null

    //Adaptador
    private val adaptadorListaEventos = AdaptadorListaEventos(arrayListOf()) //Vacio

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        binding=FragmentHomeBinding.inflate(layoutInflater)

        configurarLista()
        registrarObservadores()
        registrarEventos()
        return binding.root
    }

    private fun registrarEventos() {
        homeViewModel.descargarDatosEventos()

        binding.btnAgregarActividad.setOnClickListener{
            val intCrearEvento = Intent(context, CrearEventoActivity::class.java)
            startActivity(intCrearEvento)
        }
    }

    private fun registrarObservadores() {
        homeViewModel.arregloEventos.observe(viewLifecycleOwner){lista->
            binding.pbCargando.visibility=View.VISIBLE
            adaptadorListaEventos.actualizarDatos(lista)

            binding.pbCargando.visibility=View.INVISIBLE
        }
    }

    private fun configurarLista() {
        binding.rvListaEventos.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = adaptadorListaEventos
        }
    }

/*
*     override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
*
* */


}