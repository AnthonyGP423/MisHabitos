package com.sise.mishabitos.entities;

import java.util.Date;

public class FrecuenciaHabito {

    private Integer idFrecuencia;
    private Habito habito;
    private String diaSemana;
    private Date fechaCreacion;
    private Boolean estadoAuditoria;

    // Getters y Setters

    public Integer getIdFrecuencia() {
        return idFrecuencia;
    }

    public void setIdFrecuencia(Integer idFrecuencia) {
        this.idFrecuencia = idFrecuencia;
    }

    public Habito getHabito() {
        return habito;
    }

    public void setHabito(Habito habito) {
        this.habito = habito;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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
