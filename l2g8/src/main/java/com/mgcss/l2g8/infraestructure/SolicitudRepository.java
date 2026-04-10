package com.mgcss.l2g8.infraestructure;

import java.util.Optional;

import com.mgcss.l2g8.domain.Solicitud;

public interface SolicitudRepository {
    
    Solicitud save(Solicitud solicitud);
    Optional<Solicitud> findById(Long id);

}
