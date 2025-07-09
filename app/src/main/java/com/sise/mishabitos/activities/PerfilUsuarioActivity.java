package com.sise.mishabitos.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.repositories.UsuarioRepository;
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText inputNombre, inputApellidoPaterno, inputApellidoMaterno, inputCorreo, inputFechaNacimiento;
    private Button btnEditarPerfil;
    private boolean modoEdicion = false;

    private SharedPreferencesManager sp;
    private UsuarioRepository usuarioRepository;
    private int idUsuario;

    private final Calendar calendario = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        sp = SharedPreferencesManager.getInstance(this);
        usuarioRepository = new UsuarioRepository();
        idUsuario = sp.getUserId();

        inicializarUI();
        cargarDatosUsuario();
        obtenerDatosUsuarioDesdeServidor();
        configurarBotonEditar();
        configurarDatePicker();
    }

    private void inicializarUI() {
        inputNombre = findViewById(R.id.input_nombre);
        inputApellidoPaterno = findViewById(R.id.input_apellido_paterno);
        inputApellidoMaterno = findViewById(R.id.input_apellido_materno);
        inputCorreo = findViewById(R.id.input_correo);
        inputFechaNacimiento = findViewById(R.id.input_fecha_nacimiento);
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil);

        Button btnVolverInicio = findViewById(R.id.btnVolverInicio);
        btnVolverInicio.setOnClickListener(v -> finish());
    }

    private void cargarDatosUsuario() {
        inputNombre.setText(sp.getUserName());
        inputApellidoPaterno.setText(sp.getApellidoPaterno());
        inputApellidoMaterno.setText(sp.getApellidoMaterno());
        inputCorreo.setText(sp.getUserEmail());
        inputFechaNacimiento.setText(sp.getFechaNacimiento());
        deshabilitarCampos();
    }

    private void obtenerDatosUsuarioDesdeServidor() {
        usuarioRepository.obtenerUsuarioPorId(this, idUsuario, new com.sise.mishabitos.shared.Callback<Usuario>() {
            @Override
            public void onSuccess(Usuario usuario) {
                if (usuario != null) {
                    sp.saveUserName(usuario.getNombre() != null ? usuario.getNombre() : "");
                    sp.saveApellidoPaterno(usuario.getApellidoPaterno() != null ? usuario.getApellidoPaterno() : "");
                    sp.saveApellidoMaterno(usuario.getApellidoMaterno() != null ? usuario.getApellidoMaterno() : "");
                    sp.saveUserEmail(usuario.getCorreo() != null ? usuario.getCorreo() : "");
                    sp.saveFechaNacimiento(usuario.getFechaNacimiento() != null ? dateFormat.format(usuario.getFechaNacimiento()) : "");

                    cargarDatosUsuario();
                    Toast.makeText(PerfilUsuarioActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(PerfilUsuarioActivity.this, "Error al obtener datos actualizados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configurarBotonEditar() {
        btnEditarPerfil.setOnClickListener(v -> {
            if (modoEdicion) {
                guardarCambios();
                deshabilitarCampos();
                btnEditarPerfil.setText("Editar datos");
            } else {
                habilitarCampos();
                btnEditarPerfil.setText("Guardar cambios");
            }
            modoEdicion = !modoEdicion;
        });
    }

    private void configurarDatePicker() {
        inputFechaNacimiento.setOnClickListener(v -> {
            if (!inputFechaNacimiento.isEnabled()) return;

            int year = calendario.get(Calendar.YEAR);
            int month = calendario.get(Calendar.MONTH);
            int day = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
                calendario.set(year1, month1, dayOfMonth);
                String fechaSeleccionada = dateFormat.format(calendario.getTime());
                inputFechaNacimiento.setText(fechaSeleccionada);
            }, year, month, day);

            datePickerDialog.show();
        });
    }

    private void habilitarCampos() {
        inputNombre.setEnabled(true);
        inputApellidoPaterno.setEnabled(true);
        inputApellidoMaterno.setEnabled(true);
        inputCorreo.setEnabled(true);
        inputFechaNacimiento.setEnabled(true);
    }

    private void deshabilitarCampos() {
        inputNombre.setEnabled(false);
        inputApellidoPaterno.setEnabled(false);
        inputApellidoMaterno.setEnabled(false);
        inputCorreo.setEnabled(false);
        inputFechaNacimiento.setEnabled(false);
    }

    private void guardarCambios() {
        String nombre = inputNombre.getText().toString().trim();
        String apellidoPat = inputApellidoPaterno.getText().toString().trim();
        String apellidoMat = inputApellidoMaterno.getText().toString().trim();
        String correo = inputCorreo.getText().toString().trim();
        String fecha = inputFechaNacimiento.getText().toString().trim();

        sp.saveUserName(nombre);
        sp.saveApellidoPaterno(apellidoPat);
        sp.saveApellidoMaterno(apellidoMat);
        sp.saveUserEmail(correo);
        sp.saveFechaNacimiento(fecha);

        Usuario usuarioActualizado = new Usuario(idUsuario);
        usuarioActualizado.setNombre(nombre);
        usuarioActualizado.setApellidoPaterno(apellidoPat);
        usuarioActualizado.setApellidoMaterno(apellidoMat);
        usuarioActualizado.setCorreo(correo);

        try {
            usuarioActualizado.setFechaNacimiento(dateFormat.parse(fecha));
        } catch (Exception e) {
            usuarioActualizado.setFechaNacimiento(null);
        }

        usuarioRepository.actualizarUsuario(this, usuarioActualizado, new com.sise.mishabitos.shared.Callback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(PerfilUsuarioActivity.this, "Datos actualizados en servidor", Toast.LENGTH_SHORT).show();
                obtenerDatosUsuarioDesdeServidor();  // Refrescar después de actualizar
            }

            @Override
            public void onFailure() {
                Toast.makeText(PerfilUsuarioActivity.this, "Error al actualizar en servidor", Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(this, "Enviando datos al servidor", Toast.LENGTH_SHORT).show();
    }
}
