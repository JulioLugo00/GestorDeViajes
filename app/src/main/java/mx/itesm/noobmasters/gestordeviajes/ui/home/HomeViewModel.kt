package mx.itesm.noobmasters.gestordeviajes.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.noobmasters.gestordeviajes.model.Evento

class HomeViewModel : ViewModel() {

    // Modelo
    //arrayListOf
    //private val servicioPaises = ServicioPaises()
    private val mAuth = FirebaseAuth.getInstance()



    // Variables observables (LiveData)
    val arregloEventos = MutableLiveData<List<Evento>>()

    private lateinit var baseDatos: FirebaseDatabase


    // Eventos
    fun descargarDatosEventos() {
        baseDatos = FirebaseDatabase.getInstance()

        val referencia=baseDatos.getReference("${mAuth.currentUser?.uid}")

        referencia.addValueEventListener(object :ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
            println("Data a cambiado")
                val listaTemporal:ArrayList<Evento> = ArrayList()

                if(snapshot.hasChildren()){
                    for (registro in snapshot.children){
                        //Guarda el valor en una clase ya existente
                        val evento=registro.getValue(Evento::class.java)
/**/
                        listaTemporal.add(Evento(evento?.nombre,
                            evento?.tipo,
                            evento?.presupuesto,
                            evento?.ubicacion,
                            evento?.imagen,
                            evento?.fechaInicio,
                            evento?.fechaFin))

                    }
                    arregloEventos.value=listaTemporal
                }
                else{
                    listaTemporal.clear()
                    arregloEventos.value=listaTemporal
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }


}