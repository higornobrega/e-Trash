package com.example.e_trash.model

class Usuario {
	var id: String? = null
	var nome: String? = null
	var email: String? = null
	var senha: String? = null

	constructor() {
		// Default constructor required for calls to DataSnapshot.getValue(Post.class)
	}

	constructor(id: String, nome: String, email: String, senha: String) {
		this.id = id
		this.nome = nome
		this.email = email
		this.senha = senha
	}
}