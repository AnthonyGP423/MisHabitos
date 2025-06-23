package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.Categoria;
import com.sise.mishabitos.repositories.CategoriaRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;

import java.util.List;

public class CategoriaViewModel extends ViewModel {

    private MutableLiveData<LiveDataResponse<List<Categoria>>> listarCategoriasLiveData;
    private MutableLiveData<LiveDataResponse<String>> insertarCategoriaLiveData;
    private MutableLiveData<LiveDataResponse<String>> actualizarCategoriaLiveData;
    private MutableLiveData<LiveDataResponse<String>> eliminarCategoriaLiveData;

    private CategoriaRepository categoriaRepository;

    public CategoriaViewModel() {
        listarCategoriasLiveData = new MutableLiveData<>();
        insertarCategoriaLiveData = new MutableLiveData<>();
        actualizarCategoriaLiveData = new MutableLiveData<>();
        eliminarCategoriaLiveData = new MutableLiveData<>();
        categoriaRepository = new CategoriaRepository();
    }

    public LiveData<LiveDataResponse<List<Categoria>>> getListarCategoriasLiveData() {
        return listarCategoriasLiveData;
    }

    public LiveData<LiveDataResponse<String>> getInsertarCategoriaLiveData() {
        return insertarCategoriaLiveData;
    }

    public LiveData<LiveDataResponse<String>> getActualizarCategoriaLiveData() {
        return actualizarCategoriaLiveData;
    }

    public LiveData<LiveDataResponse<String>> getEliminarCategoriaLiveData() {
        return eliminarCategoriaLiveData;
    }

    public void listarCategorias(Context context) {
        categoriaRepository.listarCategorias(context, new Callback<List<Categoria>>() {
            @Override
            public void onSuccess(List<Categoria> result) {
                listarCategoriasLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarCategoriasLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void insertarCategoria(Context context, Categoria categoria) {
        categoriaRepository.insertarCategoria(context, categoria, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarCategoriaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                insertarCategoriaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void actualizarCategoria(Context context, Categoria categoria) {
        categoriaRepository.actualizarCategoria(context, categoria, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                actualizarCategoriaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                actualizarCategoriaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void eliminarCategoria(Context context, int idCategoria) {
        categoriaRepository.eliminarCategoria(context, idCategoria, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarCategoriaLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                eliminarCategoriaLiveData.postValue(LiveDataResponse.error());
            }
        });
    }
}
