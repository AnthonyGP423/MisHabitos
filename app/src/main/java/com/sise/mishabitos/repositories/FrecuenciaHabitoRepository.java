package com.sise.mishabitos.repositories;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.FrecuenciaHabito;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.Constants;
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrecuenciaHabitoRepository {

    public void listarFrecuenciaPorHabito(Context context, int idHabito, Callback<List<FrecuenciaHabito>> callback) {
        String url = Constants.BASE_URL_API + "/frecuencias/habito/" + idHabito;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<List<FrecuenciaHabito>>>(){}.getType();
                        BaseResponse<List<FrecuenciaHabito>> baseResponse = new Gson().fromJson(response, tipo);

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

    public void insertarFrecuencia(Context context, FrecuenciaHabito frecuencia, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frecuencias";
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(frecuencia);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>(){}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);
                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Frecuencia registrada correctamente");
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

    public void eliminarFrecuencia(Context context, int idFrecuencia, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frecuencias/" + idFrecuencia;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>(){}.getType();
                        BaseResponse<String> baseResponse = new Gson().fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Frecuencia eliminada correctamente");
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
