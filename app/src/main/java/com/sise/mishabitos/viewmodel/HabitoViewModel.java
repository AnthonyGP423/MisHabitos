package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.Habito;
import com.sise.mishabitos.repositories.HabitoRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class HabitoViewModel extends ViewModel {

    private MutableLiveData<LiveDataResponse<List<Habito>>> listarHabitosLiveData;
    private MutableLiveData<LiveDataResponse<String>> insertarHabitoLiveData;
    private MutableLiveData<LiveDataResponse<String>> actualizarHabitoLiveData;
    private MutableLiveData<LiveDataResponse<String>> eliminarHabitoLiveData;
    private HabitoRepository habitoRepository;

    public HabitoViewModel() {
        listarHabitosLiveData = new MutableLiveData<>();
        insertarHabitoLiveData = new MutableLiveData<>();
        actualizarHabitoLiveData = new MutableLiveData<>();
        eliminarHabitoLiveData = new MutableLiveData<>();
        habitoRepository = new HabitoRepository();
    }

    public LiveData<LiveDataResponse<List<Habito>>> getListarHabitosLiveData() {
        return listarHabitosLiveData;
    }

    public LiveData<LiveDataResponse<String>> getInsertarHabitoLiveData() {
        return insertarHabitoLiveData;
    }
    public LiveData<LiveDataResponse<String>> getActualizarHabitoLiveData() {
        return actualizarHabitoLiveData;
    }

    public LiveData<LiveDataResponse<String>> getEliminarHabitoLiveData() {
        return eliminarHabitoLiveData;
    }

    public void listarHabitosPorUsuario(Context context, int userId) {
        habitoRepository.listarHabitosPorUsuario(context, userId, new Callback<List<Habito>>() {
            @Override
            public void onSuccess(List<Habito> result) {
                listarHabitosLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarHabitosLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void insertarHabito(Context context, Habito habito) {
        habitoRepository.insertarHabito(context, habito, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarHabitoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                insertarHabitoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void actualizarHabito(Context context, Habito habito) {
        habitoRepository.actualizarHabito(context, habito, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                actualizarHabitoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                actualizarHabitoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void eliminarHabito(Context context, int idHabito) {
        habitoRepository.eliminarHabito(context, idHabito, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarHabitoLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                eliminarHabitoLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

}
