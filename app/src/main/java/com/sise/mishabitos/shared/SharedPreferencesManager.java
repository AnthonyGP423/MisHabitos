package com.sise.mishabitos.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "MiHabitosPrefs";
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_USER_ID = "user_id";

    private static final String KEY_NOTIFICACIONES = "notificaciones";
    private static final String KEY_MODO_OSCURO = "modo_oscuro";

    private static SharedPreferencesManager instance;
    private final SharedPreferences prefs;

    private SharedPreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    // ---------- Sesión ----------
    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply();
    }

    public void saveUserId(int userId) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void clearUserId() {
        prefs.edit().remove(KEY_USER_ID).apply();
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }

    // ---------- Configuración ----------
    public void saveNotificaciones(boolean value) {
        prefs.edit().putBoolean(KEY_NOTIFICACIONES, value).apply();
    }

    public boolean getNotificaciones() {
        return prefs.getBoolean(KEY_NOTIFICACIONES, true); // true por defecto
    }

    public void saveModoOscuro(boolean value) {
        prefs.edit().putBoolean(KEY_MODO_OSCURO, value).apply();
    }

    public boolean getModoOscuro() {
        return prefs.getBoolean(KEY_MODO_OSCURO, false); // false por defecto
    }
}
