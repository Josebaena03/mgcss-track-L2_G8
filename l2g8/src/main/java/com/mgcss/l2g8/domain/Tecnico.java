package com.mgcss.l2g8.domain;

import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tecnico {

    private Long id;
    private String nombre;
    private EstadoTecnico estado;
    
}
