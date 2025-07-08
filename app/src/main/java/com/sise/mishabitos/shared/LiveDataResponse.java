package com.sise.mishabitos.shared;

public class LiveDataResponse<T> {

    public enum Status { SUCCESS, ERROR, LOADING }

    private Status status;
    private T data;

    public LiveDataResponse() {
        // Constructor vacío necesario para instancias vacías
    }

    public LiveDataResponse(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> LiveDataResponse<T> success(T data) {
        return new LiveDataResponse<>(Status.SUCCESS, data);
    }

    public static <T> LiveDataResponse<T> error() {
        return new LiveDataResponse<>(Status.ERROR, null);
    }

    public static <T> LiveDataResponse<T> loading() {
        return new LiveDataResponse<>(Status.LOADING, null);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}
