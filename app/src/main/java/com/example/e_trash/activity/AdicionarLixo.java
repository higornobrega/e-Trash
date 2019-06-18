package com.example.e_trash.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.*;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.e_trash.R;
import com.example.e_trash.helper.ConfiguracaoFirebase;
import com.example.e_trash.helper.Permis;
import com.example.e_trash.helper.UsuarioFirebase;
import com.example.e_trash.model.Lixo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.List;

public class AdicionarLixo extends AppCompatActivity {
    private EditText campoEndereco;
    private Button botaoTirarFoto;
    private EditText informacoesLixo;
    private String idUsuarioLogado;
    private LocationManager locationManager;
    private Location location;
    private Address endereco;
    private Bitmap imagem;
    private Bitmap imagemFiltro;
    private ImageView imagem_lixo;
    private Button publicar_foto;
    private static final int SELECAO_CAMERA = 100;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lixo);
        Permis.validarPermissoes(permissoesNecessarias, this, 1);
        //imagemFiltro = imagem.copy(imagem.getConfig(), true );
        inicializarComponentes();
        recuperarLocalizacaoUsuario();
        checarPermissaoCamera();

        botaoTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        publicar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicarLixo();
                finish();
            }
        });


    }

    private void inicializarComponentes() {
        campoEndereco = findViewById(R.id.et_endereco);
        publicar_foto = findViewById(R.id.publicar_foto);
        botaoTirarFoto = findViewById(R.id.bt_camera);
        imagem_lixo = findViewById(R.id.imagem_lixo);
        informacoesLixo = findViewById(R.id.informacoesLixo);
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null ){
          byte[] dadosImagem = bundle.getByteArray("fotoEscolhida");
          imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
          imagem_lixo.setImageBitmap(imagem);
        }
    }
    private void publicarLixo(){
        final Lixo lixo = new Lixo();
        lixo.setIdUsuario(idUsuarioLogado);
        lixo.setInformacoes(informacoesLixo.getText().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG,70, baos);
        byte[] dadosImagem = baos.toByteArray();
        StorageReference storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        StorageReference imagemRef = storageRef
                .child("imagens")
                .child("lixos")
                .child(lixo.getId() + ".jpeg");

        UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdicionarLixo.this,
                        "Erro ao salvar imagem, tente novamente",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        lixo.setCaminhoFoto(uri.toString());
                        if(lixo.salvar()){
                            Toast.makeText(AdicionarLixo.this,"Sucesso ao fazer upload da imagem.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


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
        //startActivityForResult(intent, 1);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, SELECAO_CAMERA);
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imagem = (Bitmap) extras.get("data");
            imagem_lixo.setImageBitmap(imagem);
        }
    }*/
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK){
            Bitmap imagem = null;
            try {
                switch (requestCode){
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                }
                if(imagem != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    Intent i = new Intent(this, AdicionarLixo.class);
                    i.putExtra("fotoEscolhida", dadosImagem);
                    startActivity( i );
                }

            }catch (Exception e){
                e.printStackTrace();
            }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.publicar_foto :
                publicarLixo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
