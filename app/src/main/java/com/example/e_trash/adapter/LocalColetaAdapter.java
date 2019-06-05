package com.example.e_trash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.e_trash.R;
import com.example.e_trash.model.LocalColeta;

import java.util.ArrayList;

public class LocalColetaAdapter extends ArrayAdapter<LocalColeta> {
    private final Context context;
    private final ArrayList<LocalColeta> listaLocaisColeta;

    public LocalColetaAdapter(Context context, ArrayList<LocalColeta> listaLocaisColeta) {
        super(context, R.layout.local_coleta, listaLocaisColeta);
        this.context = context;
        this.listaLocaisColeta = listaLocaisColeta;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linha = inflater.inflate(R.layout.local_coleta, parent, false);

        TextView nome = linha.findViewById(R.id.tv_nomeLocal);
        TextView longitude = linha.findViewById(R.id.tv_longitudeLocalColeta);
        TextView latitude = linha.findViewById(R.id.tv_latitudeLocalColeta);

        nome.setText(listaLocaisColeta.get(position).getNome());
        longitude.setText(listaLocaisColeta.get(position).getLongitude().toString());
        latitude.setText(listaLocaisColeta.get(position).getLatitude().toString());

        return linha;
    }
}
