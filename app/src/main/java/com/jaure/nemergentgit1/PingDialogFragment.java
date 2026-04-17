package com.jaure.nemergentgit1;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class PingDialogFragment extends DialogFragment implements PingListener {

    EditText etNumIntentos;
    Button btnIniciar, btnParar;
    TextView tvResultados, tvProgreso;

    ServicioPing servicio;
    Handler manejador = new Handler(Looper.getMainLooper());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.dialog_ping, null);

        etNumIntentos = v.findViewById(R.id.etNumIntentos);
        btnIniciar = v.findViewById(R.id.btnIniciar);
        btnParar = v.findViewById(R.id.btnParar);
        tvResultados = v.findViewById(R.id.tvResultados);
        tvProgreso = v.findViewById(R.id.tvProgreso);

        servicio = new ServicioPing(this);

        btnIniciar.setOnClickListener(view -> empezarPings());
        btnParar.setOnClickListener(view -> pararPings());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Prueba de Ping")
                .setNegativeButton("Cerrar", (d, id) -> pararPings())
                .create();
    }

    private void empezarPings() {
        int max = Integer.parseInt(etNumIntentos.getText().toString());
        tvProgreso.setText("Iniciando...");
        btnIniciar.setEnabled(false);
        btnParar.setEnabled(true);

        servicio.iniciar(max);
    }

    private void pararPings() {
        servicio.parar();
        btnIniciar.setEnabled(true);
        btnParar.setEnabled(false);
    }

    @Override
    public void onProgreso(int intento, boolean exito) {
        manejador.post(() -> tvProgreso.append("\nIntento " + intento + ": " + (exito ? "OK" : "Error")));
    }

    @Override
    public void onTerminado(int ok, int mal) {
        manejador.post(() -> {
            tvResultados.setText("Ok: " + ok + " - Fallos: " + mal);
            btnIniciar.setEnabled(true);
            btnParar.setEnabled(false);
        });
    }
}