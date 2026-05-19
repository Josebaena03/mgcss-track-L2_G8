package com.mgcss.l2g8.controller.dto;

import com.mgcss.l2g8.domain.enums.EstadoTecnico;

public class TecnicoRequestDTO {
    private String nombre;
    private EstadoTecnico estado;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public EstadoTecnico getEstado() { return estado; }
    public void setEstado(EstadoTecnico estado) { this.estado = estado; }
}
