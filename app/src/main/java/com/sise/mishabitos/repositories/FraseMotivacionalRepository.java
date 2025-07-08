package com.sise.mishabitos.repositories;
import org.json.JSONException;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
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
import com.sise.mishabitos.shared.SharedPreferencesManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FraseMotivacionalRepository {

    public void listarFraseMotivacional(Context context, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frases";
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray frasesArray = jsonObject.getJSONArray("data");

                        if (frasesArray.length() > 0) {
                            int randomIndex = (int) (Math.random() * frasesArray.length());  // 🎯 Índice aleatorio
                            JSONObject fraseRandom = frasesArray.getJSONObject(randomIndex);
                            String contenido = fraseRandom.getString("frase");
                            callback.onSuccess(contenido);
                        } else {
                            callback.onFailure();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onFailure();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");

                String token = SharedPreferencesManager.getInstance(context).getToken();
                headers.put("Authorization", "Bearer " + token);  // 🚀 Aquí se envía el token

                return headers;
            }
        };

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

    public void actualizarFraseMotivacional(Context context, FraseMotivacional frase, Callback<String> callback) {
        String url = Constants.BASE_URL_API + "/frases/" + frase.getIdFrase();
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
