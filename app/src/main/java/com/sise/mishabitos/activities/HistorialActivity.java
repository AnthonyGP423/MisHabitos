package com.sise.mishabitos.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sise.mishabitos.R;
import com.sise.mishabitos.adapters.HistorialAdapter;
import com.sise.mishabitos.entities.Seguimiento;
import com.sise.mishabitos.shared.SharedPreferencesManager;
import com.sise.mishabitos.viewmodel.SeguimientoViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerHistorial;
    private TextView txtHistorialVacio;
    private HistorialAdapter historialAdapter;
    private SeguimientoViewModel seguimientoViewModel;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        inicializarUI();
        configurarRecycler();
        configurarViewModel();
        cargarHistorial();
    }

    private void inicializarUI() {
        recyclerHistorial = findViewById(R.id.recyclerHistorial);
        txtHistorialVacio = findViewById(R.id.txtHistorialVacio);

        idUsuario = SharedPreferencesManager.getInstance(this).getUserId();
    }

    private void configurarRecycler() {
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(this));
        historialAdapter = new HistorialAdapter(new ArrayList<>());
        recyclerHistorial.setAdapter(historialAdapter);
    }

    private void configurarViewModel() {
        seguimientoViewModel = new SeguimientoViewModel();

        seguimientoViewModel.getSeguimientosCompletadosLiveData().observe(this, response -> {
            if (response != null && response.isSuccess() && response.getData() != null && !response.getData().isEmpty()) {
                mostrarHistorial(response.getData());
            } else {
                mostrarSinRegistros();
            }
        });
    }

    private void cargarHistorial() {
        seguimientoViewModel.listarSeguimientosCompletadosPorUsuario(this, idUsuario);
    }

    private void mostrarHistorial(List<Seguimiento> lista) {
        historialAdapter.actualizarLista(lista);
        recyclerHistorial.setVisibility(View.VISIBLE);
        txtHistorialVacio.setVisibility(View.GONE);
    }

    private void mostrarSinRegistros() {
        recyclerHistorial.setVisibility(View.GONE);
        txtHistorialVacio.setVisibility(View.VISIBLE);
    }
}
