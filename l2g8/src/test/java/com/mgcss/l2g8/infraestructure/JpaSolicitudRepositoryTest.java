package com.mgcss.l2g8.infraestructure;

import com.mgcss.l2g8.domain.enums.EstadoSolicitud;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest 
@Tag("integration") 
@ActiveProfiles("test") 
public class JpaSolicitudRepositoryTest {

    @Autowired
    private JpaSolicitudRepository solicitudRepository;

    @Autowired
    private JpaTecnicoRepository tecnicoRepository;

    @Test
    public void debeGuardarYRecuperarSolicitudConTecnico() {
        TecnicoEntity tecnico = TecnicoEntity.builder()
                .nombre("Paco")
                .estado(EstadoTecnico.ACTIVO)
                .build();
        tecnico = tecnicoRepository.save(tecnico);

        SolicitudEntity solicitud = SolicitudEntity.builder()
                .estado(EstadoSolicitud.ABIERTA)
                .fechaCreacion(LocalDateTime.now())
                .tecnico(tecnico)
                .build();
        SolicitudEntity solicitudGuardada = solicitudRepository.save(solicitud);

        Optional<SolicitudEntity> recuperada = solicitudRepository.findById(solicitudGuardada.getId());

        assertTrue(recuperada.isPresent());
        assertEquals(EstadoSolicitud.ABIERTA, recuperada.get().getEstado());
        assertEquals("Paco", recuperada.get().getTecnico().getNombre());
    }
}