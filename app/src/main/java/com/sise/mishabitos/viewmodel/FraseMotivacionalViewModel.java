package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.FraseMotivacional;
import com.sise.mishabitos.repositories.FraseMotivacionalRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class FraseMotivacionalViewModel extends ViewModel {

    private final MutableLiveData<LiveDataResponse<List<FraseMotivacional>>> listarFrasesLiveData = new MutableLiveData<>();
    private final MutableLiveData<LiveDataResponse<Boolean>> insertarFraseLiveData = new MutableLiveData<>();
    private final MutableLiveData<LiveDataResponse<Boolean>> actualizarFraseLiveData = new MutableLiveData<>();
    private final MutableLiveData<LiveDataResponse<Boolean>> eliminarFraseLiveData = new MutableLiveData<>();

    private final FraseMotivacionalRepository repository = new FraseMotivacionalRepository();

    // Getters para LiveData
    public LiveData<LiveDataResponse<List<FraseMotivacional>>> getListarFrasesLiveData() {
        return listarFrasesLiveData;
    }

    public LiveData<LiveDataResponse<Boolean>> getInsertarFraseLiveData() {
        return insertarFraseLiveData;
    }

    public LiveData<LiveDataResponse<Boolean>> getActualizarFraseLiveData() {
        return actualizarFraseLiveData;
    }

    public LiveData<LiveDataResponse<Boolean>> getEliminarFraseLiveData() {
        return eliminarFraseLiveData;
    }

    // Métodos de acción
    public void listarFrases(Context context) {
        repository.listarFraseMotivacional(context, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                FraseMotivacional frase = new FraseMotivacional();
                frase.setFrase(result);
                listarFrasesLiveData.postValue(LiveDataResponse.success(List.of(frase)));
            }

            @Override
            public void onFailure() {
                listarFrasesLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void insertarFrase(Context context, FraseMotivacional frase) {
        repository.insertarFraseMotivacional(context, frase, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarFraseLiveData.postValue(LiveDataResponse.success(true));
            }

            @Override
            public void onFailure() {
                insertarFraseLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void actualizarFrase(Context context, FraseMotivacional frase) {
        repository.actualizarFraseMotivacional(context, frase, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                actualizarFraseLiveData.postValue(LiveDataResponse.success(true));
            }

            @Override
            public void onFailure() {
                actualizarFraseLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void eliminarFrase(Context context, int idFrase) {
        repository.eliminarFraseMotivacional(context, idFrase, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarFraseLiveData.postValue(LiveDataResponse.success(true));
            }

            @Override
            public void onFailure() {
                eliminarFraseLiveData.postValue(LiveDataResponse.error());
            }
        });
    }
}
