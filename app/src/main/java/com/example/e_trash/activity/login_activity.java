package com.example.e_trash.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.e_trash.R;
import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.example.e_trash.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_activity extends AppCompatActivity {
    private EditText campoEmail, campoSenha;
    private Button botaoLogin;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        verificarUsuarioLogado();
        inicializarComponentes();

        // Fazer login do usuario
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {
                        usuario = new Usuario();
                        usuario.setNome(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin(usuario);
                    }
                    else {
                        Toast.makeText(login_activity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login_activity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void inicializarComponentes() {
        campoEmail = findViewById(R.id.et_cadastro_nome);
        campoSenha = findViewById(R.id.et_cadastro_senha);
        botaoLogin = findViewById(R.id.bt_cadastrar);

        campoEmail.requestFocus();
    }

    public void abrirCadastro(View view) {
        Intent intent = new Intent(login_activity.this, cadastro_activity.class);
        startActivity(intent);
    }

    public void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void validarLogin(Usuario usuario) {
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(login_activity.this, "Erro ao acessar a conta!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
