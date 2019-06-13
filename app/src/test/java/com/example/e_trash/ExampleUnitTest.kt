package com.example.e_trash

import com.example.e_trash.model.Usuario
import com.example.e_trash.model.Lixo
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun createUser() {
        var user = Usuario()
        user.nome = "bruno"
        user.email = "brunodesouza@gmail.com"
        user.senha = "12345"

        assertEquals(null, user.id)
        assertEquals("bruno", user.nome)
        assertEquals("brunodesouza@gmail.com", user.email)
        assertEquals("Teste senha","12345", user.senha)

        //user = null

    }
    @Test
    fun createLixo(){
        var user = Usuario()
        user.nome = "bruno"
        user.email = "brunodesouza@gmail.com"
        user.senha = "12345"

        val lixo = Lixo();
        lixo.nome = "Lixo1"
        lixo.longitude = -52.4481016
        lixo.latitude = -23.0720325
        lixo.informacoes = "Informações lixo1"
        lixo.idUsuario = user.id
        lixo.endereco = "Rua Lixo1"
        lixo.caminhoFoto = "Caminho foto lixo1"

        assertEquals(null, lixo.id)
        assertEquals("Lixo1", lixo.nome)
        //assertEquals(-52.4481016, lixo.longitude)
        //assertEquals(-23.0720325, lixo.latitude)
        assertEquals("Informações lixo1", lixo.informacoes)
        assertEquals(user.id, lixo.idUsuario)
        assertEquals("Rua Lixo1", lixo.endereco)
        assertEquals("Caminho foto lixo1", lixo.caminhoFoto)

    }
    @Test
    fun AtualizarUsuario(){
        var user = Usuario()
        user.nome = "bruno"
        user.email = "brunodesouza@gmail.com"
        user.senha = "12345"
        assertEquals(null, user.id)
        assertEquals("bruno", user.nome)
        assertEquals("brunodesouza@gmail.com", user.email)
        assertEquals("Teste senha","12345", user.senha)

        user.nome = "bbrruunnoo"
        user.email = "bruno@gmail.com"
        user.senha = "12345abc"
        assertEquals(null, user.id)
        assertEquals("bbrruunnoo", user.nome)
        assertEquals("bruno@gmail.com", user.email)
        assertEquals("Teste senha","12345abc", user.senha)
    }
    @Test
    fun AtualizarLixo(){
        var user = Usuario()
        user.nome = "bruno"
        user.email = "brunodesouza@gmail.com"
        user.senha = "12345"

        val lixo = Lixo();
        lixo.nome = "Lixo1"
        lixo.longitude = -52.4481016
        lixo.latitude = -23.0720325
        lixo.informacoes = "Informações lixo1"
        lixo.idUsuario = user.id
        lixo.endereco = "Rua Lixo1"
        lixo.caminhoFoto = "Caminho foto lixo1"

        assertEquals(null, lixo.id)
        assertEquals("Lixo1", lixo.nome)
        //assertEquals(-52.4481016, lixo.longitude)
        //assertEquals(-23.0720325, lixo.latitude)
        assertEquals("Informações lixo1", lixo.informacoes)
        assertEquals(user.id, lixo.idUsuario)
        assertEquals("Rua Lixo1", lixo.endereco)
        assertEquals("Caminho foto lixo1", lixo.caminhoFoto)

        lixo.nome = "Lixo1.1"
        lixo.longitude = -52.4481016
        lixo.latitude = -23.0720325
        lixo.informacoes = "Informações lixo1.1"
        lixo.idUsuario = user.id
        lixo.endereco = "Rua Lixo1.1"
        lixo.caminhoFoto = "Caminho foto lixo1.1"

        assertEquals(null, lixo.id)
        assertEquals("Lixo1.1", lixo.nome)
        //assertEquals(-52.4481016, lixo.longitude)
        //assertEquals(-23.0720325, lixo.latitude)
        assertEquals("Informações lixo1.1", lixo.informacoes)
        assertEquals(user.id, lixo.idUsuario)
        assertEquals("Rua Lixo1.1", lixo.endereco)
        assertEquals("Caminho foto lixo1.1", lixo.caminhoFoto)

    }


}
