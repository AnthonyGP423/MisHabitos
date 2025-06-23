package com.sise.mishabitos.entities;

import java.util.Date;

public class HabitoInsignia {

    private Integer idHabitoInsignia;
    private Usuario usuario;
    private Habito habito;
    private Insignia insignia;
    private Date fechaObtenida;
    private Date fechaCreacion;
    private Boolean estadoAuditoria;

    public Boolean getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public void setEstadoAuditoria(Boolean estadoAuditoria) {
        this.estadoAuditoria = estadoAuditoria;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaObtenida() {
        return fechaObtenida;
    }

    public void setFechaObtenida(Date fechaObtenida) {
        this.fechaObtenida = fechaObtenida;
    }

    public Habito getHabito() {
        return habito;
    }

    public void setHabito(Habito habito) {
        this.habito = habito;
    }

    public Integer getIdHabitoInsignia() {
        return idHabitoInsignia;
    }

    public void setIdHabitoInsignia(Integer idHabitoInsignia) {
        this.idHabitoInsignia = idHabitoInsignia;
    }

    public Insignia getInsignia() {
        return insignia;
    }

    public void setInsignia(Insignia insignia) {
        this.insignia = insignia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
