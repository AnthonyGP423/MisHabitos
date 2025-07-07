package com.sise.mishabitos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.viewmodel.UsuarioViewModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputNombre, inputApellidoPaterno, inputApellidoMaterno, inputCorreo, inputPassword, inputFechaNacimiento;
    private Button btnRegistrar, btnCancelar;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNombre = findViewById(R.id.input_nombre);
        inputApellidoPaterno = findViewById(R.id.input_apellido_paterno);
        inputApellidoMaterno = findViewById(R.id.input_apellido_materno);
        inputCorreo = findViewById(R.id.input_correo);
        inputPassword = findViewById(R.id.input_password);
        inputFechaNacimiento = findViewById(R.id.input_fecha_nacimiento);

        btnRegistrar = findViewById(R.id.button_register);
        btnCancelar = findViewById(R.id.button_cancelar);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
        btnCancelar.setOnClickListener(v -> finish());

        observarResultados();
    }

    private void registrarUsuario() {
        String nombre = inputNombre.getText().toString().trim();
        String apellidoPaterno = inputApellidoPaterno.getText().toString().trim();
        String apellidoMaterno = inputApellidoMaterno.getText().toString().trim();
        String correo = inputCorreo.getText().toString().trim();
        String contrasena = inputPassword.getText().toString().trim();
        String fechaNacimiento = inputFechaNacimiento.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidoPaterno(apellidoPaterno);
        nuevoUsuario.setApellidoMaterno(apellidoMaterno);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setFechaNacimiento(fechaNacimiento);

        usuarioViewModel.insertarUsuario(this, nuevoUsuario);
    }

    private void observarResultados() {
        usuarioViewModel.getInsertarUsuarioLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                String mensaje = response.getMessage();
                if (mensaje != null && !mensaje.isEmpty()) {
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                } else {
                    inputCorreo.setError(mensaje);
                }
            }
        });
    }

}
