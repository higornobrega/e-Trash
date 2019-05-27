package com.example.e_trash.model;

import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }
    public void salvar(){
        DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuariosref = firebaseref.child("usuario").child( getId());
        usuariosref.setValue(this);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
