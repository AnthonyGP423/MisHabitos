package com.sise.mishabitos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.sise.mishabitos.activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int DURACION_SPLASH = 2000; // Duración en milisegundos (2 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish(); // Finaliza esta activity para que no vuelva al presionar "Atrás"
        }, DURACION_SPLASH);
    }
}
