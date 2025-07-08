package com.sise.mishabitos.repositories;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.Usuario;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.Constants;
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepository {

    public void loginUsuario(Context context, String correo, String contrasena, Callback<Usuario> callback) {
        String url = Constants.BASE_URL_API + "/usuarios/login?correo=" + correo + "&contrasena=" + contrasena;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<Usuario>>(){}.getType();
                        BaseResponse<Usuario> baseResponse = new Gson().fromJson(response, tipo);
                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess(baseResponse.getData());
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                });

        queue.add(request);
    }
    public void obtenerUsuarioPorId(Context context, int idUsuario, Callback<Usuario> callback) {
        String url = Constants.BASE_URL_API + "/usuarios/" + idUsuario;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<Usuario>>() {}.getType();
                        BaseResponse<Usuario> baseResponse = new Gson().fromJson(response, type);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess(baseResponse.getData());
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                if (token != null) {
                    headers.put("Authorization", "Bearer " + token);
                }
                return headers;
            }
        };

        queue.add(request);
    }

    public void listarUsuarios(Context context, Callback<List<Usuario>> callback) {
        String url = Constants.BASE_URL_API + "/usuarios";
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<List<Usuario>>>(){}.getType();
                        BaseResponse<List<Usuario>> baseResponse = new Gson().fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess() && baseResponse.getData() != null) {
                            callback.onSuccess(baseResponse.getData());
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                if (token != null) {
                    headers.put("Authorization", "Bearer " + token);
                }
                return headers;
            }
        };

        queue.add(request);
    }

    public void insertarUsuario(Context context, Usuario usuario, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/usuarios";
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(usuario);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>(){}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Usuario guardado correctamente");
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonBody.getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(request);
    }

    public void actualizarUsuario(Context context, Usuario usuario, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/usuarios/" + usuario.getIdUsuario();
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(usuario);

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>(){}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Usuario actualizado correctamente");
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return jsonBody.getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                if (token != null) {
                    headers.put("Authorization", "Bearer " + token);
                }
                return headers;
            }
        };

        queue.add(request);
    }

    public void eliminarUsuario(Context context, int idUsuario, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/usuarios/" + idUsuario;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>(){}.getType();
                        BaseResponse<String> baseResponse = new Gson().fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Usuario eliminado correctamente");
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                if (token != null) {
                    headers.put("Authorization", "Bearer " + token);
                }
                return headers;
            }
        };

        queue.add(request);
    }
}
