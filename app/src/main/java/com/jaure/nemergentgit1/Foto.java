package com.jaure.nemergentgit1;


public class Foto extends FotoBase {
    private double latitud, longitud;

    public Foto(int id, String fecha, String ruta, double lat, double lon) {
        super(id, fecha, ruta);
        latitud = lat;
        longitud = lon;
    }

    public double getLatitud() { return latitud; }
    public void setLatitud(double lat) { latitud = lat; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double lon) { longitud = lon; }

    public String getInfo() {
        return super.getInfo() + " - Loc: " + latitud + "," + longitud;
    }
}