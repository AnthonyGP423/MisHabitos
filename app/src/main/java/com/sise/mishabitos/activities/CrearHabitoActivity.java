package com.sise.mishabitos.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Categoria;
import com.sise.mishabitos.entities.FrecuenciaHabito;
import com.sise.mishabitos.entities.Habito;
import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.shared.SharedPreferencesManager;
import com.sise.mishabitos.viewmodel.CategoriaViewModel;
import com.sise.mishabitos.viewmodel.FrecuenciaHabitoViewModel;
import com.sise.mishabitos.viewmodel.HabitoViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearHabitoActivity extends AppCompatActivity {

    private EditText txtNombreHabito, txtDetallesHabito;
    private Spinner spCategoria;
    private TimePicker timePicker;
    private Switch swAlarma;
    private CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo;
    private Button btnAgregar, btnCancelar;

    private CategoriaViewModel categoriaViewModel;
    private HabitoViewModel habitoViewModel;
    private FrecuenciaHabitoViewModel frecuenciaHabitoViewModel;

    private List<Categoria> listaCategorias = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_habito);

        inicializarUI();
        inicializarViewModels();
        observarCategorias();
        cargarCategorias();
    }

    private void inicializarUI() {
        txtNombreHabito = findViewById(R.id.txtNombreHabito);
        txtDetallesHabito = findViewById(R.id.txtDetallesHabito);
        spCategoria = findViewById(R.id.spCategoria);
        timePicker = findViewById(R.id.horadelatarea);
        swAlarma = findViewById(R.id.swAlarma);

        cbLunes = findViewById(R.id.cbLunes);
        cbMartes = findViewById(R.id.cbMartes);
        cbMiercoles = findViewById(R.id.cbMiercoles);
        cbJueves = findViewById(R.id.cbJueves);
        cbViernes = findViewById(R.id.cbViernes);
        cbSabado = findViewById(R.id.cbSabado);
        cbDomingo = findViewById(R.id.cbDomingo);

        btnAgregar = findViewById(R.id.btnAgregarTarea);
        btnCancelar = findViewById(R.id.btnCancelarTarea);

        btnAgregar.setOnClickListener(v -> guardarHabito());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void inicializarViewModels() {
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
        habitoViewModel = new ViewModelProvider(this).get(HabitoViewModel.class);
        frecuenciaHabitoViewModel = new ViewModelProvider(this).get(FrecuenciaHabitoViewModel.class);

        habitoViewModel.getInsertarHabitoLiveData().observe(this, response -> {
            if (response.isSuccess()) {
                // data = idHabito devuelto del backend
                int idHabito = Integer.parseInt(response.getData());
                insertarFrecuencias(idHabito);
                Toast.makeText(this, "Hábito registrado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar hábito", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observarCategorias() {
        categoriaViewModel.getListarCategoriasLiveData().observe(this, response -> {
            if (response.getStatus() == com.sise.mishabitos.shared.LiveDataResponse.Status.SUCCESS) {
                listaCategorias = response.getData();
                List<String> nombres = new ArrayList<>();
                for (Categoria c : listaCategorias) {
                    nombres.add(c.getNombre());
                }
                spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategoria.setAdapter(spinnerAdapter);
            }
        });
    }

    private void cargarCategorias() {
        categoriaViewModel.listarCategorias(this);
    }

    private void guardarHabito() {
        String nombre = txtNombreHabito.getText().toString().trim();
        String descripcion = txtDetallesHabito.getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Habito nuevoHabito = new Habito();
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(SharedPreferencesManager.getInstance(this).getUserId());
        nuevoHabito.setUsuario(usuario);

        Categoria categoriaSeleccionada = listaCategorias.get(spCategoria.getSelectedItemPosition());
        nuevoHabito.setCategoria(categoriaSeleccionada);

        nuevoHabito.setNombre(nombre);
        nuevoHabito.setDescripcion(descripcion);

        String hora = String.format("%02d:%02d:00", timePicker.getHour(), timePicker.getMinute());
        nuevoHabito.setHoraSugerida(hora);

        nuevoHabito.setEstadoAuditoria(true);
        nuevoHabito.setFechaCreacion(new Date());

        habitoViewModel.insertarHabito(this, nuevoHabito);
    }

    private void insertarFrecuencias(int idHabito) {
        List<String> dias = obtenerDiasSeleccionados();
        for (String dia : dias) {
            FrecuenciaHabito f = new FrecuenciaHabito();
            f.setIdHabito(idHabito);
            f.setDiaSemana(dia);
            frecuenciaHabitoViewModel.insertar(this, f);
        }
    }

    private List<String> obtenerDiasSeleccionados() {
        List<String> dias = new ArrayList<>();
        if (cbLunes.isChecked()) dias.add("Lunes");
        if (cbMartes.isChecked()) dias.add("Martes");
        if (cbMiercoles.isChecked()) dias.add("Miércoles");
        if (cbJueves.isChecked()) dias.add("Jueves");
        if (cbViernes.isChecked()) dias.add("Viernes");
        if (cbSabado.isChecked()) dias.add("Sábado");
        if (cbDomingo.isChecked()) dias.add("Domingo");
        return dias;
    }
}
