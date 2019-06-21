package com.example.e_trash.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.e_trash.R;
import com.example.e_trash.model.Comentario;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.MyViewHolder> {
    private List<Comentario> listaComentario;
    private Context context;

    public ComentarioAdapter(List<Comentario> listaComentario, Context context) {
        this.listaComentario = listaComentario;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_comentarios, viewGroup, false);
        return new ComentarioAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Comentario comentario = listaComentario.get(i);

        Glide.with(context).load(comentario.getCaminhoFoto()).into(myViewHolder.imagemPerfil);
        myViewHolder.nomeUsuario.setText(comentario.getNomeUsuario());
        myViewHolder.comentario.setText(comentario.getComentario());
    }

    @Override
    public int getItemCount() {
        return listaComentario.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imagemPerfil;
        TextView nomeUsuario;
        TextView comentario;

        public MyViewHolder(View itemView) {
            super(itemView);

            imagemPerfil = itemView.findViewById(R.id.imagemFotoComentario);
            nomeUsuario = itemView.findViewById(R.id.textNomeComentario);
            comentario = itemView.findViewById(R.id.textComentario);
        }
    }
}
