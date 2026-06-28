package com.rednorte.ms_citas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private CitaService citaService;

    @Test
    void findAllShouldReturnCitaList() {
        Cita cita = new Cita();
        cita.setId(1L);
        cita.setFecha("2026-06-22");
        when(citaRepository.findAll()).thenReturn(List.of(cita));

        List<Cita> result = citaService.findAll();

        assertEquals(1, result.size());
        assertEquals("2026-06-22", result.get(0).getFecha());
    }

    @Test
    void findByIdShouldReturnCitaWhenFound() {
        Cita cita = new Cita();
        cita.setId(1L);
        when(citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        Optional<Cita> result = citaService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void saveShouldPersistCita() {
        Cita cita = new Cita();
        cita.setEspecialidad("Cardiología");
        when(citaRepository.save(cita)).thenReturn(cita);

        Cita result = citaService.save(cita);

        assertNotNull(result);
        assertEquals("Cardiología", result.getEspecialidad());
        verify(citaRepository).save(cita);
    }

    @Test
    void updateShouldModifyExistingCita() {
        Cita existing = new Cita();
        existing.setId(1L);
        existing.setFecha("2026-06-22");
        existing.setEspecialidad("General");
        existing.setIdPaciente(1L);

        Cita updated = new Cita();
        updated.setFecha("2026-06-23");
        updated.setEspecialidad("Dermatología");
        updated.setIdPaciente(2L);

        when(citaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(citaRepository.save(existing)).thenReturn(existing);

        Optional<Cita> result = citaService.update(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("2026-06-23", result.get().getFecha());
        assertEquals("Dermatología", result.get().getEspecialidad());
    }

    @Test
    void deleteShouldReturnTrueWhenCitaExists() {
        when(citaRepository.existsById(1L)).thenReturn(true);

        boolean deleted = citaService.delete(1L);

        assertTrue(deleted);
        verify(citaRepository).deleteById(1L);
    }

    @Test
    void deleteShouldReturnFalseWhenCitaDoesNotExist() {
        when(citaRepository.existsById(1L)).thenReturn(false);

        boolean deleted = citaService.delete(1L);

        assertFalse(deleted);
        verify(citaRepository, never()).deleteById(anyLong());
    }
}
