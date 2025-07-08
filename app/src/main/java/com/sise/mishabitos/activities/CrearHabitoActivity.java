package com.sise.mishabitos.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Categoria;
import com.sise.mishabitos.entities.Habito;
import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.repositories.CategoriaRepository;
import com.sise.mishabitos.repositories.HabitoRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CrearHabitoActivity extends AppCompatActivity {
    private int idCategoriaSeleccionada = -1;  // "ninguna seleccionada"
    private Spinner spCategoria;
    private Button btnAgregarTarea, btnCancelarTarea;
    private EditText txtNombreHabito, txtDetallesHabito;
    private TimePicker timePicker;
    private Switch swAlarma;
    private CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo;

    private List<Categoria> listaCategorias = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_habito);

        View rootView = findViewById(R.id.drawer_layout);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Inicializar views
        spCategoria = findViewById(R.id.spCategoria);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);
        btnCancelarTarea = findViewById(R.id.btnCancelarTarea);
        txtNombreHabito = findViewById(R.id.txtNombreHabito);
        txtDetallesHabito = findViewById(R.id.txtDetallesHabito);
        timePicker = findViewById(R.id.horadelatarea);
        timePicker.setHour(9);
        timePicker.setMinute(0);
        swAlarma = findViewById(R.id.swAlarma);

        cbLunes = findViewById(R.id.cbLunes);
        cbMartes = findViewById(R.id.cbMartes);
        cbMiercoles = findViewById(R.id.cbMiercoles);
        cbJueves = findViewById(R.id.cbJueves);
        cbViernes = findViewById(R.id.cbViernes);
        cbSabado = findViewById(R.id.cbSabado);
        cbDomingo = findViewById(R.id.cbDomingo);

        btnAgregarTarea.setEnabled(false);

        btnCancelarTarea.setOnClickListener(v -> finish());

        txtNombreHabito.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { validarFormulario(); }
            @Override public void afterTextChanged(Editable s) { }
        });

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { validarFormulario();
                // actualizamos la categoría seleccionada
                if (position >= 0 && position < listaCategorias.size()) {
                    idCategoriaSeleccionada = listaCategorias.get(position).getIdCategoria();
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnAgregarTarea.setOnClickListener(v -> guardarHabito());

        cargarCategorias();
    }

    private void validarFormulario() {
        String nombre = txtNombreHabito.getText().toString().trim();
        boolean categoriaSeleccionada = spCategoria.getSelectedItemPosition() != AdapterView.INVALID_POSITION;
        btnAgregarTarea.setEnabled(!nombre.isEmpty() && categoriaSeleccionada);
    }

    private void cargarCategorias() {
        CategoriaRepository categoriaRepo = new CategoriaRepository();

        categoriaRepo.listarCategorias(this, new Callback<List<Categoria>>() {
            @Override
            public void onSuccess(List<Categoria> categorias) {
                listaCategorias = categorias;
                List<String> nombresCategorias = new ArrayList<>();
                for (Categoria cat : categorias) {
                    nombresCategorias.add(cat.getNombre());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CrearHabitoActivity.this, android.R.layout.simple_spinner_item, nombresCategorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategoria.setAdapter(adapter);

                validarFormulario();
            }

            @Override
            public void onFailure() {
                Toast.makeText(CrearHabitoActivity.this, "No se pudieron cargar las categorías 😢", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarHabito() {
        String nombre = txtNombreHabito.getText().toString().trim();
        String descripcion = txtDetallesHabito.getText().toString().trim();

        int hora = timePicker.getHour();
        int minuto = timePicker.getMinute();
        String horaFormateada = String.format(Locale.getDefault(), "%02d:%02d:00", hora, minuto);

        int idUsuario = SharedPreferencesManager.getInstance(this).getUserId();

        Habito habito = new Habito();
        habito.setNombre(nombre);
        habito.setDescripcion(descripcion);
        habito.setHoraSugerida(horaFormateada);
        habito.setFechaCreacion(new Date());
        habito.setEstadoAuditoria(true);

        //Asignar usuario COMPLETO
        Usuario usuario = new Usuario(idUsuario);
        habito.setUsuario(usuario);

        // Asignar categoría COMPLETA
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoriaSeleccionada);
        habito.setCategoria(categoria);

        HabitoRepository repo = new HabitoRepository();

        // Debug opcional
        String jsonDebug = new com.google.gson.Gson().toJson(habito);
        System.out.println("DEBUG HABITO JSON: " + jsonDebug);

        repo.insertarHabito(this, habito, new Callback<String>() {
            @Override
            public void onSuccess(String mensaje) {
                Toast.makeText(CrearHabitoActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure() {
                Toast.makeText(CrearHabitoActivity.this, "Error al guardar hábito ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
