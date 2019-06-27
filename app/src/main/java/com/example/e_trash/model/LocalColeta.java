package com.example.e_trash.model;

import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class LocalColeta {
    private String id;
    private String nome;
    private Double latitude;
    private Double longitude;
    public LocalColeta() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference ponto_de_coleta_Ref = firebaseRef.child("PontosDeColeta");
        String idPontoDeColeta = ponto_de_coleta_Ref.push().getKey();
        setId( idPontoDeColeta);
    }
/*
    public LocalColeta(String nome, Double latitude, Double longitude) {
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }*//*
    public boolean salvar(){
        DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebase();
        DatabaseReference localcoletasref = firebaseref.child("locais_coletas").child( getId());
        localcoletasref.setValue(this);
        return true;
    }*/
    public boolean salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference idPonto_de_coleta_Ref = firebaseRef.child("PontosDeColeta")
                .child(getId());
        idPonto_de_coleta_Ref.setValue(this);
        return true;

    }
//   public void salvar(){
//       DatabaseReference firebaseref = ConfiguracaoFirebase.getFirebase();
//       DatabaseReference usuariosref = firebaseref.child("usuario").child( getId());
//       usuariosref.setValue(this);
//   }
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
