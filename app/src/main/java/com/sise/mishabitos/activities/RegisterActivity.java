package com.sise.mishabitos.activities;

import android.app.DatePickerDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        configurarSelectorFecha();
        observarResultados();
    }

    private void registrarUsuario() {
        String nombre = inputNombre.getText().toString().trim();
        String apellidoPaterno = inputApellidoPaterno.getText().toString().trim();
        String apellidoMaterno = inputApellidoMaterno.getText().toString().trim();
        String correo = inputCorreo.getText().toString().trim();
        String contrasena = inputPassword.getText().toString().trim();
        String fechaStr = inputFechaNacimiento.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || fechaStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Date fechaNacimiento;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaNacimiento = sdf.parse(fechaStr);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Formato de fecha inválido", Toast.LENGTH_SHORT).show();
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

    private void configurarSelectorFecha() {
        inputFechaNacimiento.setOnClickListener(v -> mostrarDatePicker());
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        String fechaActualStr = inputFechaNacimiento.getText().toString().trim();
        if (!fechaActualStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(fechaActualStr);
                calendar.setTime(date);
            } catch (Exception ignored) {
            }
        }

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    inputFechaNacimiento.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );

        datePickerDialog.show();
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
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
