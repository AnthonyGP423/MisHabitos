package com.sise.mishabitos.entities;

import java.util.Date;

public class Frase {
    private Integer id_frase;
    private String frase;
    private String autor;
    private Date fechaCreacion;
    private Boolean estado;

    // Getters y Setters
    public Integer getId_frase() { return id_frase; }
    public void setId_frase(Integer id_frase) { this.id_frase = id_frase; }

    public String getFrase() { return frase; }
    public void setFrase(String frase) { this.frase = frase; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}