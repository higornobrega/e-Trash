package com.example.e_trash.model;

import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Comentario {
    private String id;
    private String idLixo;
    private String idUsuario;
    private String caminhoFoto;
    private String nomeUsuario;
    private String comentario;

    public Comentario() {
    }

    public boolean salvar() {
        DatabaseReference comentarisRef = ConfiguracaoFirebase.getFirebase().child("comentarios").child(getId());
        String chaveComentario = comentarisRef.push().getKey();
        setId(chaveComentario);
        comentarisRef.child(getId()).setValue(this);

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdLixo() {
        return idLixo;
    }

    public void setIdLixo(String idLixo) {
        this.idLixo = idLixo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
