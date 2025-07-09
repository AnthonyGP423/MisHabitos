package com.sise.mishabitos.activities;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private RecyclerView recyclerHabitos;
    private HabitoAdapter habitoAdapter;

    private FraseMotivacionalViewModel fraseViewModel;
    private HabitoViewModel habitoViewModel;
    private SeguimientoViewModel seguimientoViewModel;

    private TextView fraseMostrada;
    private Button btnOtraFrase;
    private Button btnVerHistorial;
    private int idUsuario;
    private String fechaHoy;

    private List<Seguimiento> seguimientosCompletados = new ArrayList<>();
    private List<Habito> habitosDelUsuario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesManager sp = SharedPreferencesManager.getInstance(this);
        String token = sp.getToken();
        idUsuario = sp.getUserId();

        if (token == null || token.isEmpty() || idUsuario == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        fechaHoy = LocalDate.now().toString();

        inicializarUI();
        configurarViewModels();
        configurarMenuLateral();
        configurarRecyclerHabitos();
        configurarBotonFlotante();
    }
    private void inicializarUI() {
        fraseMostrada = findViewById(R.id.frase_mostrada);
        btnOtraFrase = findViewById(R.id.btn_otra_frase);
        btnVerHistorial = findViewById(R.id.btn_historial);

        btnOtraFrase.setOnClickListener(v -> fraseViewModel.listarFrases(this));

        btnVerHistorial.setOnClickListener(v -> {
            startActivity(new Intent(this, HistorialActivity.class));
        });
    }
    private void configurarViewModels() {
        fraseViewModel = new ViewModelProvider(this).get(FraseMotivacionalViewModel.class);
        habitoViewModel = new ViewModelProvider(this).get(HabitoViewModel.class);
        seguimientoViewModel = new ViewModelProvider(this).get(SeguimientoViewModel.class);

        fraseViewModel.getFraseDelDiaLiveData().observe(this, this::mostrarFraseDelDia);
        fraseViewModel.listarFrases(this);

        habitoViewModel.getListarHabitosLiveData().observe(this, response -> {
            if (response != null && response.isSuccess() && response.getData() != null) {

                habitosDelUsuario = response.getData();
                Log.d("MainActivity", "Hábitos obtenidos: " + habitosDelUsuario.size());

                // Siempre actualiza la lista, aunque sea vacía
                habitoAdapter.actualizarLista(habitosDelUsuario);

                if (habitosDelUsuario.isEmpty()) {
                    Toast.makeText(this, "No tienes hábitos aún.", Toast.LENGTH_SHORT).show();
                } else {
                    // Solo si hay hábitos, carga los seguimientos
                    seguimientoViewModel.listarSeguimientosCompletadosPorUsuario(this, idUsuario);
                }

            } else {
                // Si hubo error, vacía la lista visual
                habitoAdapter.actualizarLista(new ArrayList<>());
                Toast.makeText(this, "No se pudieron cargar los hábitos", Toast.LENGTH_SHORT).show();
            }
        });

        seguimientoViewModel.getSeguimientosCompletadosLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                seguimientosCompletados = response.getData();
                Log.d("MainActivity", "Seguimientos COMPLETADOS recibidos: " + seguimientosCompletados.size());
                actualizarVistaHabitos();
            } else {
                Toast.makeText(this, "No se pudieron cargar seguimientos completados", Toast.LENGTH_SHORT).show();
            }
        });

        seguimientoViewModel.getInsertarSeguimientoLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                Toast.makeText(this, "Hábito completado ✅", Toast.LENGTH_SHORT).show();
                cargarDatos();
            } else {
                Toast.makeText(this, "No se pudo marcar como completado", Toast.LENGTH_SHORT).show();
                actualizarVistaHabitos();
            }
        });
    }

    private void cargarDatos() {
        habitoViewModel.listarHabitosPorUsuario(this);
        seguimientoViewModel.listarSeguimientosCompletadosPorUsuario(this, idUsuario);
    }

    private void actualizarVistaHabitos() {
        if (habitosDelUsuario == null || habitosDelUsuario.isEmpty()) {
            Toast.makeText(this, "No tienes hábitos aún.", Toast.LENGTH_SHORT).show();
            habitoAdapter.actualizarLista(new ArrayList<>());
            return;
        }

        List<Habito> habitosPendientes = new ArrayList<>();

        for (Habito habito : habitosDelUsuario) {
            boolean completado = false;

            for (Seguimiento seguimiento : seguimientosCompletados) {
                if (seguimiento.getHabito() != null &&
                        habito.getIdHabito().equals(seguimiento.getHabito().getIdHabito()) &&
                        Boolean.TRUE.equals(seguimiento.getCompletado())) {
                    completado = true;
                    break;
                }
            }

            habito.setCompletadoLocal(completado);

            if (!completado) {
                habitosPendientes.add(habito);
            }
        }

        habitoAdapter.actualizarLista(habitosPendientes);
    }

    private void configurarMenuLateral() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configurarRecyclerHabitos() {
        recyclerHabitos = findViewById(R.id.recycler_habitos);
        recyclerHabitos.setLayoutManager(new LinearLayoutManager(this));

        habitoAdapter = new HabitoAdapter(this, new ArrayList<>(), new HabitoAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(Habito habito) {
                Intent intent = new Intent(MainActivity.this, EditarHabitoActivity.class);
                intent.putExtra("habito", habito);
                startActivity(intent);
            }

            @Override
            public void onCompletarClick(Habito habito) {
                if (!habito.isCompletadoLocal()) {
                    mostrarDialogoNota(habito);
                } else {
                    Toast.makeText(MainActivity.this, "Este hábito ya fue completado ✅", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerHabitos.setAdapter(habitoAdapter);
    }

    private void registrarSeguimiento(Habito habito, String notaDia) {
        Seguimiento s = new Seguimiento();
        s.setIdUsuario(idUsuario);
        s.setIdHabito(habito.getIdHabito());
        s.setFecha(fechaHoy);
        s.setEstado(true);
        s.setCompletado(true);
        s.setNotaDia(notaDia);

        seguimientoViewModel.insertarSeguimiento(this, s);
    }

    private void configurarBotonFlotante() {
        FloatingActionButton botonAgregar = findViewById(R.id.btn_agregar_tarea);
        botonAgregar.setOnClickListener(v -> startActivity(new Intent(this, CrearHabitoActivity.class)));
    }

    private void mostrarFraseDelDia(String texto) {
        fraseMostrada.setText(texto);
    }

    private void mostrarDialogoNota(Habito habito) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Nota del Día 📝");

        final EditText input = new EditText(this);
        input.setHint("Escribe una nota (opcional)");
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nota = input.getText().toString().trim();
            registrarSeguimiento(habito, nota.isEmpty() ? "Cumplido" : nota);
            Toast.makeText(this, "¡Genial! Hábito completado 💪", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, PerfilUsuarioActivity.class));
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Abriste Configuración", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_faq) {
            Toast.makeText(this, "Abriste FAQ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            SharedPreferencesManager.getInstance(this).clearSession();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
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
