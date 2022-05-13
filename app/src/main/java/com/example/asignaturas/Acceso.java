package com.example.asignaturas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//clase de acceso a la base de datos
public class Acceso extends SQLiteOpenHelper {

    String tabla = "CREATE TABLE ASIGNATURAS(ID INTEGER PRIMARY KEY, NOMBRE TEXT, NOTA TEXT)";

    public Acceso(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // se crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    // metodo para actualizar las tablas en la base de dato
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table asignaturas");
        db.execSQL(tabla);
    }
}