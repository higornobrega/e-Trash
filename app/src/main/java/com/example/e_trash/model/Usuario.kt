package com.example.e_trash.model

class Usuario {
	var id: String = ""
	var nome: String = ""
	var email: String = ""
	var senha: String = ""

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