package com.example.e_trash.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_trash.R;
import com.example.e_trash.adapter.ComentarioAdapter;
import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.example.e_trash.helper.UsuarioFirebase;
import com.example.e_trash.model.Comentario;
import com.example.e_trash.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComentatiosActivity extends AppCompatActivity {
    private EditText editComentario;
    private RecyclerView recyclerComentarios;
    private String idPostagem;
    private Usuario usuario;
    private ComentarioAdapter comentarioAdapter;
    private List<Comentario> listaComentario = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private DatabaseReference comentariosRef;
    private ValueEventListener valueEventListenerComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentatios);

        editComentario = findViewById(R.id.editComentatio);
        recyclerComentarios = findViewById(R.id.recyclerComentarios);

        usuario = UsuarioFirebase.getDadosUsuarioLogado();
        firebaseRef = ConfiguracaoFirebase.getFirebase();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        comentarioAdapter = new ComentarioAdapter(listaComentario, getApplicationContext());
        recyclerComentarios.setHasFixedSize(true);
        recyclerComentarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerComentarios.setAdapter(comentarioAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idPostagem = bundle.getString("idPostagem");
        }
    }

    private void recuperarComentario() {
        comentariosRef = firebaseRef.child("comentarios").child(idPostagem);
        valueEventListenerComentarios = comentariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaComentario.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    listaComentario.add(ds.getValue(Comentario.class));
                }
                comentarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarComentario();
    }

    @Override
    protected void onStop() {
        super.onStop();
        comentariosRef.removeEventListener(valueEventListenerComentarios);
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
            else {
                Toast.makeText(this, "Não foi possível publicar o comentário!", Toast.LENGTH_SHORT).show();
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
