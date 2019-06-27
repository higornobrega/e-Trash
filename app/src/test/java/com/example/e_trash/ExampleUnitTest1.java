package com.example.e_trash;

import android.app.Activity;
import android.widget.Button;
import com.example.e_trash.activity.CadastroActivity;
import com.example.e_trash.model.Comentario;
import com.example.e_trash.model.Lixo;
import com.example.e_trash.model.LocalColeta;
import com.example.e_trash.model.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

public class ExampleUnitTest1 {

    Usuario usuario = null;
    @Before
    public void setUp() {
        usuario = new Usuario();
        usuario.setNome("bruno");
        usuario.setSenha("12345");
        usuario.setEmail("brunodesouza@gmail.com");
        usuario.setCaminhoFoto("https://firebasestorage.googleapis.com/v0/b/e-trash-bb649.appspot.com/o/imagens%2Flixos%2F-Lhg_uEvZftKzSn3aaTv.jpeg?alt=media&token=abf9569c-b39f-4460-bc63-0819c1595b51");
        usuario.setId("-Lhg_uEvZftKzSn3aaTv");
    }
    @After
    public void tearDown() {
        usuario = null;
    }
    @Test
    public void CriarUsuario() {
        //assertEquals(null, usuario.getId());
        assertEquals("bruno", usuario.getNome());
        assertEquals("brunodesouza@gmail.com", usuario.getEmail());
        assertEquals("Teste senha","12345", usuario.getSenha());
        assertEquals("https://firebasestorage.googleapis.com/v0/b/e-trash-bb649.appspot.com/o/imagens%2Flixos%2F-Lhg_uEvZftKzSn3aaTv.jpeg?alt=media&token=abf9569c-b39f-4460-bc63-0819c1595b51", usuario.getCaminhoFoto());
        assertEquals("-Lhg_uEvZftKzSn3aaTv", usuario.getId());


    }
    @Test
    public void CiarLocalColeta(){
        LocalColeta localColeta = new LocalColeta();
        localColeta.setLongitude(123456.4);
        localColeta.setLatitude(4.654321);
        localColeta.setId("123");
        localColeta.setNome("UFRN");
        assertEquals("UFRN", localColeta.getNome());
        assertEquals("123", localColeta.getId());

        assertEquals(123456.4, localColeta.getLongitude());
        assertEquals(4.654321, localColeta.getLatitude());

    }
    @Test
    public void CiarComentario(){
        Lixo lixo = new Lixo();
        lixo.setId("123");
        Comentario comentario = new Comentario();
        comentario.setCaminhoFoto("Caminho");
        comentario.setComentario("comentario");
        comentario.setId("123");
        comentario.setIdLixo(lixo.getId());
        comentario.setNomeUsuario(usuario.getNome());
        comentario.setIdUsuario(usuario.getId());
        assertEquals("123", comentario.getId());
        assertEquals("Caminho", comentario.getCaminhoFoto());
        assertEquals("comentario", comentario.getComentario());
        assertEquals("123", comentario.getIdLixo());
        assertEquals("-Lhg_uEvZftKzSn3aaTv", comentario.getIdUsuario());



    }




}

