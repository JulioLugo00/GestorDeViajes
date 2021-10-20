package mx.itesm.noobmasters.gestordeviajes.ui.crearevento

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.noobmasters.gestordeviajes.R
import mx.itesm.noobmasters.gestordeviajes.databinding.CreareventoFragmentBinding
import mx.itesm.noobmasters.gestordeviajes.databinding.FragmentHomeBinding
import mx.itesm.noobmasters.gestordeviajes.model.EventoTipo
import mx.itesm.noobmasters.gestordeviajes.ui.home.HomeViewModel

class CrearEventoFragment : Fragment() {
    lateinit var opciones: Spinner
    private lateinit var crearEventoViewModel: CrearEventoViewModel
    private lateinit var binding:CreareventoFragmentBinding
    private lateinit var baseDatos: FirebaseDatabase


    companion object {
        fun newInstance() = CrearEventoFragment()
    }



    private lateinit var viewModel: CrearEventoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        crearEventoViewModel = ViewModelProvider(this).get(CrearEventoViewModel::class.java)


        binding= CreareventoFragmentBinding.inflate(layoutInflater)

        configurarLista()
        registrarObservadores()
        registrarEventos()
        baseDatos = Firebase.database
        return binding.root
    }

    private fun registrarEventos() {
        opciones = binding.spinnerTipo
        var adapter :ArrayAdapter<CharSequence>  = ArrayAdapter.createFromResource(requireContext(), R.array.opciones, android.R.layout.simple_spinner_item)
        opciones.adapter = adapter

        binding.btnCrearEvento.setOnClickListener{

            var presupuesto = binding.etnPresupuesto.text.toString()
            var ubicacion = binding.tilUbicacion.editText?.text.toString()
            var nombre  = binding.tilNombreEvento.editText?.text.toString()
            var fechaInicio = binding.etdFechaInicio.text.toString()
            var fechaFin = binding.etdFechaFinal.text.toString()
            var tipoStr = binding.spinnerTipo.selectedItem.toString()
            var imagen = ""


            if(presupuesto == "" || ubicacion == "" || fechaInicio == "" || nombre == ""|| fechaFin==""  ){
                Toast.makeText(requireContext(), "Es necesario introducir todos los datos", Toast.LENGTH_SHORT).show()
            }
            
            else {
                var presupuestoInt = presupuesto.toInt()
                var tipo: EventoTipo
                if (tipoStr == "Viaje") {
                    tipo = EventoTipo.VIAJES
                } else if (tipoStr == "Cita") {
                    tipo = EventoTipo.CITAS
                } else {
                    tipo = EventoTipo.SALIDAS
                }

                var evento = crearEventoViewModel.crearEvento(presupuestoInt, nombre, fechaInicio,fechaFin, tipo,
                    ubicacion, imagen)
                val referencia = baseDatos.getReference("eventos/$nombre")
                referencia.setValue(evento)
            }
        }
    }

    private fun registrarObservadores() {


    }

    private fun configurarLista() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProvider(this).get(CrearEventoViewModel::class.java)

    }

}