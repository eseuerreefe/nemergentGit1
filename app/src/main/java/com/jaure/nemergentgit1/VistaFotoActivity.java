package com.jaure.nemergentgit1;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VistaFotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_foto);

        ImageView img = findViewById(R.id.imgFotoGrande);
        TextView tv = findViewById(R.id.tvInfoFoto);

        String ruta = getIntent().getStringExtra("ruta_foto");
        String fecha = getIntent().getStringExtra("fecha");
        double lat = getIntent().getDoubleExtra("lat", 0);
        double lon = getIntent().getDoubleExtra("lon", 0);

        img.setImageURI(Uri.parse(ruta));

        tv.setText("Fecha: " + fecha + "\nLoc: " + lat + ", " + lon);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ver foto");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}