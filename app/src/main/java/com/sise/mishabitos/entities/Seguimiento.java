package com.sise.mishabitos.entities;

import java.io.Serializable;
import java.util.Date;

public class Seguimiento implements Serializable {  // Opcional: Serializable si lo necesitas enviar por Intent

    private Integer idSeguimiento;
    private Integer idUsuario;
    private Integer idHabito;
    private String fecha;
    private Boolean estado;

    private Habito habito;
    private Boolean completado;
    private String notaDia;
    private Date fechaRegistro;
    private Date fechaCreacion;
    private Boolean estadoAuditoria;

    // Getters y Setters

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(Integer idHabito) {
        this.idHabito = idHabito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Habito getHabito() {
        return habito;
    }

    public void setHabito(Habito habito) {
        this.habito = habito;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }
    public String getNotaDia() {
        return notaDia;
    }

    public void setNotaDia(String notaDia) {
        this.notaDia = notaDia;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
