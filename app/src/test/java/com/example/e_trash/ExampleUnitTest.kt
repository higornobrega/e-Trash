package com.example.e_trash

import com.example.e_trash.model.Usuario
import org.junit.Test

import org.junit.Assert.*

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
        val user = Usuario()
        user.nome = "bruno"
        user.email = "brunodesouza@gmail.com"
        user.senha = "12345"

        assertEquals(null, user.id)
        assertEquals("bruno", user.nome)
        assertEquals("brunodesouza@gmail.com", user.email)
        assertEquals("Teste senha","12345", user.senha)
    }
}
