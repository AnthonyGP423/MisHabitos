package com.sise.mishabitos.entities;

import java.util.Date;

public class Insignia {

    private Integer idInsignia;
    private String nombre;
    private String descripcion;
    private String criterioAsignacion;
    private String iconoUrl;
    private Date fechaCreacion;
    private Boolean estadoAuditoria;

    // Getters y Setters

    public Integer getIdInsignia() {
        return idInsignia;
    }

    public void setIdInsignia(Integer idInsignia) {
        this.idInsignia = idInsignia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCriterioAsignacion() {
        return criterioAsignacion;
    }

    public void setCriterioAsignacion(String criterioAsignacion) {
        this.criterioAsignacion = criterioAsignacion;
    }

    public String getIconoUrl() {
        return iconoUrl;
    }

    public void setIconoUrl(String iconoUrl) {
        this.iconoUrl = iconoUrl;
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
