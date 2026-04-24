package com.mgcss.l2g8.infraestructure;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

// Fíjate que aquí importamos el LocalDateTime moderno
import java.time.LocalDateTime; 

@Entity
@Table(name = "solicitudes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntity tecnico;

    private LocalDateTime fechaCreacion; 
}