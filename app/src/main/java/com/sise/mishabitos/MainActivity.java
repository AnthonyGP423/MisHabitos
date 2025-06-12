package com.sise.mishabitos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.Frase;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Constants;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textoFraseDia = findViewById(R.id.texto_frase_dia);
        Button btnOtraFrase = findViewById(R.id.btn_otra_frase);
        FloatingActionButton botonAgregar = findViewById(R.id.btn_agregar_tarea);

        // ▶️ Llama a la función que obtiene la frase del backend
        obtenerFraseDelServidor(textoFraseDia);

        btnOtraFrase.setOnClickListener(v -> obtenerFraseDelServidor(textoFraseDia));

        // Menú lateral
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Lista de tareas de prueba
        LinearLayout contenedorTareas = findViewById(R.id.contenedor_tareas);
        List<String> listaTareas = Arrays.asList("Tarea 1", "Tarea 2", "Tarea 3");

        for (String nombreTarea : listaTareas) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(nombreTarea);
            checkBox.setTextColor(ContextCompat.getColor(this, R.color.texto_tareas));
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    getResources().getDimension(R.dimen.checkbox_text_size) / getResources().getDisplayMetrics().scaledDensity);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) getResources().getDimension(R.dimen.checkbox_height)
            );
            int margin = (int) getResources().getDimension(R.dimen.checkbox_margin);
            params.setMargins(margin, margin, margin, margin);
            checkBox.setLayoutParams(params);

            int padding = (int) getResources().getDimension(R.dimen.checkbox_padding);
            checkBox.setPadding(padding, padding, padding, padding);

            contenedorTareas.addView(checkBox);
        }

        botonAgregar.setOnClickListener(v -> {
            Toast.makeText(this, "Agregar nueva tarea", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CrearTarea.class);
            startActivity(intent);
        });
    }

    private void obtenerFraseDelServidor(TextView textoFraseDia) {
        String url = Constants.BASE_URL_API + "/frases";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        // Usa TypeToken para deserializar un BaseResponse<Frase>
                        Type tipoRespuesta = new TypeToken<BaseResponse<Frase>>(){}.getType();
                        BaseResponse<Frase> baseResponse = gson.fromJson(response, tipoRespuesta);

                        if (baseResponse != null && baseResponse.isSuccess() && baseResponse.getData() != null) {
                            textoFraseDia.setText(baseResponse.getData().getFrase());
                        } else {
                            textoFraseDia.setText("No se pudo obtener la frase");
                        }

                    } catch (Exception e) {
                        textoFraseDia.setText("Error al procesar la respuesta");
                        e.printStackTrace();
                    }
                },
                error -> {
                    textoFraseDia.setText("Error al conectarse con el servidor");
                    error.printStackTrace();
                });

        queue.add(request);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Abriste Perfil", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Abriste Configuración", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_faq) {
            Toast.makeText(this, "Abriste FAQ", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
