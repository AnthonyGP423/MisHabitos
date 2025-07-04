package com.sise.mishabitos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
    }

    private void registerUser() {
        String nombre = nombreInput.getText().toString().trim();
        String correo = correoInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        String url = "http://TU_IP_LOCAL:PUERTO/usuario"; // Cambia IP y puerto

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
}
