package com.example.e_trash.helper;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.e_trash.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getReferenciaAutenticacao();
        return usuario.getCurrentUser();
    }

    public static void atualizarNomeUsuario( String nome){

        try {
            FirebaseUser usuarioLogado = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName( nome )
                    .build();
            usuarioLogado.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if( !task.isSuccessful() ){
                        Log.d("Perfil","Erro ao atualizar nome de perfil.");
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Usuario getDadosUsuarioLogado(){

        FirebaseUser firebaseUser = getUsuarioAtual();

        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getDisplayName());
        usuario.setId(firebaseUser.getUid());

        if( firebaseUser.getPhotoUrl() == null){
            usuario.setCaminhoFoto("");
        }else {
            usuario.setCaminhoFoto ( firebaseUser.getPhotoUrl().toString());
        }

        return usuario;
    }
}
