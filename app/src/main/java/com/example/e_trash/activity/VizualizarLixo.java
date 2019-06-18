package com.example.e_trash.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.e_trash.R;
import com.example.e_trash.adapter.LixoAdapter;
import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.example.e_trash.helper.UsuarioFirebase;
import com.example.e_trash.model.Lixo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VizualizarLixo extends AppCompatActivity {
    private List<Lixo> listaLixo = new ArrayList<>();
    private RecyclerView recyclerLixo;
    private LixoAdapter adapterLixo;
    private ValueEventListener valueEventListenerLixo;
    private DatabaseReference lixoRef;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_lixo);

        recyclerLixo = findViewById(R.id.rv_feedLixo);

        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();
        lixoRef = ConfiguracaoFirebase.getFirebase().child("lixos").child(idUsuarioLogado);

        adapterLixo = new LixoAdapter(listaLixo, getApplicationContext());
        recyclerLixo.setHasFixedSize(true);
        recyclerLixo.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerLixo.setAdapter(adapterLixo);
    }

    private void listarLixo() {
        valueEventListenerLixo = lixoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    listaLixo.add(ds.getValue(Lixo.class));
                }
                Collections.reverse(listaLixo);
                adapterLixo.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listarLixo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        lixoRef.removeEventListener(valueEventListenerLixo);
    }
}
