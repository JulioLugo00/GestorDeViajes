package mx.itesm.noobmasters.gestordeviajes.ui.eventoInfo

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import mx.itesm.noobmasters.gestordeviajes.LogInActivity
import mx.itesm.noobmasters.gestordeviajes.MainActivity2
import mx.itesm.noobmasters.gestordeviajes.R
import mx.itesm.noobmasters.gestordeviajes.databinding.EventoInfoFragmentBinding
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText

import mx.itesm.noobmasters.gestordeviajes.MainActivity




class EventoInfoFrag : Fragment() {

    companion object {
        fun newInstance() = EventoInfoFrag()
    }

    private lateinit var viewModel: EventoInfoVM

    private val args:EventoInfoFragArgs by navArgs()

    private lateinit var binding:EventoInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.evento_info_fragment, container, false)
        binding= EventoInfoFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventoInfoVM::class.java)
        // TODO: Use the ViewModel
        registrarEventos()
        registrarObservadores()
    }

    private fun registrarObservadores() {
        //Si alguien elimino el evento, te saca
        viewModel.eventoEliminado.observe(viewLifecycleOwner){booleano ->
            if(booleano){
                Toast.makeText(context,"Alguien elimino el evento",Toast.LENGTH_SHORT).show()
                val intMenuPrincipal = Intent(context, MainActivity2::class.java)
                startActivity(intMenuPrincipal)
            }
        }
    }

    private fun registrarEventos() {

        //Saber si alguien elimino el evento
        viewModel.notificarEventoEliminado("${args.eventoInfo.idUnico}")


        //Registrar datos evento
        binding.tvNombreEvento.setText("${args.eventoInfo.nombre}")
        binding.inputCodigoEvento.setText("${args.eventoInfo.idUnico}")

        //Copiar codigo
        binding.btnCopiarCodigo.setOnClickListener {
            val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip=ClipData.newPlainText("inputCodigoEvento",binding.inputCodigoEvento.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Código copiado", Toast.LENGTH_LONG).show()
        }

        //Eliminar evento
        binding.btnEliminarEvento.setOnClickListener {

            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Alerta")
            alertDialog.setMessage("Vas a eliminar un evento, ¿Estas seguro?")

            alertDialog.setPositiveButton(android.R.string.ok) { dialog, which ->
                viewModel.eliminarEvento("${args.eventoInfo.idUnico}")
            }

            alertDialog.setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            alertDialog.show()
        }

        //Agregar objeto
        binding.btnAgregarObjeto.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            val input:EditText = EditText(context)
            input.inputType=InputType.TYPE_CLASS_TEXT
            alertDialog.setTitle("Objeto")
            alertDialog.setMessage("¿Qué vas a traer?")
            alertDialog.setView(input)

            alertDialog.setPositiveButton(android.R.string.ok) { dialog, which ->
               if(input.text.isNotEmpty()){
                   println(input.text.toString())
                   viewModel.agregarObjetoABase(input.text.toString())
               }else{
                   Toast.makeText(context, "No hay texto", Toast.LENGTH_LONG).show()
               }

            }

            alertDialog.setNegativeButton(android.R.string.cancel) { dialog, which ->
            }
            alertDialog.show()
        }

    }

}