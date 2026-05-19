package com.mgcss.l2g8.controller.dto;

import com.mgcss.l2g8.domain.enums.EstadoTecnico;

public class TecnicoResponseDTO {
    private Long id;
    private String nombre;
    private EstadoTecnico estado;

    public TecnicoResponseDTO(Long id, String nombre, EstadoTecnico estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public EstadoTecnico getEstado() { return estado; }
    public void setEstado(EstadoTecnico estado) { this.estado = estado; }
}
