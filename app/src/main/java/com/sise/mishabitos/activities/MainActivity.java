package com.sise.mishabitos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sise.mishabitos.R;
import com.sise.mishabitos.adapters.HabitoAdapter;
import com.sise.mishabitos.entities.Habito;
import com.sise.mishabitos.entities.Seguimiento;
import com.sise.mishabitos.shared.SharedPreferencesManager;
import com.sise.mishabitos.viewmodel.FraseMotivacionalViewModel;
import com.sise.mishabitos.viewmodel.HabitoViewModel;
import com.sise.mishabitos.viewmodel.SeguimientoViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FraseMotivacionalViewModel fraseViewModel;
    private HabitoViewModel habitoViewModel;
    private SeguimientoViewModel seguimientoViewModel;

    private RecyclerView recyclerHabitos;
    private HabitoAdapter habitoAdapter;

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
        configurarViewModels();
        configurarMenuLateral();
        configurarRecyclerHabitos();
        configurarBotonFlotante();
    }

    private void inicializarUI() {
        Button btnOtraFrase = findViewById(R.id.btn_otra_frase);
        btnOtraFrase.setOnClickListener(v -> fraseViewModel.listarFrases(this));

        Button btnHistorial = findViewById(R.id.btn_historial);
        btnHistorial.setOnClickListener(v -> startActivity(new Intent(this, HistorialActivity.class)));
    }

    private void configurarViewModels() {
        fraseViewModel = new ViewModelProvider(this).get(FraseMotivacionalViewModel.class);
        habitoViewModel = new ViewModelProvider(this).get(HabitoViewModel.class);
        seguimientoViewModel = new ViewModelProvider(this).get(SeguimientoViewModel.class);

        fraseViewModel.getFraseDelDiaLiveData().observe(this, this::mostrarFraseDelDia);
        fraseViewModel.listarFrases(this);

        habitoViewModel.getListarHabitosLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                habitoAdapter.actualizarLista(response.getData());
            } else {
                Toast.makeText(this, "No se pudieron cargar los hábitos", Toast.LENGTH_SHORT).show();
            }
        });

        int userId = SharedPreferencesManager.getInstance(this).getUserId();

        habitoViewModel.listarHabitosPorUsuario(this, userId);

        seguimientoViewModel.getInsertarSeguimientoLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                Toast.makeText(this, "Hábito completado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se pudo marcar como completado", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void configurarRecyclerHabitos() {
        recyclerHabitos = findViewById(R.id.recycler_habitos);
        recyclerHabitos.setLayoutManager(new LinearLayoutManager(this));

        habitoAdapter = new HabitoAdapter(
                this,
                new ArrayList<>(),
                new HabitoAdapter.OnItemClickListener() {
                    @Override
                    public void onEditarClick(Habito habito) {
                        Intent intent = new Intent(MainActivity.this, EditarHabitoActivity.class);
                        intent.putExtra("habito", habito);
                        startActivity(intent);
                    }

                    @Override
                    public void onCompletarClick(Habito habito) {
                        Toast.makeText(MainActivity.this, "Completar hábito: " + habito.getNombre(), Toast.LENGTH_SHORT).show();
                        registrarSeguimiento(habito);
                    }
                }
        );

        recyclerHabitos.setAdapter(habitoAdapter);
    }

    private void registrarSeguimiento(Habito habito) {
        int idUsuario = SharedPreferencesManager.getInstance(this).getUserId();
        String fechaHoy = LocalDate.now().toString();

        Seguimiento s = new Seguimiento();
        s.setIdUsuario(idUsuario);
        s.setIdHabito(habito.getIdHabito());
        s.setFecha(fechaHoy);
        s.setEstado(true);

        seguimientoViewModel.insertarSeguimiento(this, s);
    }

    private void configurarBotonFlotante() {
        FloatingActionButton botonAgregar = findViewById(R.id.btn_agregar_tarea);
        botonAgregar.setOnClickListener(v -> startActivity(new Intent(this, CrearHabitoActivity.class)));
    }

    private void mostrarFraseDelDia(String texto) {
        TextView fraseMostrada = findViewById(R.id.frase_mostrada);
        fraseMostrada.setText(texto);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, PerfilUsuarioActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, ConfiguracionActivity.class));
        } else if (id == R.id.nav_faq) {
            Toast.makeText(this, "Abriste FAQ", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userId = SharedPreferencesManager.getInstance(this).getUserId();
        habitoViewModel.listarHabitosPorUsuario(this, userId);
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
