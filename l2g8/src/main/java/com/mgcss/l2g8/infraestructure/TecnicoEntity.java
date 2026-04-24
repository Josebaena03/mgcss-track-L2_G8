package com.mgcss.l2g8.infraestructure;

import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity
@Table(name = "tecnicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TecnicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private EstadoTecnico estado;
}