package com.example.e_trash.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_trash.R
import com.example.e_trash.helper.ConfiguracaoFirebase
import com.example.e_trash.model.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class activity_login : AppCompatActivity() {

	private lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		// Fazer login do usuario
		bt_login.setOnClickListener {

			val textoEmail = et_login_email.text.toString()
			val textoSenha = et_login_email.text.toString()

			if (!textoEmail.isEmpty()) {
				if (!textoSenha.isEmpty()) {
					val usuario = Usuario()
					usuario.email = textoEmail
					usuario.senha = textoSenha
					validarLogin(usuario)
				}
				else {
					Toast.makeText(applicationContext, "Preencha a senha!", Toast.LENGTH_SHORT).show()
				}
			}
			else {
				Toast.makeText(applicationContext, "Preencha o E-mail!", Toast.LENGTH_SHORT).show()
			}
		}
	}

	fun validarLogin(usuario: Usuario) {
		// Initialize Firebase Auth
	}
}