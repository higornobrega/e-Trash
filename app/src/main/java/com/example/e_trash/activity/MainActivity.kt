package com.example.e_trash.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import com.example.e_trash.R
import com.example.e_trash.helper.ConfiguracaoFirebase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
	private lateinit var auth: FirebaseAuth
	private lateinit var mMap: GoogleMap

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val toolbar: Toolbar = findViewById(R.id.toolbar)
		setSupportActionBar(toolbar)
		auth = ConfiguracaoFirebase.getReferenciaAutenticacao()

		val mapFragment = supportFragmentManager
			.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)

		val fab: FloatingActionButton = findViewById(R.id.fab)
		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show()
		}
		val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
		val navView: NavigationView = findViewById(R.id.nav_view)
		val toggle = ActionBarDrawerToggle(
			this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
		)
		drawerLayout.addDrawerListener(toggle)
		toggle.syncState()

		navView.setNavigationItemSelectedListener(this)
	}

	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap

		// Add a marker in Sydney and move the camera
		val sydney = LatLng(-34.0, 151.0)
		mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
	}

	override fun onBackPressed() {
		val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		when(item.itemId) {
            R.id.action_logout -> deslogarUsuario()
        }
        return super.onOptionsItemSelected(item)
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		// Handle navigation view item clicks here.
		when (item.itemId) {
			R.id.nav_home -> {
				val intent = Intent(this, MapsActivity::class.java)
				startActivity(intent)
			}
			R.id.nav_gallery -> {

			}
			R.id.nav_slideshow -> {

			}
			R.id.nav_tools -> {

			}
			R.id.nav_share -> {

			}
			R.id.nav_send -> {

			}
		}
		val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
		drawerLayout.closeDrawer(GravityCompat.START)
		return true
	}

	fun deslogarUsuario() {
		try {
			auth.signOut()
			Toast.makeText(applicationContext, "Deslogado com sucesso!", Toast.LENGTH_LONG).show()
			var intent = Intent(this, LoginActivity::class.java)
			startActivity(intent)
			finish()
		}
		catch (e: Exception) {
			e.printStackTrace()
			Toast.makeText(applicationContext, "Erro ao deslogar!", Toast.LENGTH_LONG).show()
		}
	}
}
