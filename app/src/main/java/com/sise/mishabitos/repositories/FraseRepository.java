package com.sise.mishabitos.repositories;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.mishabitos.entities.Frase;
import com.sise.mishabitos.shared.BaseResponse;
import com.sise.mishabitos.shared.Constants;

import java.lang.reflect.Type;

public class FraseRepository {

    public interface FraseCallback {
        void onFraseRecibida(String textoFrase);
        void onError(String mensaje);
    }

    public void obtenerFrase(Context context, FraseCallback callback) {
        String url = Constants.BASE_URL_API + "/frases";
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Gson gson = new Gson();
                        Type tipo = new TypeToken<BaseResponse<Frase>>(){}.getType();
                        BaseResponse<Frase> baseResponse = gson.fromJson(response, tipo);

                        if (baseResponse != null && baseResponse.isSuccess() && baseResponse.getData() != null) {
                            callback.onFraseRecibida(baseResponse.getData().getFrase());
                        } else {
                            callback.onError("No se pudo obtener la frase");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onError("Error procesando la respuesta");
                    }
                },
                error -> {
                    error.printStackTrace();
                    callback.onError("Error de red");
                });

        queue.add(request);
    }
}