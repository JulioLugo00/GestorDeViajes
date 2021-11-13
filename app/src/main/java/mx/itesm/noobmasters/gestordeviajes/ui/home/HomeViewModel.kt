package mx.itesm.noobmasters.gestordeviajes.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mx.itesm.noobmasters.gestordeviajes.model.Evento
import mx.itesm.noobmasters.gestordeviajes.model.EventoTipo
import kotlin.reflect.typeOf

class HomeViewModel : ViewModel() {

    // Modelo
    //arrayListOf
    //private val servicioPaises = ServicioPaises()
    private val mAuth = FirebaseAuth.getInstance()



    // Variables observables (LiveData)
    val arregloEventos = MutableLiveData<List<Evento>>()

    val filtro = MutableLiveData<Int>(0)

    private lateinit var baseDatos: FirebaseDatabase

    fun aplicarFiltroViaje(){
        filtro.value=1
    }
    fun aplicarFiltroSalida(){
        filtro.value=2
    }
    fun aplicarFiltroCita(){
        filtro.value=3
    }
    fun quitarFiltro(){
        filtro.value=0
    }

    // Eventos
    fun descargarDatosEventos() {
        baseDatos = FirebaseDatabase.getInstance()
        //var prueba = emptyMap<String,Evento>()
        var arrayTodosLosEventosEnBD = mutableMapOf<String,Evento>()
        //val prueba:ArrayList<Evento> = ArrayList()
        var arrayTodosMisEventos = ArrayList<String>()
        //var arrayTodosLosEventosEnBD = ArrayList<String>()

        baseDatos.getReference("eventos").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaTemporal:ArrayList<Evento> = ArrayList()
                arrayTodosMisEventos.clear()
                arrayTodosLosEventosEnBD.clear()

                for (elemento in snapshot.children) {
                    val evento=elemento.getValue(Evento::class.java)
                    arrayTodosLosEventosEnBD.put(elemento.key.toString(),Evento(evento?.nombre,
                        evento?.tipo,
                        evento?.presupuesto,
                        evento?.ubicacion,
                        evento?.imagen,
                        evento?.fechaInicio,
                        evento?.fechaFin,
                        evento?.idUnico,
                        evento?.cosasPorLlevar
                        ))
                    //arrayTodosMisEventos.add(elemento.key.toString())
                }
                baseDatos.getReference("usuarios/${mAuth.currentUser?.uid}").child("eventos").get()
                    .addOnSuccessListener { result ->
                        for (elemento in result.children) {
                            arrayTodosMisEventos.add(elemento.key.toString())
                            //prueba.put(elemento.key.toString(), )
                           // var key =elemento.key.toString()
                            //arrayTodosLosEventosEnBD.add(key)
                        }

                        //Filtrado
                        arrayTodosLosEventosEnBD.map {
                            if(arrayTodosMisEventos.contains(it.key)){
                                listaTemporal.add(it.value)
                            }
                        }
                        //println(listaTemporal)
                        arregloEventos.value=listaTemporal
                    }
                    .addOnFailureListener { exception ->

                    }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





    }


}