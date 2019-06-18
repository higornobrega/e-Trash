package com.example.e_trash.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.e_trash.R;
import com.example.e_trash.model.Lixo;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class LixoAdapter extends RecyclerView.Adapter<LixoAdapter.MyViewHolder> {
    private List<Lixo> listaLixo;
    private Context context;

    public LixoAdapter(List<Lixo> listaLixo, Context context) {
        this.listaLixo = listaLixo;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_lixo, viewGroup, false);
        return new LixoAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Lixo lixo = listaLixo.get(i);
        // Carregando os dados do feed
        Uri uriFotoUsuario = Uri.parse(lixo.getCaminhoFoto());
        Uri uriFotoLixo = Uri.parse(lixo.getCaminhoFoto());

        Glide.with(context).load(uriFotoUsuario).into(myViewHolder.avatar);
        Glide.with(context).load(uriFotoLixo).into(myViewHolder.imagemLixo);
        myViewHolder.nomeLixo.setText("Nome do lixo: " + lixo.getNome());
        myViewHolder.informacoes.setText("Informações: " + lixo.getInformacoes());
        myViewHolder.endereco.setText("Endereço: " + lixo.getEndereco());

    }

    @Override
    public int getItemCount() {
        return listaLixo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar;
        private ImageView imagemLixo;
        private ImageView botaoComentario;
        private TextView nomeLixo;
        private TextView nomeUsuario;
        private TextView informacoes;
        private TextView endereco;

        public MyViewHolder(View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.imagemPerfilPostagemLixo);
            imagemLixo = itemView.findViewById(R.id.iv_fotoLixoFeed);
            botaoComentario = itemView.findViewById(R.id.iv_botaoComentatio);
            nomeLixo = itemView.findViewById(R.id.tv_nomeLixoFeed);
            nomeUsuario = itemView.findViewById(R.id.tv_nomeUsuarioFeed);
            informacoes = itemView.findViewById(R.id.tv_informacoesLixoFeed);
            endereco = itemView.findViewById(R.id.tv_enderecoFeed);
        }
    }
}