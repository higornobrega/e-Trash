package com.example.e_trash.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.e_trash.R
import com.example.e_trash.helper.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = ConfiguracaoFirebase.getReferenciaAutenticacao()

        bt_logout.setOnClickListener {
            deslogarUsuario()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun deslogarUsuario() {
        try {
            auth.signOut()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
