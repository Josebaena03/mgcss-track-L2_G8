package com.mgcss.l2g8.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.l2g8.controller.dto.TecnicoRequestDTO;
import com.mgcss.l2g8.controller.dto.TecnicoResponseDTO;
import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.infraestructure.TecnicoRepository;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private TecnicoService tecnicoService;

    @Test
    void testCrearTecnico() {
        TecnicoRequestDTO req = new TecnicoRequestDTO();
        req.setNombre("Juan Perez");
        // Asumiendo que existe un enumerado u objeto para estado, si no ajusta esto a tu codigo real.
        // req.setEstado(EstadoTecnico.DISPONIBLE);

        Tecnico tecnicoEsperado = Tecnico.builder()
                .id(1L)
                .nombre("Juan Perez")
                //.estado(EstadoTecnico.DISPONIBLE)
                .build();

        when(tecnicoRepository.save(any(Tecnico.class))).thenReturn(tecnicoEsperado);

        TecnicoResponseDTO response = tecnicoService.crearTecnico(req);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Perez", response.getNombre());
    }

    @Test
    void testObtenerTodos() {
        Tecnico tecnico1 = Tecnico.builder().id(1L).nombre("A").build();
        Tecnico tecnico2 = Tecnico.builder().id(2L).nombre("B").build();
        
        when(tecnicoRepository.findAll()).thenReturn(Arrays.asList(tecnico1, tecnico2));

        List<TecnicoResponseDTO> response = tecnicoService.obtenerTodos();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());
    }
}