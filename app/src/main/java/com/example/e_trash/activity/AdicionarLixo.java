package com.example.e_trash.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.example.e_trash.R;
import java.io.IOException;
import java.util.List;

public class AdicionarLixo extends AppCompatActivity {
    private EditText campoEndereco;
    private LocationManager locationManager;
    private Location location;
    private Address endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lixo);

        inicializarComponentes();
        recuperarLocalizacaoUsuario();
    }

    private void inicializarComponentes() {
        campoEndereco = findViewById(R.id.et_endereco);
    }

    private void recuperarLocalizacaoUsuario() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        }
        else {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // recupera latitude e longitude
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            try {
                endereco = buscarEndereco(latitude, longitude);
                campoEndereco.setText(endereco.getAddressLine(0));
            } catch (IOException e) {
                Toast.makeText(AdicionarLixo.this, "GPS" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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
