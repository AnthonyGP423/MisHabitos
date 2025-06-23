package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.FrecuenciaHabito;
import com.sise.mishabitos.repositories.FrecuenciaHabitoRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class FrecuenciaHabitoViewModel extends ViewModel {

    private MutableLiveData<LiveDataResponse<List<FrecuenciaHabito>>> listarFrecuenciasLiveData;
    private MutableLiveData<LiveDataResponse<String>> insertarFrecuenciaLiveData;
    private MutableLiveData<LiveDataResponse<String>> eliminarFrecuenciaLiveData;

    private FrecuenciaHabitoRepository frecuenciaRepository;

    public FrecuenciaHabitoViewModel() {
        listarFrecuenciasLiveData = new MutableLiveData<>();
        insertarFrecuenciaLiveData = new MutableLiveData<>();
        eliminarFrecuenciaLiveData = new MutableLiveData<>();
        frecuenciaRepository = new FrecuenciaHabitoRepository();
    }

    public LiveData<LiveDataResponse<List<FrecuenciaHabito>>> getListarFrecuenciasLiveData() {
        return listarFrecuenciasLiveData;
    }

    public LiveData<LiveDataResponse<String>> getInsertarFrecuenciaLiveData() {
        return insertarFrecuenciaLiveData;
    }

    public LiveData<LiveDataResponse<String>> getEliminarFrecuenciaLiveData() {
        return eliminarFrecuenciaLiveData;
    }

    public void listarPorHabito(Context context, int idHabito) {
        frecuenciaRepository.listarFrecuenciaPorHabito(context, idHabito, new Callback<List<FrecuenciaHabito>>() {
            @Override
            public void onSuccess(List<FrecuenciaHabito> result) {
                listarFrecuenciasLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarFrecuenciasLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void insertar(Context context, FrecuenciaHabito frecuencia) {
        frecuenciaRepository.insertarFrecuencia(context, frecuencia, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarFrecuenciaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                insertarFrecuenciaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void eliminar(Context context, int idFrecuencia) {
        frecuenciaRepository.eliminarFrecuencia(context, idFrecuencia, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarFrecuenciaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                eliminarFrecuenciaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }
}
