package com.sise.mishabitos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button btnVolverMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        inicializarUI();
        configurarRecycler();
        configurarViewModel();
        configurarBotonVolver();
    }

    private void inicializarUI() {
        recyclerHistorial = findViewById(R.id.recyclerHistorial);
        txtHistorialVacio = findViewById(R.id.txtHistorialVacio);
        btnVolverMain = findViewById(R.id.btn_volver_main);

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
                List<Seguimiento> lista = response.getData();
                historialAdapter.actualizarLista(lista);
                recyclerHistorial.setVisibility(View.VISIBLE);
                txtHistorialVacio.setVisibility(View.GONE);
            } else {
                recyclerHistorial.setVisibility(View.GONE);
                txtHistorialVacio.setVisibility(View.VISIBLE);
            }
        });

        seguimientoViewModel.listarSeguimientosCompletadosPorUsuario(this, idUsuario);
    }

    private void configurarBotonVolver() {
        btnVolverMain.setOnClickListener(v -> {
            Intent intent = new Intent(HistorialActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
