package com.example.e_trash.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.*;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_trash.R;
import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.example.e_trash.helper.UsuarioFirebase;
import com.example.e_trash.model.Lixo;
import com.example.e_trash.model.LocalColeta;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class AdicionarPontoDeColeta extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Address ponto;
    private Address endereco;
    private EditText nomePontoColeta;
    private Button botaoSalvar;
    private Button botaoCancelar;
    private String idUsuarioLogado;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_ponto_de_coleta);
        inicializarComponentes();
        //recuperarLocalizacaoUsuario();
        //checarPermissaoCamera();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicarPontoDeColeta();
                finish();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void inicializarComponentes() {
        nomePontoColeta = findViewById(R.id.et_nome_ponto_coleta);
        botaoSalvar = findViewById(R.id.bt_salvar_ponto_coleta);
        botaoCancelar = findViewById(R.id.bt_cancelar_ponto_coleta);
        nomePontoColeta = findViewById(R.id.et_nome_ponto_coleta);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        recuperarLocalizacaoUsuario();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.lixeira)));
            }
        });
    }

    private void publicarPontoDeColeta() {
        final LocalColeta local_coleta = new LocalColeta();
        local_coleta.setNome(nomePontoColeta.getText().toString());
        local_coleta.setLatitude(ponto.getLatitude());
        local_coleta.setLongitude(ponto.getLongitude());
        if (local_coleta.salvar()) {
            Toast.makeText(AdicionarPontoDeColeta.this, "Sucesso ao salvar postagem!", Toast.LENGTH_SHORT).show();
        }


    }

    private void recuperarLocalizacaoUsuario() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        else {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // recupera latitude e longitude
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            try {
                ponto = buscarEndereco(latitude, longitude);
            } catch (IOException e) {
                Toast.makeText(AdicionarPontoDeColeta.this, "GPS" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
//
//    private void recuperarLocalizacaoUsuario() {
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                LatLng meuLocal = new LatLng(latitude, longitude);
//                mMap.addMarker(new MarkerOptions().position(meuLocal).title("Você está aqui").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meuLocal, 15));
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 5, locationListener);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())){
            case R.id.bt_salvar_ponto_coleta:
                publicarPontoDeColeta();
        }
        return  super.onOptionsItemSelected(item);
    }
    public Address buscarEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if (addresses.size() > 0) {
            address = addresses.get(0);
        }
        return address;
    }
}
