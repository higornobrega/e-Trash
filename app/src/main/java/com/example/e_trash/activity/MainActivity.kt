package com.example.e_trash.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import com.example.e_trash.R
import com.example.e_trash.helper.ConfiguracaoFirebase
import com.example.e_trash.helper.Permissao
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
	private lateinit var auth: FirebaseAuth
	private lateinit var mMap: GoogleMap
	private val permissoes = arrayOf<String>(android.Manifest.permission.ACCESS_FINE_LOCATION)
	private lateinit var locationManager: LocationManager
	private lateinit var locationListener: LocationListener

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val toolbar: Toolbar = findViewById(R.id.toolbar)
		setSupportActionBar(toolbar)
		auth = ConfiguracaoFirebase.getReferenciaAutenticacao()

		// Validar permissoes
		Permissao.validarPermissoes(permissoes, this, 1)

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

		// Objeto reponsavel por gerenciar a localizacao do usuario
		locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
		locationListener = object : LocationListener {
			override fun onLocationChanged(location: Location) {
				val latitude: Double = location.latitude
				val longitude: Double = location.longitude
				val localUsuario = LatLng(latitude, longitude)
				mMap.clear()
				mMap.addMarker(MarkerOptions().position(localUsuario).title("Você está aqui").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 15.0f))
			}
			override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

			}
			override fun onProviderEnabled(provider: String) {

			}
			override fun onProviderDisabled(provider: String) {

			}
		}

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 5.0f, locationListener)
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		for (permissaoResultado in grantResults) {
			if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
				alertaValidacaoPermissao()
			}
			else {
				//Recuperar localizacao do usuario

				/*
				* 1) Provedor da localização
				* 2) Tempo mínimo entre atualizacões de localização (milesegundos)
				* 3) Distancia mínima entre atualizacões de localização (metros)
				* 4) Location listener (para recebermos as atualizações)
				* */
				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 5.0f, locationListener)
				}
			}

		}
	}

	fun alertaValidacaoPermissao() {
		val builder = AlertDialog.Builder(this)
		builder.setTitle("Permissoẽs Negadas")
		builder.setMessage("Para utilizar o app é necessário aceitar as permissões")
		builder.setCancelable(false)
		builder.setPositiveButton("Confirmar", object:DialogInterface.OnClickListener {
			override fun onClick(dialog:DialogInterface, which:Int) {
				finish()
			}
		})

		val dialog: AlertDialog = builder.create()
        dialog.show()
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
			R.id.nav_perfil -> {
				val intent = Intent(this, EditarPerfilActivity::class.java)
				startActivity(intent)
			}
			R.id.nav_agendamentos -> {

			}
			R.id.nav_slideshow -> {

			}
			R.id.nav_tools -> {

			}
			R.id.nav_add_ponto_coleta -> {
				val intent = Intent(this, AdicionarPontoDeColeta::class.java)
				startActivity(intent)
			}
			R.id.nav_rem_ponto_coleta -> {
				val intent = Intent(this, RemoverPontoDeColeta::class.java)
				startActivity(intent)
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
