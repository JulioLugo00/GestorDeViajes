package mx.itesm.noobmasters.gestordeviajes.ui.crearevento

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.noobmasters.gestordeviajes.CrearEventoActivity
import mx.itesm.noobmasters.gestordeviajes.MainActivity2
import mx.itesm.noobmasters.gestordeviajes.R
import mx.itesm.noobmasters.gestordeviajes.databinding.CreareventoFragmentBinding
import mx.itesm.noobmasters.gestordeviajes.model.EventoTipo
import java.text.SimpleDateFormat
//import android.R



//import android.R





class CrearEventoFragment : Fragment() {
    lateinit var opciones: Spinner
    private lateinit var crearEventoViewModel: CrearEventoViewModel
    private lateinit var binding:CreareventoFragmentBinding
    private lateinit var baseDatos: FirebaseDatabase
    private val mAuth = FirebaseAuth.getInstance()


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



        val v: View = inflater.inflate(R.layout.crearevento_fragment, container, false)
        val texto = requireArguments().getString("message")
        if (texto != null) {
            binding.tilUbicacion.editText?.setText(texto)
        }

        return binding.root
    }



    private fun registrarEventos() {
        opciones = binding.spinnerTipo
        var adapter :ArrayAdapter<CharSequence>  = ArrayAdapter.createFromResource(requireContext(), R.array.opciones, android.R.layout.simple_spinner_item)
        opciones.adapter = adapter


        binding.btnFechaInicio.setOnClickListener{
            showDatePickedDialog()

        }

        binding.btnFechaFinal.setOnClickListener{
            showDatePickedDialogFinal()
        }

        binding.btnCrearEvento.setOnClickListener{
            var usuario = mAuth.currentUser

            var presupuesto = binding.etnPresupuesto.text.toString()
            var ubicacion = binding.tilUbicacion.editText?.text.toString()
            var nombre  = binding.tilNombreEvento.editText?.text.toString()
            var fechaInicio = binding.btnFechaInicio.text.toString()
            var fechaFin = binding.btnFechaFinal.text.toString()
            var tipoStr = binding.spinnerTipo.selectedItem.toString()
            var imagen = ""
            var cosasPorLlevar= mapOf("Asistencia" to "No")

            if(presupuesto == "" || ubicacion == ""  || nombre == ""
                || fechaInicio == "##-##-####" || fechaFin == "##-##-####"){
                Toast.makeText(requireContext(), "Es necesario introducir todos los datos", Toast.LENGTH_SHORT).show()
            }
            else{

                var dateFormat = SimpleDateFormat("yyyy-MM-dd")
                var dateInicio = dateFormat.parse(fechaInicio)
                var dateFinal = dateFormat.parse(fechaFin)

                if (dateInicio.after(dateFinal)){
                    Toast.makeText(requireContext(), "Las fechas de inicio debe ser antes de la final", Toast.LENGTH_SHORT).show()
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
                    val id=generarIdRandom(10)

                    var evento = crearEventoViewModel.crearEvento(presupuestoInt, nombre, fechaInicio,fechaFin, tipo,
                        ubicacion, imagen,id,cosasPorLlevar)


                    val referencia = baseDatos.getReference("usuarios/${usuario?.uid}/eventos/${id}")

                    val referencia02 = baseDatos.getReference("eventos/${id}/")
                    referencia02.setValue(evento)
                    referencia.setValue(true)

                    Toast.makeText(context,"Actividad creada exitosamente",Toast.LENGTH_SHORT).show()
                    val intCrearMainActivity2 = Intent(context, MainActivity2::class.java)
                    startActivity(intCrearMainActivity2)

                }
            }
        }

        binding.btnAceptarCodigoEvento.setOnClickListener {
            var codigo = binding.tillCodigoEvento.editText?.text.toString()
            if(codigo.isNotEmpty()){
                baseDatos.getReference("eventos/${codigo}").get()
                    .addOnSuccessListener { result ->
                        if(result.hasChildren()){
                            var usuario = mAuth.currentUser
                            val referencia = baseDatos.getReference("usuarios/${usuario?.uid}/eventos/${codigo}")
                            referencia.setValue(true)
                            Toast.makeText(context,"Codigo exitoso",Toast.LENGTH_SHORT).show()
                            val intCrearMainActivity2 = Intent(context, MainActivity2::class.java)
                            startActivity(intCrearMainActivity2)
                        }else{
                            Toast.makeText(context,"Codigo invalido",Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->

                    }

            }else{
                Toast.makeText(context,"No hay codigo",Toast.LENGTH_SHORT).show()
            }
        }


    }



    private fun showDatePickedDialogFinal() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelectedFinal(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelectedFinal(day: Int, month: Int, year: Int) {
        binding.btnFechaFinal.setText("$day-$month-$year")

    }

    private fun showDatePickedDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker")

    }

    fun onDateSelected(day:Int, month:Int, year:Int){
        binding.btnFechaInicio.setText("$day-$month-$year")

    }

    private fun registrarObservadores() {


    }
    fun generarIdRandom(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun configurarLista() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProvider(this).get(CrearEventoViewModel::class.java)

    }

}