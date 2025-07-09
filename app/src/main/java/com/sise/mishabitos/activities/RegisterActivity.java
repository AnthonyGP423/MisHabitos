package com.sise.mishabitos.activities;
import com.sise.mishabitos.R;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText nombreInput, correoInput, passwordInput;
    private Button registerButton;
    EditText inputFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFechaNacimiento = findViewById(R.id.input_fecha_nacimiento);

        inputFechaNacimiento.setOnClickListener(v -> mostrarSelectorFecha());

        nombreInput = findViewById(R.id.input_nombre);
        correoInput = findViewById(R.id.input_correo);
        passwordInput = findViewById(R.id.input_password);
        registerButton = findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        Button btnCancelar = findViewById(R.id.button_cancelar);

        btnCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // opcional, para que no vuelva al registro si presiona "atrás"
        });
    }

    private void registerUser() {
        String nombre = nombreInput.getText().toString().trim();
        String correo = correoInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        String url = "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                },
                error -> Toast.makeText(RegisterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("nombre", nombre);
                    jsonBody.put("correo", correo);
                    jsonBody.put("password", password);
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
    private void mostrarSelectorFecha() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
                    // Formato: dd/mm/yyyy
                    String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    inputFechaNacimiento.setText(fechaSeleccionada);
                }, anio, mes, dia);

        datePickerDialog.show();
    }
}