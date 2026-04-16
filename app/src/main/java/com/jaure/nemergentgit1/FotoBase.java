package com.jaure.nemergentgit1;


public class FotoBase {
    protected int id;
    protected String fechacaptura;
    protected String rutafoto;

    public FotoBase(int id, String fecha, String ruta) {
        this.id = id;
        fechacaptura = fecha;
        rutafoto = ruta;
    }
    //prueba

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFechacaptura() { return fechacaptura; }
    public void setFechacaptura(String f) { fechacaptura = f; }

    public String getRutafoto() { return rutafoto; }
    public void setRutafoto(String r) { rutafoto = r; }

    public String getInfo() {
        return "Foto: " + rutafoto + " - Fecha: " + fechacaptura;
    }
}