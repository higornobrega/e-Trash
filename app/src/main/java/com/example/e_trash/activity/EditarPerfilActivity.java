package com.example.e_trash.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
import com.example.e_trash.R;
import com.example.e_trash.helper.UsuarioFirebase;
import com.google.firebase.auth.FirebaseUser;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageView avatar;
    private TextView textAlterarFoto;
    private TextInputEditText editNomePerfil;
    private Button buttonSalvarAlteracoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);



        //Botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        //Recuperar dados do Usuário
        inicializarComponentes();
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        editNomePerfil.setText( usuarioPerfil.getDisplayName());
        
    }
    public void inicializarComponentes(){
        avatar = findViewById(R.id.avatar);
        textAlterarFoto = findViewById(R.id.textAlterarFoto);
        editNomePerfil = findViewById(R.id.editNomePerfil);
        buttonSalvarAlteracoes = findViewById(R.id.buttonSalvarAlteracoes);
    }

}
