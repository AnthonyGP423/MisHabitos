package com.sise.mishabitos.entities;
import java.io.Serializable;
import java.util.Date;

public class Habito implements Serializable {

    private Integer idHabito;
    private Usuario usuario;
    private Categoria categoria;
    private String nombre;
    private String descripcion;
    private String horaSugerida; // formato "HH:mm"
    private Date fechaCreacion;
    private Boolean estadoAuditoria;
    private Integer idCategoria;

    // Getters y Setters

    public Integer getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(Integer idHabito) {
        this.idHabito = idHabito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public String getHoraSugerida() {
        return horaSugerida;
    }

    public void setHoraSugerida(String horaSugerida) {
        this.horaSugerida = horaSugerida;
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
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    // Campo temporal solo para la app (no se guarda en la BD)
    private boolean completadoLocal;

    public boolean isCompletadoLocal() {
        return completadoLocal;
    }

    public void setCompletadoLocal(boolean completadoLocal) {
        this.completadoLocal = completadoLocal;
    }

}
