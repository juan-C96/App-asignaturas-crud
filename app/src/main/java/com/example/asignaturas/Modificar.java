package com.example.asignaturas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Clase para modificar una asignatura
public class Modificar extends AppCompatActivity {

    EditText edit_nombre, edit_nota;
    Button bt_modificar, bt_eliminar;
    int id;
    String nombre, nota;

    //Se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        //Se obtienen los datos del listado con el intent
        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getInt("Id");
            nombre = b.getString("NombreA");
            nota = b.getString("Nota");
        }

        edit_nombre = (EditText) findViewById(R.id.edit_nombre);
        edit_nota = (EditText) findViewById(R.id.edit_nota);

        edit_nombre.setText(nombre);
        edit_nota.setText(nota);
        Toast.makeText(this, "Nombre: " + nombre + " Nota: " + nota, Toast.LENGTH_SHORT).show();

        bt_modificar = (Button) findViewById(R.id.bt_modificar);
        bt_eliminar = (Button) findViewById(R.id.bt_eliminar);

        //Acción del botón eliminar que llama al método eliminar
        bt_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar(id);
                onBackPressed();
            }
        });

        //Acción del botón modificar
        bt_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(edit_nombre.getText().toString().isEmpty()) && !(edit_nota.getText().toString().isEmpty())) {
                    float number = Float.parseFloat(edit_nota.getText().toString());
                    if (number < 0 || number > 10) {
                        mensajeNumeroInvalido();
                    } else {
                        Modificar(id, edit_nombre.getText().toString(), edit_nota.getText().toString());
                        onBackPressed();
                    }
                } else {
                    mensajeFaltanValores();
                }
            }
        });
    }

    //Métodos para mostrar mensajes, validando campos
    private void mensajeFaltanValores() {
        Toast.makeText(this, "Error: Debe añadir los datos requeridos", Toast.LENGTH_SHORT).show();
    }

    private void mensajeNumeroInvalido() {
        Toast.makeText(this, "Error: debe introducir una nota entre 0 y 10. ", Toast.LENGTH_SHORT).show();
    }

    //Método para modificar una asignatura en la base de datos
    private void Modificar(int Id, String Nombre, String Nota) {
        Acceso access = new Acceso(this, "Demo", null, 1);
        SQLiteDatabase db = access.getWritableDatabase();
        try {
        String sql = "update Asignaturas set Nombre = '" + Nombre + "', Nota = '" + Nota + "' where Id = " + Id;
        db.execSQL(sql);
        db.close();
            Toast.makeText(this, "La asignatura se ha modificado satisfactoriamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Método para eliminar una asignatura de la base de datos
    private void Eliminar(int Id) {
        Acceso access = new Acceso(this, "Demo", null, 1);
        SQLiteDatabase db = access.getWritableDatabase();
        try {
            String sql = "delete from Asignaturas where Id = " + Id;
            db.execSQL(sql);
            db.close();
            Toast.makeText(this, "La asignatura se ha borrado satisfactoriamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}