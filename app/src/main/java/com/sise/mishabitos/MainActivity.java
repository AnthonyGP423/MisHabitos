package com.sise.mishabitos;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Toast;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sise.mishabitos.activities.CrearHabitoActivity;
import com.sise.mishabitos.activities.LoginActivity;
import com.sise.mishabitos.shared.SharedPreferencesManager;
import com.sise.mishabitos.viewmodel.FraseMotivacionalViewModel;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FraseMotivacionalViewModel viewModel;
    private LinearLayout layoutFrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesManager sp = SharedPreferencesManager.getInstance(this);
        String token = sp.getToken();
        int userId = sp.getUserId();

        if (token == null || token.isEmpty() || userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        inicializarUI();
        configurarViewModel();
        configurarMenuLateral();
        cargarTareasEjemplo();
        configurarBotonFlotante();

    }

    private void inicializarUI() {
        Button btnOtraFrase = findViewById(R.id.btn_otra_frase);
        layoutFrases = findViewById(R.id.layout_frases);
        btnOtraFrase.setOnClickListener(v -> viewModel.listarFrases(this));
    }

    private void configurarViewModel() {
        viewModel = new ViewModelProvider(this).get(FraseMotivacionalViewModel.class);

        viewModel.getFraseDelDiaLiveData().observe(this, this::agregarFraseALista);
        viewModel.listarFrases(this);
    }

    private void configurarMenuLateral() {
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
    }

    private void cargarTareasEjemplo() {
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
    }

    private void configurarBotonFlotante() {
        FloatingActionButton botonAgregar = findViewById(R.id.btn_agregar_tarea);
        botonAgregar.setOnClickListener(v -> {
            Toast.makeText(this, "Agregar nueva tarea", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CrearHabitoActivity.class));
        });
    }

    private void agregarFraseALista(String texto) {
        TextView fraseMostrada = findViewById(R.id.frase_mostrada);
        fraseMostrada.setText(texto);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, LoginActivity.class));
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
