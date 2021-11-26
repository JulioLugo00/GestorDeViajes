package mx.itesm.noobmasters.gestordeviajes.ui.eventoInfo

import android.R
import android.content.Context
import android.media.metrics.Event
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.itesm.noobmasters.gestordeviajes.databinding.EventoInfoFragmentBinding
import mx.itesm.noobmasters.gestordeviajes.ui.crearevento.CrearEventoViewModel

class InfoObjetosFragment:ListFragment() {

    private var arrCosasPorLlevar:MutableList<String> = mutableListOf()

    var eventKey=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onResume() {
        super.onResume()
        val preferencias = activity?.getSharedPreferences("eventKey",Context.MODE_PRIVATE)
        eventKey=preferencias?.getString("idEvent","No existe")!!
        println("Interior ${eventKey}")
        leerDatos()
    }

    private fun leerDatos(){
        val baseDatos = Firebase.database
        println("INTERIOR $eventKey")
        val referencia = baseDatos.getReference("eventos/$eventKey/cosasPorLlevar")

        referencia.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrCosasPorLlevar.clear()
                for (registro in snapshot.children){
                    arrCosasPorLlevar.add("${registro.key}")
                }
                //Adaptador
                val adaptador = ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, arrCosasPorLlevar)
                listAdapter = adaptador
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}