package com.sise.mishabitos.activities;

import android.os.Bundle;
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
import com.sise.mishabitos.shared.LiveDataResponse;
import com.sise.mishabitos.viewmodel.CategoriaViewModel;
import com.sise.mishabitos.viewmodel.FrecuenciaHabitoViewModel;
import com.sise.mishabitos.viewmodel.HabitoViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditarHabitoActivity extends AppCompatActivity {

    private EditText edtNombre, edtDescripcion;
    private Spinner spCategoria;
    private TimePicker timePicker;
    private Switch swAlarma;
    private CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes, cbSabado, cbDomingo;
    private Button btnGuardar, btnEliminar;

    private Habito habitoActual;
    private CategoriaViewModel categoriaViewModel;
    private FrecuenciaHabitoViewModel frecuenciaHabitoViewModel;
    private HabitoViewModel habitoViewModel;

    private List<Categoria> listaCategorias = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_habito);

        inicializarUI();
        inicializarViewModels();

        habitoActual = (Habito) getIntent().getSerializableExtra("habito");
        if (habitoActual == null) {
            Toast.makeText(this, "Error: Hábito no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        observarLiveData();  // 👈 Nuevo
        cargarDatosHabito();
        cargarCategorias();
        cargarFrecuencias();
    }

    private void inicializarUI() {
        edtNombre = findViewById(R.id.edtNombreHabito);
        edtDescripcion = findViewById(R.id.edtDescripcionHabito);
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

        btnGuardar = findViewById(R.id.btnGuardarCambios);
        btnEliminar = findViewById(R.id.btnEliminarHabito);

        btnGuardar.setOnClickListener(v -> guardarCambios());
        btnEliminar.setOnClickListener(v -> eliminarHabito());
    }

    private void inicializarViewModels() {
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);
        frecuenciaHabitoViewModel = new ViewModelProvider(this).get(FrecuenciaHabitoViewModel.class);
        habitoViewModel = new ViewModelProvider(this).get(HabitoViewModel.class);
    }

    private void observarLiveData() {
        habitoViewModel.getActualizarHabitoLiveData().observe(this, response -> {
            if (response.getStatus() == LiveDataResponse.Status.SUCCESS) {
                guardarFrecuencias();
                Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar hábito", Toast.LENGTH_SHORT).show();
            }
        });

        frecuenciaHabitoViewModel.getListarFrecuenciasLiveData().observe(this, response -> {
            if (response.getStatus() == LiveDataResponse.Status.SUCCESS) {
                for (FrecuenciaHabito f : response.getData()) {
                    frecuenciaHabitoViewModel.eliminar(this, f.getIdFrecuencia());
                }

                for (String dia : obtenerDiasSeleccionados()) {
                    FrecuenciaHabito nueva = new FrecuenciaHabito();
                    nueva.setIdHabito(habitoActual.getIdHabito());
                    nueva.setDiaSemana(dia);
                    frecuenciaHabitoViewModel.insertar(this, nueva);
                }
            }
        });

        categoriaViewModel.getListarCategoriasLiveData().observe(this, response -> {
            if (response.getStatus() == LiveDataResponse.Status.SUCCESS) {
                listaCategorias = response.getData();
                List<String> nombres = new ArrayList<>();
                for (Categoria c : listaCategorias) {
                    nombres.add(c.getNombre());
                }

                spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategoria.setAdapter(spinnerAdapter);

                for (int i = 0; i < listaCategorias.size(); i++) {
                    if (listaCategorias.get(i).getIdCategoria() == habitoActual.getIdCategoria()) {
                        spCategoria.setSelection(i);
                        break;
                    }
                }
            }
        });

        frecuenciaHabitoViewModel.getListarFrecuenciasLiveData().observe(this, response -> {
            if (response.getStatus() == LiveDataResponse.Status.SUCCESS) {
                for (FrecuenciaHabito f : response.getData()) {
                    marcarCheckBox(f.getDiaSemana());
                }
            }
        });
    }

    private void cargarDatosHabito() {
        edtNombre.setText(habitoActual.getNombre());
        edtDescripcion.setText(habitoActual.getDescripcion());

        String hora = habitoActual.getHoraSugerida();
        if (hora != null && hora.length() >= 5) {
            int hour = Integer.parseInt(hora.substring(0, 2));
            int minute = Integer.parseInt(hora.substring(3, 5));
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }
    }

    private void cargarCategorias() {
        categoriaViewModel.listarCategorias(this);
    }

    private void cargarFrecuencias() {
        frecuenciaHabitoViewModel.listarPorHabito(this, habitoActual.getIdHabito());
    }

    private void guardarCambios() {
        Habito h = new Habito();
        h.setIdHabito(habitoActual.getIdHabito());
        h.setNombre(edtNombre.getText().toString().trim());
        h.setDescripcion(edtDescripcion.getText().toString().trim());
        h.setIdCategoria(listaCategorias.get(spCategoria.getSelectedItemPosition()).getIdCategoria());
        h.setHoraSugerida(String.format("%02d:%02d:00", timePicker.getHour(), timePicker.getMinute()));

        habitoViewModel.actualizarHabito(this, h);
    }

    private void guardarFrecuencias() {
        frecuenciaHabitoViewModel.listarPorHabito(this, habitoActual.getIdHabito());
    }

    private void eliminarHabito() {
        habitoViewModel.eliminarHabito(this, habitoActual.getIdHabito());
        Toast.makeText(this, "Hábito eliminado", Toast.LENGTH_SHORT).show();
        finish();
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

    private void marcarCheckBox(String dia) {
        switch (dia) {
            case "Lunes": cbLunes.setChecked(true); break;
            case "Martes": cbMartes.setChecked(true); break;
            case "Miércoles": cbMiercoles.setChecked(true); break;
            case "Jueves": cbJueves.setChecked(true); break;
            case "Viernes": cbViernes.setChecked(true); break;
            case "Sábado": cbSabado.setChecked(true); break;
            case "Domingo": cbDomingo.setChecked(true); break;
        }
    }
}
