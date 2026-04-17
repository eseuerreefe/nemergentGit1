package com.jaure.nemergentgit1;

import java.net.InetAddress;

public class ServicioPing {

    PingListener escuchador;
    boolean corriendo = false;
    Thread hilo;

    public ServicioPing(PingListener listener) {
        escuchador = listener;
    }

    public void iniciar(int max) {
        corriendo = true;
        hilo = new Thread(() -> {
            int ok = 0, mal = 0;
            for (int i = 1; i <= max && corriendo; i++) {
                boolean exito = false;
                try {
                    exito = InetAddress.getByName("google.com").isReachable(2000);
                } catch (Exception e) {}

                if (exito) ok++; else mal++;

                if (escuchador != null && corriendo) {
                    escuchador.onProgreso(i, exito);
                }

                try { Thread.sleep(600); } catch (InterruptedException e) { break; }
            }
            if (escuchador != null && corriendo) {
                escuchador.onTerminado(ok, mal);
            }
            corriendo = false;
        });
        hilo.start();
    }

    public void parar() {
        corriendo = false;
        if (hilo != null) hilo.interrupt();
    }
}