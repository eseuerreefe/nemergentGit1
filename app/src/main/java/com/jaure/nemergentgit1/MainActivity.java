package com.jaure.nemergentgit1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button btnCamara = findViewById(R.id.btnCamara);
        Button btnPing = findViewById(R.id.btnPing);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

    }
}