package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.Recordatorio;
import com.sise.mishabitos.repositories.RecordatorioRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class RecordatorioViewModel extends ViewModel {

    private MutableLiveData<LiveDataResponse<List<Recordatorio>>> listarRecordatorioLiveData;
    private MutableLiveData<LiveDataResponse<String>> insertarRecordatorioLiveData;
    private MutableLiveData<LiveDataResponse<String>> actualizarRecordatorioLiveData;
    private MutableLiveData<LiveDataResponse<String>> eliminarRecordatorioLiveData;
    private RecordatorioRepository recordatorioRepository;

    public RecordatorioViewModel() {
        listarRecordatorioLiveData = new MutableLiveData<>();
        insertarRecordatorioLiveData = new MutableLiveData<>();
        actualizarRecordatorioLiveData = new MutableLiveData<>();
        eliminarRecordatorioLiveData = new MutableLiveData<>();
        recordatorioRepository = new RecordatorioRepository();
    }

    public LiveData<LiveDataResponse<List<Recordatorio>>> getListarRecordatorioLiveData() {
        return listarRecordatorioLiveData;
    }

    public LiveData<LiveDataResponse<String>> getInsertarRecordatorioLiveData() {
        return insertarRecordatorioLiveData;
    }

    public LiveData<LiveDataResponse<String>> getActualizarRecordatorioLiveData() {
        return actualizarRecordatorioLiveData;
    }

    public LiveData<LiveDataResponse<String>> getEliminarRecordatorioLiveData() {
        return eliminarRecordatorioLiveData;
    }

    public void listarRecordatorioPorHabito(Context context, int idHabito) {
        recordatorioRepository.listarRecordatorioPorHabito(context, idHabito, new Callback<List<Recordatorio>>() {
            @Override
            public void onSuccess(List<Recordatorio> result) {
                listarRecordatorioLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarRecordatorioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void insertarRecordatorio(Context context, Recordatorio recordatorio) {
        recordatorioRepository.insertarRecordatorio(context, recordatorio, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarRecordatorioLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                insertarRecordatorioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void actualizarRecordatorio(Context context, Recordatorio recordatorio) {
        recordatorioRepository.actualizarRecordatorio(context, recordatorio, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                actualizarRecordatorioLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                actualizarRecordatorioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void eliminarRecordatorio(Context context, int idRecordatorio) {
        recordatorioRepository.eliminarRecordatorio(context, idRecordatorio, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarRecordatorioLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                eliminarRecordatorioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }
}
