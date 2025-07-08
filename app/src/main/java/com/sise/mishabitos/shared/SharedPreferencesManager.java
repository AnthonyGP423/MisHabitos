package com.sise.mishabitos.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "MiHabitosPrefs";
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NOMBRE = "user_nombre";
    private static final String KEY_APELLIDO_PATERNO = "user_apellido_paterno";
    private static final String KEY_APELLIDO_MATERNO = "user_apellido_materno";
    private static final String KEY_CORREO = "user_correo";
    private static final String KEY_FECHA_NACIMIENTO = "user_fecha_nacimiento";

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

    // Token
    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply();
    }

    // User ID
    public void saveUserId(int userId) {
        prefs.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void clearUserId() {
        prefs.edit().remove(KEY_USER_ID).apply();
    }

    // Nombre
    public void saveUserName(String nombre) {
        prefs.edit().putString(KEY_NOMBRE, nombre).apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_NOMBRE, "");
    }

    // Apellido Paterno
    public void saveApellidoPaterno(String apellido) {
        prefs.edit().putString(KEY_APELLIDO_PATERNO, apellido).apply();
    }

    public String getApellidoPaterno() {
        return prefs.getString(KEY_APELLIDO_PATERNO, "");
    }

    // Apellido Materno
    public void saveApellidoMaterno(String apellido) {
        prefs.edit().putString(KEY_APELLIDO_MATERNO, apellido).apply();
    }

    public String getApellidoMaterno() {
        return prefs.getString(KEY_APELLIDO_MATERNO, "");
    }

    // Correo
    public void saveUserEmail(String email) {
        prefs.edit().putString(KEY_CORREO, email).apply();
    }

    public String getUserEmail() {
        return prefs.getString(KEY_CORREO, "");
    }

    // Fecha Nacimiento
    public void saveFechaNacimiento(String fecha) {
        prefs.edit().putString(KEY_FECHA_NACIMIENTO, fecha).apply();
    }

    public String getFechaNacimiento() {
        return prefs.getString(KEY_FECHA_NACIMIENTO, "");
    }

    // Limpiar
    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
