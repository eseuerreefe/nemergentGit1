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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    String rutaActual;
    FusedLocationProviderClient clienteGps;
    double latActual = 0;
    double lonActual = 0;
    RecyclerView recyclerView;
    FotoAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        clienteGps = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
        }, 100);

        findViewById(R.id.btnCamara).setOnClickListener(v -> {
            sacarLocalizacion();
            abrirCamara();
        });

        cargarFotos();
    }

    private void sacarLocalizacion() {
        try {
            clienteGps.getLastLocation().addOnSuccessListener(this, loc -> {
                if (loc != null) {
                    latActual = loc.getLatitude();
                    lonActual = loc.getLongitude();
                }
            });
        } catch (SecurityException e) {

        }
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
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(rutaActual))));

            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Foto f = new Foto(0, fecha, rutaActual, latActual, lonActual);
            dbHelper.insertarFoto(f);

            cargarFotos();
        }
    }

    private void cargarFotos() {
        List<Foto> fotosBd = dbHelper.obtenerTodasLasFotos();

        if (adaptador == null) {
            adaptador = new FotoAdapter(this, fotosBd, new FotoAdapter.OnFotoClickListener() {
                @Override
                public void onFotoClick(Foto f, int pos) {
                    Intent i = new Intent(MainActivity.this, VistaFotoActivity.class);
                    i.putExtra("ruta_foto", f.getRutafoto());
                    i.putExtra("fecha", f.getFechacaptura());
                    i.putExtra("lat", f.getLatitud());
                    i.putExtra("lon", f.getLongitud());
                    startActivity(i);
                }

                @Override
                public void onBorrarClick(Foto f, int pos) {
                    dbHelper.borrarFoto(f.getId());
                    new File(f.getRutafoto()).delete();
                    cargarFotos();
                }
            });
            recyclerView.setAdapter(adaptador);
        } else {
            adaptador.refrescar(fotosBd);
        }
    }
}