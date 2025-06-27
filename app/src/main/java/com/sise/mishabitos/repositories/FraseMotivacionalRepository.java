package com.sise.mishabitos.repositories;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.FraseMotivacional;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Callback;
import com.sise.mishabitos.shared.Constants;

import java.lang.reflect.Type;

public class FraseMotivacionalRepository {

    public void listarFraseMotivacional(Context context, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frases";
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        Type tipo = new TypeToken<BaseResponse<FraseMotivacional>>() {}.getType();
                        BaseResponse<FraseMotivacional> baseResponse = gson.fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess() && baseResponse.getData() != null) {
                            callback.onSuccess(baseResponse.getData().getFrase());
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

    public void insertarFraseMotivacional(Context context, FraseMotivacional frase, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frases";
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(frase);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Frase guardada correctamente");
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
                    return jsonBody == null ? null : jsonBody.getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(request);
    }

    public void actualizarFraseMotivacional(Context context, FraseMotivacional frase, Callback<String> callback) {
        String url = Constants.BASE_URL_API + Constants.ENDPOINT_FRASE_MOTIVACIONAL + frase.getIdFrase(); // Asegúrate de que `FraseMotivacional` tenga un `id`
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(frase);

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = gson.fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Frase actualizada correctamente");
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
                    return jsonBody == null ? null : jsonBody.getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(request);
    }

    public void eliminarFraseMotivacional(Context context, int idFrase, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frases/" + idFrase;
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    try {
                        Type tipo = new TypeToken<BaseResponse<String>>() {}.getType();
                        BaseResponse<String> baseResponse = new Gson().fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess()) {
                            callback.onSuccess("Frase eliminada correctamente");
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
