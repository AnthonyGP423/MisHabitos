package com.sise.mishabitos.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.repositories.UsuarioRepository;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.LiveDataResponse;
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.util.List;

public class UsuarioViewModel extends ViewModel {

    private final MutableLiveData<LiveDataResponse<Usuario>> loginUsuarioLiveData;
    private final MutableLiveData<LiveDataResponse<List<Usuario>>> listarUsuariosLiveData;
    private final MutableLiveData<LiveDataResponse<Boolean>> insertarUsuarioLiveData;
    private final MutableLiveData<LiveDataResponse<Boolean>> actualizarUsuarioLiveData;
    private final MutableLiveData<LiveDataResponse<Boolean>> eliminarUsuarioLiveData;

    private final UsuarioRepository usuarioRepository;

    public UsuarioViewModel() {
        loginUsuarioLiveData = new MutableLiveData<>();
        listarUsuariosLiveData = new MutableLiveData<>();
        insertarUsuarioLiveData = new MutableLiveData<>();
        actualizarUsuarioLiveData = new MutableLiveData<>();
        eliminarUsuarioLiveData = new MutableLiveData<>();
        usuarioRepository = new UsuarioRepository();
    }

    public LiveData<LiveDataResponse<Usuario>> getLoginUsuarioLiveData() {
        return loginUsuarioLiveData;
    }

    public LiveData<LiveDataResponse<List<Usuario>>> getListarUsuariosLiveData() {
        return listarUsuariosLiveData;
    }

    public LiveData<LiveDataResponse<Boolean>> getInsertarUsuarioLiveData() {
        return insertarUsuarioLiveData;
    }

    public LiveData<LiveDataResponse<Boolean>> getActualizarUsuarioLiveData() {
        return actualizarUsuarioLiveData;
    }

    public LiveData<LiveDataResponse<Boolean>> getEliminarUsuarioLiveData() {
        return eliminarUsuarioLiveData;
    }

    public void loginUsuario(Context context, String correo, String contrasena) {
        usuarioRepository.loginUsuario(context, correo, contrasena, new Callback<Usuario>() {
            @Override
            public void onSuccess(Usuario result) {
                // IMPORTANTE: Guarda el ID de usuario en SharedPreferences
                SharedPreferencesManager.getInstance(context).saveUserId(result.getIdUsuario());
                loginUsuarioLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                loginUsuarioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void listarUsuarios(Context context) {
        usuarioRepository.listarUsuarios(context, new Callback<List<Usuario>>() {
            @Override
            public void onSuccess(List<Usuario> result) {
                listarUsuariosLiveData.postValue(LiveDataResponse.success(result));
            }

            @Override
            public void onFailure() {
                listarUsuariosLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void insertarUsuario(Context context, Usuario usuario) {
        usuarioRepository.insertarUsuario(context, usuario, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                insertarUsuarioLiveData.postValue(LiveDataResponse.success(true));
            }

            @Override
            public void onFailure() {
                insertarUsuarioLiveData.postValue(LiveDataResponse.error());
            }

            @Override
            public void onError(String message) {
                insertarUsuarioLiveData.postValue(LiveDataResponse.error(message));
            }
        });
    }


    public void actualizarUsuario(Context context, Usuario usuario) {
        usuarioRepository.actualizarUsuario(context, usuario, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                actualizarUsuarioLiveData.postValue(LiveDataResponse.success(true));
            }

            @Override
            public void onFailure() {
                actualizarUsuarioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }

    public void eliminarUsuario(Context context, int idUsuario) {
        usuarioRepository.eliminarUsuario(context, idUsuario, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                eliminarUsuarioLiveData.postValue(LiveDataResponse.success(true));
            }

            @Override
            public void onFailure() {
                eliminarUsuarioLiveData.postValue(LiveDataResponse.error());
            }
        });
    }
}
