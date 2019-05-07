package com.example.e_trash.helper

import com.google.firebase.auth.FirebaseAuth

class ConfiguracaoFirebase {
	private lateinit var referenciaAutenticacao: FirebaseAuth

	fun getFirebaseAutenticacao(): FirebaseAuth {
		if (referenciaAutenticacao == null) {
			referenciaAutenticacao = FirebaseAuth.getInstance()
		}
		return referenciaAutenticacao
	}
}