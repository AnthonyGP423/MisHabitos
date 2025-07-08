package com.sise.mishabitos.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.Seguimiento;
import com.sise.mishabitos.repositories.SeguimientoRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class SeguimientoViewModel extends ViewModel {

    private static final String TAG = "SeguimientoViewModel";

    private final MutableLiveData<LiveDataResponse<List<Seguimiento>>> listarSeguimientosPorHabitoLiveData;
    private final MutableLiveData<LiveDataResponse<List<Seguimiento>>> listarSeguimientosPorUsuarioFechaLiveData;
    private final MutableLiveData<LiveDataResponse<String>> insertarSeguimientoLiveData;
    private final MutableLiveData<LiveDataResponse<String>> actualizarSeguimientoLiveData;
    private final MutableLiveData<LiveDataResponse<String>> eliminarSeguimientoLiveData;

    private final SeguimientoRepository seguimientoRepository;

    public SeguimientoViewModel() {
        listarSeguimientosPorHabitoLiveData = new MutableLiveData<>();
        listarSeguimientosPorUsuarioFechaLiveData = new MutableLiveData<>();
        insertarSeguimientoLiveData = new MutableLiveData<>();
        actualizarSeguimientoLiveData = new MutableLiveData<>();
        eliminarSeguimientoLiveData = new MutableLiveData<>();
        seguimientoRepository = new SeguimientoRepository();
    }

    public LiveData<LiveDataResponse<List<Seguimiento>>> getListarSeguimientosPorHabitoLiveData() {
        return listarSeguimientosPorHabitoLiveData;
    }

    public LiveData<LiveDataResponse<List<Seguimiento>>> getListarSeguimientosPorUsuarioFechaLiveData() {
        return listarSeguimientosPorUsuarioFechaLiveData;
    }

    public LiveData<LiveDataResponse<String>> getInsertarSeguimientoLiveData() {
        return insertarSeguimientoLiveData;
    }

    public LiveData<LiveDataResponse<String>> getActualizarSeguimientoLiveData() {
        return actualizarSeguimientoLiveData;
    }

    public LiveData<LiveDataResponse<String>> getEliminarSeguimientoLiveData() {
        return eliminarSeguimientoLiveData;
    }

    /**
     * Listar seguimientos por Hábito (opcional)
     */
    public void listarSeguimientosPorHabito(Context context, int idHabito) {
        Log.d(TAG, "listando seguimientos por hábito: " + idHabito);
        seguimientoRepository.listarSeguimientosPorHabito(context, idHabito, new Callback<List<Seguimiento>>() {
            @Override
            public void onSuccess(List<Seguimiento> result) {
                Log.d(TAG, "Éxito al obtener seguimientos por hábito. Total: " + (result != null ? result.size() : 0));
                listarSeguimientosPorHabitoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "Error al obtener seguimientos por hábito.");
                listarSeguimientosPorHabitoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    /**
     * Listar seguimientos por Usuario + Fecha
     */
    public void listarSeguimientosPorUsuarioYFecha(Context context, int idUsuario, String fecha) {
        Log.d(TAG, "Listando seguimientos para usuario " + idUsuario + " en fecha " + fecha);
        seguimientoRepository.listarSeguimientosPorUsuarioYFecha(context, idUsuario, fecha, new Callback<List<Seguimiento>>() {
            @Override
            public void onSuccess(List<Seguimiento> result) {
                Log.d(TAG, "Éxito: Seguimientos obtenidos: " + (result != null ? result.size() : 0));
                listarSeguimientosPorUsuarioFechaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "Error al obtener seguimientos por usuario y fecha.");
                listarSeguimientosPorUsuarioFechaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    private final MutableLiveData<LiveDataResponse<List<Seguimiento>>> seguimientosCompletadosLiveData = new MutableLiveData<>();

    public LiveData<LiveDataResponse<List<Seguimiento>>> getSeguimientosCompletadosLiveData() {
        return seguimientosCompletadosLiveData;
    }

    public void listarSeguimientosCompletadosPorUsuario(Context context, int idUsuario) {
        seguimientoRepository.listarSeguimientosCompletadosPorUsuario(context, idUsuario, new Callback<List<Seguimiento>>() {
            @Override
            public void onSuccess(List<Seguimiento> result) {
                seguimientosCompletadosLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                seguimientosCompletadosLiveData.postValue(LiveDataResponse.error());
            }
        });
    }


    /**
     * Insertar nuevo seguimiento
     */
    public void insertarSeguimiento(Context context, Seguimiento seguimiento) {
        Log.d(TAG, "Insertando seguimiento: " + seguimiento.toString());
        seguimientoRepository.insertarSeguimiento(context, seguimiento, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "Seguimiento insertado exitosamente: " + result);
                insertarSeguimientoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "Error al insertar seguimiento.");
                insertarSeguimientoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    /**
     * ✅ Actualizar seguimiento existente
     */
    public void actualizarSeguimiento(Context context, Seguimiento seguimiento) {
        Log.d(TAG, "Actualizando seguimiento: " + seguimiento.getIdSeguimiento());
        seguimientoRepository.actualizarSeguimiento(context, seguimiento, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "Seguimiento actualizado: " + result);
                actualizarSeguimientoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "Error al actualizar seguimiento.");
                actualizarSeguimientoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    /**
     * Eliminar seguimiento
     */
    public void eliminarSeguimiento(Context context, int idSeguimiento) {
        Log.d(TAG, "Eliminando seguimiento: ID=" + idSeguimiento);
        seguimientoRepository.eliminarSeguimiento(context, idSeguimiento, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "Seguimiento eliminado: " + result);
                eliminarSeguimientoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                Log.e(TAG, "Error al eliminar seguimiento.");
                eliminarSeguimientoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }
}
