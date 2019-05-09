package com.example.e_trash.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_trash.R;
import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.example.e_trash.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class CadastroActivity extends AppCompatActivity {
    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastro;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializarComponentes();

        // Cadastrar usuario
        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoNome.isEmpty()) {
                    if (!textoEmail.isEmpty()) {
                        if (!textoSenha.isEmpty()) {
                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrar(usuario);
                        }
                        else {
                            Toast.makeText(CadastroActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(CadastroActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CadastroActivity.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void inicializarComponentes() {
        campoNome = findViewById(R.id.et_cadastro_nome);
        campoEmail = findViewById(R.id.et_cadastro_email);
        campoSenha = findViewById(R.id.et_cadastro_senha);
        botaoCadastro = findViewById(R.id.bt_cadastro);

        campoNome.requestFocus();
    }

    // Metodo responsavel por cadastrar o usuario com email e senha e fazer validacoes ao fazer o cadastro
    public void cadastrar(Usuario usuario) {
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Cadastro criado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte!";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Digite um e-mail válido!";
                    }
                    catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Conta já foi criada em nosso sistema!";
                    }
                    catch (Exception e) {
                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
