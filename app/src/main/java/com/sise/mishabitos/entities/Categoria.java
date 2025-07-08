package com.sise.mishabitos.entities;
import java.io.Serializable;
import java.util.Date;

public class Categoria implements Serializable{

    private Integer idCategoria;
    private String nombre;
    private Date fechaCreacion;
    private Boolean estadoAuditoria;

    // Getters y Setters

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public void setEstadoAuditoria(Boolean estadoAuditoria) {
        this.estadoAuditoria = estadoAuditoria;
    }
}
