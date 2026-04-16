package com.jaure.nemergentgit1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {

        super(context, "fotos_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE fotos (id INTEGER PRIMARY KEY AUTOINCREMENT, fecha_captura TEXT, ruta_foto TEXT, latitud REAL, longitud REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fotos");
        onCreate(db);
    }

    public void insertarFoto(Foto f) {
        SQLiteDatabase base = this.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("fecha_captura", f.getFechacaptura());
        v.put("ruta_foto", f.getRutafoto());
        v.put("latitud", f.getLatitud());
        v.put("longitud", f.getLongitud());

        base.insert("fotos", null, v);
        base.close();
    }

    public List<Foto> obtenerTodasLasFotos() {
        List<Foto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM fotos", null);

        if (cursor.moveToFirst()) {
            do {

                Foto foto = new Foto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4)
                );
                lista.add(foto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public void borrarFoto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("fotos", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}