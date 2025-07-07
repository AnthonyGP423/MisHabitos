package com.sise.mishabitos.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.shared.SharedPreferencesManager;
import com.sise.mishabitos.viewmodel.UsuarioViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText edtCorreo, edtNombres, edtApellidoPaterno, edtApellidoMaterno, edtFechaNac;
    private Button btnEditar, btnGuardar, btnCerrarSesion;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        inicializarUI();
        inicializarViewModel();
        cargarDatosUsuario();

        btnEditar.setOnClickListener(v -> habilitarEdicion(true));

        btnGuardar.setOnClickListener(v -> guardarCambios());

        btnCerrarSesion.setOnClickListener(v -> mostrarDialogCerrarSesion());

        configurarSelectorFecha();
    }

    private void inicializarUI() {
        edtCorreo = findViewById(R.id.input_correo);
        edtNombres = findViewById(R.id.input_nombre);
        edtApellidoPaterno = findViewById(R.id.input_apellido_paterno);
        edtApellidoMaterno = findViewById(R.id.input_apellido_materno);
        edtFechaNac = findViewById(R.id.input_fecha_nacimiento);

        btnEditar = findViewById(R.id.btnEditarPerfil);
        btnGuardar = findViewById(R.id.btnGuardarPerfil);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        habilitarEdicion(false);
    }

    private void inicializarViewModel() {
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getListarUsuariosLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                int idUsuario = SharedPreferencesManager.getInstance(this).getUserId();
                for (Usuario u : response.getData()) {
                    if (u.getIdUsuario() == idUsuario) {
                        usuarioActual = u;
                        mostrarDatos();
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "No se pudo cargar usuario", Toast.LENGTH_SHORT).show();
            }
        });

        usuarioViewModel.getActualizarUsuarioLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                habilitarEdicion(false);
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDatosUsuario() {
        usuarioViewModel.listarUsuarios(this);
    }

    private void mostrarDatos() {
        if (usuarioActual != null) {
            edtCorreo.setText(usuarioActual.getCorreo());
            edtNombres.setText(usuarioActual.getNombre());
            edtApellidoPaterno.setText(usuarioActual.getApellidoPaterno());
            edtApellidoMaterno.setText(usuarioActual.getApellidoMaterno());

            if (usuarioActual.getFechaNacimiento() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = sdf.format(usuarioActual.getFechaNacimiento());
                edtFechaNac.setText(fechaFormateada);
            }
        }
    }

    private void habilitarEdicion(boolean habilitar) {
        edtNombres.setEnabled(habilitar);
        edtApellidoPaterno.setEnabled(habilitar);
        edtApellidoMaterno.setEnabled(habilitar);
        edtFechaNac.setEnabled(habilitar);
        btnGuardar.setEnabled(habilitar);
    }

    private void guardarCambios() {
        usuarioActual.setNombre(edtNombres.getText().toString().trim());
        usuarioActual.setApellidoPaterno(edtApellidoPaterno.getText().toString().trim());
        usuarioActual.setApellidoMaterno(edtApellidoMaterno.getText().toString().trim());

        String fechaStr = edtFechaNac.getText().toString().trim();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(fechaStr);
            usuarioActual.setFechaNacimiento(fechaNacimiento);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Formato de fecha inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        usuarioViewModel.actualizarUsuario(this, usuarioActual);
    }

    private void configurarSelectorFecha() {
        edtFechaNac.setOnClickListener(v -> mostrarDatePicker());
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        String fechaActualStr = edtFechaNac.getText().toString().trim();
        if (!fechaActualStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(fechaActualStr);
                calendar.setTime(date);
            } catch (Exception ignored) {}
        }

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    edtFechaNac.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );

        datePickerDialog.show();
    }

    private void mostrarDialogCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> cerrarSesion())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void cerrarSesion() {
        SharedPreferencesManager.getInstance(this).clearSession();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
