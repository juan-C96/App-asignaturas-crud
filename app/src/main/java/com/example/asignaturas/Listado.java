package com.example.asignaturas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {


    ListView vista_listado;
    ArrayList<String> listado;

    //Método donde se actualizan los datos del listado cuando se modifica alguna asignatura y se retrocede a la actividad del listado
    @Override
    protected void onPostResume() {
        super.onPostResume();
        CargarListado();
    }

    // Método para crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        vista_listado = (ListView) findViewById(R.id.list_view);


        CargarListado();

        //Implementación de la acción para pulsar en una asignatura y pasar los datos a otra actividad para poder modificarlos
        vista_listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int numero = Integer.parseInt(listado.get(position).split(": ")[0]);
                String nombre = listado.get(position).split(" ")[1];
                String nota = listado.get(position).split(" ")[2];
                Intent intent = new Intent(Listado.this, Modificar.class);
                intent.putExtra("Id", numero);
                intent.putExtra("NombreA", nombre);
                intent.putExtra("Nota", nota);
                startActivity(intent);
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //Muestra el botó atrás
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para cargar el listado de las asignaturas
    private void CargarListado(){
        listado = ListaAsignaturas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listado);
        vista_listado.setAdapter(adapter);
    }

    //Método para obtener el listado de las asignaturas en forma de string
    private ArrayList<String> ListaAsignaturas(){
        ArrayList<String> datos = new ArrayList<String>();
        Acceso access = new Acceso(this, "Demo", null,1);
        SQLiteDatabase db = access.getReadableDatabase();
        String sql = "select Id, Nombre, Nota from Asignaturas";
        Cursor c = db.rawQuery(sql, null);
        if(c.moveToFirst()){
            do{
                String linea = c.getInt(0) + ": " + c.getString(1) + " " + c.getString(2);
                datos.add(linea);

            }while(c.moveToNext());
        }
        db.close();
        return datos;
    }
}