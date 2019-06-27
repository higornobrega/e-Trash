package com.example.e_trash.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.e_trash.R;
import com.example.e_trash.adapter.LocalColetaAdapter;
import com.example.e_trash.model.LocalColeta;

import java.sql.Array;
import java.util.ArrayList;

public class RemoverPontoDeColeta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_ponto_de_coleta);

//        LocalColetaAdapter adapter = new LocalColetaAdapter(this, todosOsLocais());
//
//        ListView listaDeLocais = (ListView) findViewById(R.id.lista);
//        listaDeLocais.setAdapter(adapter);
    }

//    private ArrayList<LocalColeta> todosOsLocais() {
//        ArrayList<LocalColeta> items = new ArrayList<>();
//        items.add(new LocalColeta("Loja 01", -14.0000, 23.444));
//        items.add(new LocalColeta("Loja 02", -16.0000, 27.444));
//
//        return items;
//    }
}
