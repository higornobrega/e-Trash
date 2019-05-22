package com.example.e_trash.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.example.e_trash.R
import com.example.e_trash.fragment.AgendamentoFragment
import com.example.e_trash.fragment.CadastrarLixoFragment
import com.example.e_trash.fragment.PerfilFragment
import com.example.e_trash.fragment.PontoDeColetaFragment
import com.example.e_trash.helper.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao()

        // Configurar a Toolbar
        //toolbarPrincipal.setTitle("e-Trash")
        //setSupportActionBar(toolbarPrincipal)

        // BottomNavigation Fragment
        configurarBottomNavigationView()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.viewPager, PontoDeColetaFragment()).commit()

    }

    fun deslogarUsuario() {
        try {
            auth.signOut()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId) {
//            R.id.menu_deslogar -> deslogarUsuario()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun configurarBottomNavigationView() {
        val bottomNavigationViewEx: BottomNavigationViewEx = findViewById(R.id.bottomNavigation)

        bottomNavigationViewEx.enableAnimation(true)
        bottomNavigationViewEx.setTextVisibility(false)

        habilitarNavegacao(bottomNavigationViewEx)

        // Configura item selecionado inicialmente
        val menu: Menu = bottomNavigationViewEx.menu
        val menuItem: MenuItem = menu.getItem(0)
        menuItem.setChecked(true)
    }

    private fun habilitarNavegacao(viewEx: BottomNavigationViewEx) {
        viewEx.onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                when (menuItem.itemId) {
                    R.id.ic_pontos_coleta -> fragmentTransaction.replace(R.id.viewPager, PontoDeColetaFragment()).commit()
                    R.id.ic_agendamentos -> fragmentTransaction.replace(R.id.viewPager, AgendamentoFragment()).commit()
                    R.id.ic_cadastrar_lixo -> fragmentTransaction.replace(R.id.viewPager, CadastrarLixoFragment()).commit()
                    R.id.ic_perfil -> fragmentTransaction.replace(R.id.viewPager, PerfilFragment()).commit()
                }
                false
            }
    }
}
