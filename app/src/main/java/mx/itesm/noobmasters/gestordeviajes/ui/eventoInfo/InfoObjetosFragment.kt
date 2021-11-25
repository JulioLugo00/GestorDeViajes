package mx.itesm.noobmasters.gestordeviajes.ui.eventoInfo

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.noobmasters.gestordeviajes.databinding.EventoInfoFragmentBinding

class InfoObjetosFragment:ListFragment() {

    private lateinit var binding: EventoInfoFragmentBinding


    private var arrCosasPorLlevar:MutableList<String> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= EventoInfoFragmentBinding.inflate(layoutInflater)
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        println("Mensaje desde fragment objeto")
    }


/*
*    private fun leerDatos(){
        val baseDatos= FirebaseDatabase.getInstance()
        val referencia=baseDatos.getReference("Alumnos")

        //Se llama una unoca vez para la lectura de los datos.
        //referencia.addListenerForSingleValueEvent(object :ValueEventListener{
        referencia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //snapshot: Conunto de datos que acaba de leer de DB

                //Regresa un conjunto de datos que leyo
                arrAlumnos.clear()
                //Solo toma un elemento del conjunto. Cada matricula
                for (registro in snapshot.children){
                    //Guarda el valor en una clase ya existente
                    val alumno=registro.getValue(Alumno::class.java)
                    arrAlumnos.add("${alumno?.matricula}, ${alumno?.nombre} , ${alumno?.semestre}")

                }

                //Adaptador de tipo arrayAdapter
                val adaptador= ArrayAdapter(requireContext(), R.layout.simple_list_item_1,arrAlumnos)

                //listAdapter es el adaptador de listFragment que ya cuenta con su
                //Recycler VIEW
                listAdapter = adaptador
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
* */



}