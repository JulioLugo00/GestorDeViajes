package mx.itesm.noobmasters.gestordeviajes.ui.eventoInfo

import android.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

import mx.itesm.noobmasters.gestordeviajes.MainActivity
import mx.itesm.noobmasters.gestordeviajes.model.Evento


class EventoInfoVM : ViewModel() {
    // TODO: Implement the ViewModel

    val eventoEliminado = MutableLiveData<Boolean>()

    private lateinit var baseDatos: FirebaseDatabase
    private val mAuth = FirebaseAuth.getInstance()


    fun agregarObjetoABase(objeto:String, eventoId:String?){
        val usuario = mAuth.currentUser
        val referencia = baseDatos.getReference("eventos/$eventoId/cosasPorLlevar/$objeto")
        referencia.setValue("${usuario?.displayName}")

    }

    //Cierra la ventana si alguein elimino el evento
    fun notificarEventoEliminado(idEvento:String) {
        baseDatos = FirebaseDatabase.getInstance()

        baseDatos.getReference("eventos/${idEvento}").addValueEventListener(object :
            ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChildren()){
                    println("Nodo eliminado")
                    eventoEliminado.value=true
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun eliminarEvento(idEvento:String) {
        baseDatos = FirebaseDatabase.getInstance()

        baseDatos.getReference("eventos/${idEvento}").removeValue()

    }









}