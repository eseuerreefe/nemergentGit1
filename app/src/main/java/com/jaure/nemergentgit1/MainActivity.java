package com.jaure.nemergentgit1;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    String rutaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        //Pedirle permiso al usuario al abrir la app
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
        }, 100);

        findViewById(R.id.btnCamara).setOnClickListener(v -> {
            abrirCamara();
        });
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            String nombre = "FOTO_" + System.currentTimeMillis();
            File carpeta = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imagen = File.createTempFile(nombre, ".jpg", carpeta);

            rutaActual = imagen.getAbsolutePath();

            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Foto f = new Foto(0, fecha, rutaActual, 0.0, 0.0);
            dbHelper.insertarFoto(f);
        }
    }
}