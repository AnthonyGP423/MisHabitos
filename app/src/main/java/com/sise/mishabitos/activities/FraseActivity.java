package com.sise.mishabitos.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sise.mishabitos.R;
import com.sise.mishabitos.viewmodel.FraseMotivacionalViewModel;

public class FraseActivity extends AppCompatActivity {

    private FraseMotivacionalViewModel viewModel;
    private LinearLayout layoutFrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutFrases = findViewById(R.id.layout_frases);
        Button btnOtra = findViewById(R.id.btn_otra_frase);

        viewModel = new ViewModelProvider(this).get(FraseMotivacionalViewModel.class);

        viewModel.getFraseDelDiaLiveData().observe(this, texto -> {
            agregarFraseALista(texto.toString());
        });

        // Carga inicial
        viewModel.listarFrases(this);

        // Botón
        btnOtra.setOnClickListener(v -> viewModel.listarFrases(this));
    }

    private void agregarFraseALista(String texto) {
        TextView nuevaFrase = new TextView(this);
        nuevaFrase.setText("• " + texto);
        nuevaFrase.setTextSize(16);
        nuevaFrase.setPadding(0, 10, 0, 10);
        layoutFrases.addView(nuevaFrase);
    }
}
