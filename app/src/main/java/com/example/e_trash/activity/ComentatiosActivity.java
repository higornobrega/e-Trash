package com.example.e_trash.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_trash.R;
import com.example.e_trash.helper.UsuarioFirebase;
import com.example.e_trash.model.Comentario;
import com.example.e_trash.model.Usuario;

public class ComentatiosActivity extends AppCompatActivity {
    private EditText editComentario;
    private String idPostagem;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentatios);

        editComentario = findViewById(R.id.editComentatio);

        usuario = UsuarioFirebase.getDadosUsuarioLogado();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idPostagem = bundle.getString("idPostagem");
        }
    }

    public void salvarComentario(View view) {
        String textoComentario = editComentario.getText().toString();
        if (textoComentario != null && !textoComentario.equals("")) {
            Comentario comentario =new Comentario();
            comentario.setId(idPostagem);
            comentario.setIdUsuario(usuario.getId());
            comentario.setNomeUsuario(usuario.getNome());
            comentario.setCaminhoFoto(usuario.getCaminhoFoto());
            comentario.setComentario(textoComentario);
            if (comentario.salvar()) {
                Toast.makeText(this, "Comentário salvo com sucesso!", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this, "Insira o comentário antes de salvar!", Toast.LENGTH_SHORT).show();
        }

        editComentario.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
