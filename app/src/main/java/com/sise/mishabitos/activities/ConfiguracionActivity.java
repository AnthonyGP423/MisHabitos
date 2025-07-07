package com.sise.mishabitos.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sise.mishabitos.R;
import com.sise.mishabitos.shared.SharedPreferencesManager;

public class ConfiguracionActivity extends AppCompatActivity {

    private Switch swNotificaciones, swModoOscuro;
    private Button btnGuardarConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        // Inicializar UI
        swNotificaciones = findViewById(R.id.swNotificaciones);
        swModoOscuro = findViewById(R.id.swModoOscuro);
        btnGuardarConfig = findViewById(R.id.btnGuardarConfig);

        // Cargar estado guardado
        SharedPreferencesManager sp = SharedPreferencesManager.getInstance(this);
        swNotificaciones.setChecked(sp.getNotificaciones());
        swModoOscuro.setChecked(sp.getModoOscuro());

        // Botón guardar
        btnGuardarConfig.setOnClickListener(v -> {
            sp.saveNotificaciones(swNotificaciones.isChecked());
            sp.saveModoOscuro(swModoOscuro.isChecked());
            Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
