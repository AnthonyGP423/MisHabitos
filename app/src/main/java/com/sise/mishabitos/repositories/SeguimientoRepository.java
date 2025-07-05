package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.Seguimiento;
import com.sise.mishabitos.repositories.SeguimientoRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class SeguimientoViewModel extends ViewModel {

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

    // 🔵 LISTAR POR HÁBITO
    public void listarSeguimientosPorHabito(Context context, int idHabito) {
        seguimientoRepository.listarSeguimientosPorHabito(context, idHabito, new Callback<List<Seguimiento>>() {
            @Override
            public void onSuccess(List<Seguimiento> result) {
                listarSeguimientosPorHabitoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarSeguimientosPorHabitoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    // 🔵 LISTAR POR USUARIO Y FECHA (usa SharedPreferencesManager)
    public void listarSeguimientosPorUsuarioYFecha(Context context, String fecha) {
        seguimientoRepository.listarSeguimientosPorUsuarioYFecha(context, fecha, new Callback<List<Seguimiento>>() {
            @Override
            public void onSuccess(List<Seguimiento> result) {
                listarSeguimientosPorUsuarioFechaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarSeguimientosPorUsuarioFechaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    // 🔵 INSERTAR
    public void insertarSeguimiento(Context context, Seguimiento seguimiento) {
        seguimientoRepository.insertarSeguimiento(context, seguimiento, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarSeguimientoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                insertarSeguimientoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    // 🔵 ACTUALIZAR
    public void actualizarSeguimiento(Context context, Seguimiento seguimiento) {
        seguimientoRepository.actualizarSeguimiento(context, seguimiento, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                actualizarSeguimientoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                actualizarSeguimientoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    // 🔵 ELIMINAR
    public void eliminarSeguimiento(Context context, int idSeguimiento) {
        seguimientoRepository.eliminarSeguimiento(context, idSeguimiento, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarSeguimientoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                eliminarSeguimientoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

}
