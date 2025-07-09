package com.sise.mishabitos.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sise.mishabitos.R;
import com.sise.mishabitos.shared.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombreInput, apellidoPaternoInput, apellidoMaternoInput, correoInput, passwordInput, fechaNacimientoInput;
    private Button registerButton;

    private final Calendar calendario = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombreInput = findViewById(R.id.input_nombre);
        apellidoPaternoInput = findViewById(R.id.input_apellido_paterno);
        apellidoMaternoInput = findViewById(R.id.input_apellido_materno);
        correoInput = findViewById(R.id.input_correo);
        passwordInput = findViewById(R.id.input_password);
        fechaNacimientoInput = findViewById(R.id.input_fecha_nacimiento);
        registerButton = findViewById(R.id.button_register);

        // Abrir calendario al tocar el campo fecha
        fechaNacimientoInput.setFocusable(false);
        fechaNacimientoInput.setOnClickListener(v -> mostrarDatePicker());

        // Al hacer clic en Registrar
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void mostrarDatePicker() {
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            calendario.set(year1, month1, dayOfMonth);
            String fechaSeleccionada = dateFormat.format(calendario.getTime());
            fechaNacimientoInput.setText(fechaSeleccionada);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void registerUser() {
        String nombre = nombreInput.getText().toString().trim();
        String apellidoPaterno = apellidoPaternoInput.getText().toString().trim();
        String apellidoMaterno = apellidoMaternoInput.getText().toString().trim();
        String correo = correoInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String fechaNacimiento = fechaNacimientoInput.getText().toString().trim();

        // 🔍 Validación básica
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || fechaNacimiento.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Constants.BASE_URL_API + "/usuarios"; // ✅ Asegúrate que esta URL esté correcta

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Registro exitoso 🎉", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error al registrar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("nombre", nombre);
                    jsonBody.put("apellidoPaterno", apellidoPaterno);
                    jsonBody.put("apellidoMaterno", apellidoMaterno);
                    jsonBody.put("correo", correo);
                    jsonBody.put("contrasena", password);
                    jsonBody.put("fechaNacimiento", fechaNacimiento);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
