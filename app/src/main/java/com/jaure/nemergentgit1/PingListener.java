package com.jaure.nemergentgit1;

public interface PingListener {
    void onProgreso(int intento, boolean exito);
    void onTerminado(int ok, int mal);
}