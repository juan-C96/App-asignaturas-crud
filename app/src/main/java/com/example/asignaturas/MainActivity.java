package com.example.asignaturas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asignaturas.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    EditText edit_nombre, edit_nota;
    Button bt_guardar, bt_mostrar;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    //Método donde se crea la actividad y los widgets
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Listado.class));
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        edit_nombre = (EditText) findViewById(R.id.edit_nombre);
        edit_nota = (EditText) findViewById(R.id.edit_nota);

        bt_guardar = (Button) findViewById(R.id.bt_guardar);
        bt_mostrar = (Button) findViewById(R.id.bt_ver);

        //Función para la acción del botón de guardar una asignatura
        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(edit_nombre.getText().toString().isEmpty()) && !(edit_nota.getText().toString().isEmpty())) {
                    float number = Float.parseFloat(edit_nota.getText().toString());
                    if (number < 0 || number > 10){
                        mensajeNumeroInvalido();
                    } else {
                        guardar(edit_nombre.getText().toString(), edit_nota.getText().toString());
                    }
                } else {
                    mensajeFaltanValores();
                }
            }
        });

        //Función para la acción de mostrar todas las asignaturas con sus calificaciones
        bt_mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Listado.class));
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Métodos para mostrar mensajes, validando campos
    private void mensajeNumeroInvalido(){
        Toast.makeText(this, "Error: debe introducir una nota entre 0 y 10. ", Toast.LENGTH_SHORT).show();
    }

    private void mensajeFaltanValores(){
        Toast.makeText(this, "Error: Debe añadir los datos requeridos", Toast.LENGTH_SHORT).show();
    }

    //Método para guardar una asignatura en la base de datos
    private void guardar(String Nombre, String Nota) {
        Acceso access = new Acceso(this, "Demo", null, 1);
        SQLiteDatabase db = access.getWritableDatabase();
        try {
                ContentValues c = new ContentValues();
                c.put("Nombre", Nombre);
                c.put("Nota", Nota);
                db.insert("ASIGNATURAS", null, c);
                db.close();
                Toast.makeText(this, "Asignatura guardada satisfactoriamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}