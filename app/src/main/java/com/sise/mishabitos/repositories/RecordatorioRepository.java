package com.sise.mishabitos.repositories;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.Recordatorio;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.Constants;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class RecordatorioRepository {

    public void listarRecordatorioPorHabito(Context context, int idHabito, Callback<List<Recordatorio>> callback) {
        String url = Constants.BASE_URL_API + "/recordatorios/habito/" + idHabito;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<Recordatorio[]>>() {}.getType();
                        BaseResponse<Recordatorio[]> baseResponse = new Gson().fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            List<Recordatorio> lista = Arrays.asList(baseResponse.getData());
                            callback.onSuccess(lista);
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

    public void insertarRecordatorio(Context context, Recordatorio recordatorio, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/recordatorios";
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(recordatorio);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);
                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Recordatorio guardado correctamente");
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

    public void actualizarRecordatorio(Context context, Recordatorio recordatorio, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/recordatorios/" + recordatorio.getIdRecordatorio();
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(recordatorio);

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);
                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Recordatorio actualizado correctamente");
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

    public void eliminarRecordatorio(Context context, int idRecordatorio, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/recordatorios/" + idRecordatorio;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = new Gson().fromJson(response, tipo);
                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Recordatorio eliminado correctamente");
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
}
