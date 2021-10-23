package mx.itesm.noobmasters.gestordeviajes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Esconde el titulo de la app
        supportActionBar?.hide()

        cargarPrincipal()
    }

    private fun cargarPrincipal() {
        Handler(Looper.getMainLooper()).postDelayed({

            cambiarPantalla()


        },1000)
    }

    private fun cambiarPantalla() {
        val intLogIn = Intent(this, LogInActivity::class.java)
        startActivity(intLogIn)
        //Destruye la pantalla, esta actividad (Slash Activity)
        this.finish()
    }
}