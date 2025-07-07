package com.sise.mishabitos.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sise.mishabitos.R;
import com.sise.mishabitos.adapters.HistorialAdapter;
import com.sise.mishabitos.viewmodel.SeguimientoViewModel;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerHistorial;
    private TextView txtHistorialVacio;
    private HistorialAdapter historialAdapter;
    private SeguimientoViewModel seguimientoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerHistorial = findViewById(R.id.recyclerHistorial);
        txtHistorialVacio = findViewById(R.id.txtHistorialVacio);

        recyclerHistorial.setLayoutManager(new LinearLayoutManager(this));
        historialAdapter = new HistorialAdapter(new ArrayList<>());
        recyclerHistorial.setAdapter(historialAdapter);

        seguimientoViewModel = new ViewModelProvider(this).get(SeguimientoViewModel.class);

        seguimientoViewModel.getListarSeguimientosPorUsuarioLiveData().observe(this, response -> {
            if (response.isSuccess() && response.getData() != null && !response.getData().isEmpty()) {
                historialAdapter.actualizarLista(response.getData());
                recyclerHistorial.setVisibility(View.VISIBLE);
                txtHistorialVacio.setVisibility(View.GONE);
            } else {
                recyclerHistorial.setVisibility(View.GONE);
                txtHistorialVacio.setVisibility(View.VISIBLE);
            }
        });

        seguimientoViewModel.listarSeguimientosPorUsuario(this);
    }
}
