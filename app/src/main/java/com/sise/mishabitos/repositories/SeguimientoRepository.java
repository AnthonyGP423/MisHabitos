package com.sise.mishabitos.repositories;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.Seguimiento;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.Constants;
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeguimientoRepository {

    /**
     * ✅ Listar seguimientos por Hábito
     */
    public void listarSeguimientosPorHabito(Context context, int idHabito, Callback<List<Seguimiento>> callback) {
        String url = Constants.BASE_URL_API + "/seguimientos/habito/" + idHabito;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<List<Seguimiento>>>() {}.getType();
                        BaseResponse<List<Seguimiento>> baseResponse = new Gson().fromJson(response, type);

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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }

    /**
     * Listar seguimientos por Usuario y Fecha (CORREGIDO)
     */
    public void listarSeguimientosPorUsuarioYFecha(Context context, int idUsuario, String fecha, Callback<List<Seguimiento>> callback) {
        String url = Constants.BASE_URL_API + "/seguimientos/usuario/" + idUsuario + "/fecha/" + fecha;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<List<Seguimiento>>>() {}.getType();
                        BaseResponse<List<Seguimiento>> baseResponse = new Gson().fromJson(response, type);

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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }

    /**
     * ✅ Insertar seguimiento
     */
    public void insertarSeguimiento(Context context, Seguimiento seguimiento, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/seguimientos";
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(seguimiento);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<Seguimiento>>() {}.getType();
                        BaseResponse<Seguimiento> baseResponse = gson.fromJson(response, type);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Seguimiento registrado correctamente");
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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(request);
    }

    public void listarSeguimientosCompletadosPorUsuario(Context context, int idUsuario, Callback<List<Seguimiento>> callback) {
        String url = Constants.BASE_URL_API + "/seguimientos/usuario/" + idUsuario + "/completados";
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<List<Seguimiento>>>() {}.getType();
                        BaseResponse<List<Seguimiento>> baseResponse = new Gson().fromJson(response, type);

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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }


    /**
     * ✅ Actualizar seguimiento
     */
    public void actualizarSeguimiento(Context context, Seguimiento seguimiento, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/seguimientos/" + seguimiento.getIdSeguimiento();
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(seguimiento);

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, type);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Seguimiento actualizado correctamente");
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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(request);
    }

    /**
     * ✅ Eliminar seguimiento
     */
    public void eliminarSeguimiento(Context context, int idSeguimiento, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/seguimientos/" + idSeguimiento;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    try {
                        Type type = new TypeToken<BaseResponse<Seguimiento>>() {}.getType();
                        BaseResponse<Seguimiento> baseResponse = new Gson().fromJson(response, type);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Seguimiento eliminado correctamente");
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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(request);
    }
}
