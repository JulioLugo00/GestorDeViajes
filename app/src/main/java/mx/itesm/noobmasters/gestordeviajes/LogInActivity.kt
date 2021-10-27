package mx.itesm.noobmasters.gestordeviajes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import mx.itesm.noobmasters.gestordeviajes.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    private val CODIGO_SIGNIN = 100
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        configurarEventos()
    }

    override fun onStart(){
        super.onStart()
        val usuario = mAuth.currentUser
        if (usuario != null) {
            println("INICIA: ${usuario?.displayName}")
            println("Correo: ${usuario?.email}")
            println("UID: ${usuario?.uid}")
            val intMain2 = Intent(this, MainActivity2::class.java)
            startActivity(intMain2)
        } else {
            println("Hacer SignIn")
        }
    }

    private fun autenticar() {
        val providers =
            arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            CODIGO_SIGNIN
        )

    }

    private fun configurarEventos() {
        binding.btnSignIn.setOnClickListener{
            autenticar()

            //AuthUI.getInstance().signOut(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_SIGNIN) {
            when (resultCode) {
                RESULT_OK -> {
                    val usuario = FirebaseAuth.getInstance().currentUser
                    println("Bienvenido: ${usuario?.displayName}")
                    println("Correo: ${usuario?.email}")
                    println("ID: ${usuario?.uid}")
                    val intMain2 = Intent(this, MainActivity2::class.java)
                    startActivity(intMain2)
                }
                RESULT_CANCELED -> {
                    println("Cancelado...")
                    val response = IdpResponse.fromResultIntent(data)
                    println("Error:${response?.error?.localizedMessage}")
                }
                else -> {
                    val response = IdpResponse.fromResultIntent(data)
                    println("Error:${response?.error?.errorCode}")
                }
            }
        }
    }

}