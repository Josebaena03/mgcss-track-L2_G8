package com.mgcss.l2g8.controller.dto;

import java.util.Date;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudResponseDTO {

	private Long id;
	private EstadoSolicitud estado;
	private Date fechaCreacion;
	private String nombreTecnico;
}