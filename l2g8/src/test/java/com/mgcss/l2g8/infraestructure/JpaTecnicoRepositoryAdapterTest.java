package com.mgcss.l2g8.infraestructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.l2g8.domain.Tecnico;
import com.mgcss.l2g8.domain.enums.EstadoTecnico;

@ExtendWith(MockitoExtension.class)
class JpaTecnicoRepositoryAdapterTest {

    @Mock
    private JpaTecnicoRepository jpaTecnicoRepository;

    @InjectMocks
    private JpaTecnicoRepositoryAdapter adapter;

    @Test
    void testFindById() {
        TecnicoEntity entity = TecnicoEntity.builder()
                .id(1L)
                .nombre("Test")
                .estado(EstadoTecnico.ACTIVO)
                .build();
        when(jpaTecnicoRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Tecnico> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getNombre());
    }

    @Test
    void testFindAll() {
        TecnicoEntity e1 = TecnicoEntity.builder().id(1L).nombre("Test1").build();
        TecnicoEntity e2 = TecnicoEntity.builder().id(2L).nombre("Test2").build();
        
        when(jpaTecnicoRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Tecnico> result = adapter.findAll();

        assertEquals(2, result.size());
        assertEquals("Test1", result.get(0).getNombre());
    }

    @Test
    void testSave() {
        Tecnico domain = Tecnico.builder().nombre("SaveMe").estado(EstadoTecnico.ACTIVO).build();
        TecnicoEntity savedEntity = TecnicoEntity.builder().id(99L).nombre("SaveMe").estado(EstadoTecnico.ACTIVO).build();
        
        when(jpaTecnicoRepository.save(any(TecnicoEntity.class))).thenReturn(savedEntity);

        Tecnico result = adapter.save(domain);

        assertEquals(99L, result.getId());
        assertEquals("SaveMe", result.getNombre());
    }
}