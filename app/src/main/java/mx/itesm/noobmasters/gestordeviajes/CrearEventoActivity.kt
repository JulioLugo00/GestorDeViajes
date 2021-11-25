package mx.itesm.noobmasters.gestordeviajes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mx.itesm.noobmasters.gestordeviajes.ui.crearevento.CrearEventoFragment
import mx.itesm.noobmasters.gestordeviajes.ui.usuario.UsuarioFragment




class CrearEventoActivity : AppCompatActivity() {
    var cadena = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_evento_activity)
        cadena = intent.getStringExtra("ubicacion_key")!!



        val bundle = Bundle()
        bundle.putString("message", cadena)
        val newFragment = CrearEventoFragment()
        newFragment.arguments = bundle;

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .commitNow()
        }


        //Esconde el titulo de la app
        supportActionBar?.hide()
    }
}