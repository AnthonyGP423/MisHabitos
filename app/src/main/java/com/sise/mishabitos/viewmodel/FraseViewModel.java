package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.repositories.FraseRepository;

public class FraseViewModel extends ViewModel {
    private final MutableLiveData<String> fraseDelDia = new MutableLiveData<>();
    private final FraseRepository repo = new FraseRepository();

    public LiveData<String> getFraseDelDia() {
        return fraseDelDia;
    }

    public void cargarFrase(Context context) {
        repo.obtenerFrase(context, new FraseRepository.FraseCallback() {
            @Override
            public void onFraseRecibida(String textoFrase) {
                fraseDelDia.postValue(textoFrase);
            }

            @Override
            public void onError(String mensaje) {
                fraseDelDia.postValue(mensaje);
            }
        });
    }
}