package com.example.e_trash.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.e_trash.R;
import com.example.e_trash.helper.UsuarioFirebase;
import com.example.e_trash.model.Usuario;
import com.google.firebase.auth.FirebaseUser;

public class EditarPerfilActivity extends AppCompatActivity {
    private ImageView avatar;
    private TextView textAlterarFoto;
    private EditText editNomePerfil;
    private Button buttonSalvarAlteracoes;
    private Usuario usuarioLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //Botão voltar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        //Recuperar dados do Usuário
        inicializarComponentes();
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        editNomePerfil.setText( usuarioPerfil.getDisplayName());

        buttonSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAtualizado = editNomePerfil.getText().toString();
                UsuarioFirebase.atualizarNomeUsuario(nomeAtualizado);
                usuarioLogado.setNome(nomeAtualizado);
                usuarioLogado.atualizar();
            }
        });
        
    }
    public void inicializarComponentes(){
        avatar = findViewById(R.id.avatar);
        textAlterarFoto = findViewById(R.id.textAlterarFoto);
        editNomePerfil = findViewById(R.id.editNomePerfil);
        buttonSalvarAlteracoes = findViewById(R.id.buttonSalvarAlteracoes);
    }

}
