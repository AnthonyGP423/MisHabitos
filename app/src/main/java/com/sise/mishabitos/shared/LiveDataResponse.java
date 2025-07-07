package com.sise.mishabitos.shared;

public class LiveDataResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public LiveDataResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public LiveDataResponse(boolean success, T data) {
        this(success, data, null);
    }

    public static <T> LiveDataResponse<T> success(T data) {
        return new LiveDataResponse<>(true, data, null);
    }

    public static <T> LiveDataResponse<T> error() {
        return new LiveDataResponse<>(false, null, null);
    }

    public static <T> LiveDataResponse<T> error(String message) {
        return new LiveDataResponse<>(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
