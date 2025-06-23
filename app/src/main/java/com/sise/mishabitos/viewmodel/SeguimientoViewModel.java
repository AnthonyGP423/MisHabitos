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

    private MutableLiveData<LiveDataResponse<List<Seguimiento>>> listarSeguimientosLiveData;
    private MutableLiveData<LiveDataResponse<String>> insertarSeguimientoLiveData;
    private MutableLiveData<LiveDataResponse<String>> actualizarSeguimientoLiveData;
    private MutableLiveData<LiveDataResponse<String>> eliminarSeguimientoLiveData;
    private SeguimientoRepository seguimientoRepository;

    public SeguimientoViewModel() {
        listarSeguimientosLiveData = new MutableLiveData<>();
        insertarSeguimientoLiveData = new MutableLiveData<>();
        actualizarSeguimientoLiveData = new MutableLiveData<>();
        eliminarSeguimientoLiveData = new MutableLiveData<>();
        seguimientoRepository = new SeguimientoRepository();
    }


    public LiveData<LiveDataResponse<List<Seguimiento>>> getListarSeguimientosLiveData() {
        return listarSeguimientosLiveData;
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

    public void listarSeguimientosPorHabito(Context context, int idHabito) {
        seguimientoRepository.listarSeguimientosPorHabito(context, idHabito, new Callback<List<Seguimiento>>() {
            @Override
            public void onSuccess(List<Seguimiento> result) {
                listarSeguimientosLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarSeguimientosLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

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
