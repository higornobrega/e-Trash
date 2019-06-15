package com.example.e_trash.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.*;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.e_trash.R;
import java.io.IOException;
import java.util.List;

public class AdicionarLixo extends AppCompatActivity {
    private EditText campoEndereco;
    private Button botaoTirarFoto;
    private LocationManager locationManager;
    private Location location;
    private Address endereco;
    private Bitmap imagem;
    private ImageView imagem_lixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lixo);

        inicializarComponentes();
        recuperarLocalizacaoUsuario();
        checarPermissaoCamera();

        botaoTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });
    }

    private void inicializarComponentes() {
        campoEndereco = findViewById(R.id.et_endereco);
        botaoTirarFoto = findViewById(R.id.bt_camera);
        imagem_lixo = findViewById(R.id.imagem_lixo);
    }

    private void checarPermissaoCamera() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    public  void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imagem = (Bitmap) extras.get("data");
            imagem_lixo.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
